package com.jf_isomc1000.rabbitmqprovider;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Wangjie
 * @Date: 2020-09-30 13:52
 * @Description: 这里是描述
 * To change this template use File | Settings | File and Templates.
 */
@RestController
public class SendMessageController {

    //使用RabbitTemplate,这提供了接收/发送等等方法
    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/sendDirectMessage")
    public String sendDirectMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "test message, hello!";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String,Object> map=new HashMap<>();
        map.put("messageId",messageId);
        map.put("messageData",messageData);
        map.put("createTime",createTime);
        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
        rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", map);
        return "ok";
    }

    @GetMapping("/sendTopicMessage1")
    public String sendTopicMessage1() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageManData = "message: M A N ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> manMap = new HashMap<>();
        manMap.put("messageId", messageId);
        manMap.put("messageData", messageManData);
        manMap.put("createTime", createTime);
        rabbitTemplate.convertAndSend("topicExchange", "topic.man", manMap);
        return "ok";
    }

    @GetMapping("/sendTopicMessage2")
    public String sendTopicMessage2() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageWomanData = "message: woman is all ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> womanMap = new HashMap<>();
        womanMap.put("messageId", messageId);
        womanMap.put("messageData", messageWomanData);
        womanMap.put("createTime", createTime);
        rabbitTemplate.convertAndSend("topicExchange", "topic.woman", womanMap);
        return "ok";
    }

    @GetMapping("/sendFanoutMessage")
    public String sendFanoutMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: testFanoutMessage ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        rabbitTemplate.convertAndSend("fanoutExchange", null, map);
        return "ok";
    }
    //=========================生产者推送消息的消息确认=========================
    /**
     * ①：消息成功地到了服务器，但没找到交换机；
     * 控制台的打印信息如下：
     *ConfirmCallback:     相关数据：null
     * ConfirmCallback:     确认情况：false
     * ConfirmCallback:     原因：channel error; protocol method: #method<channel.close>(reply-code=404, reply-text=NOT_FOUND - no exchange 'non-existent-exchange' in vhost '/', class-id=60, method-id=40)
     * 说明这种情况下，消息确认触发的是 setConfirmCallback 的回调函数
     */
    @GetMapping("/TestMessageAck")
    public String TestMessageAck() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: non-existent-exchange test message ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        rabbitTemplate.convertAndSend("non-existent-exchange", "TestDirectRouting", map);
        return "ok";
    }

    /**
     * ②：消息成功地到了服务器，找到了交换机，但没有处理消息的 工作队列
     * 控制台对应到的打印信息如下：
     *ConfirmCallback:     相关数据：null
     * ConfirmCallback:     确认情况：true
     * ConfirmCallback:     原因：null
     * ReturnCallback:     消息：(Body:'{createTime=2020-10-09 16:36:53, messageId=06029d52-5e65-4edf-bad5-132342e74cdd, messageData=message: lonelyDirectExchange test message }' MessageProperties [headers={}, contentType=application/x-java-serialized-object, contentLength=0, receivedDeliveryMode=PERSISTENT, priority=0, deliveryTag=0])
     * ReturnCallback:     回应码：312
     * ReturnCallback:     回应信息：NO_ROUTE
     * ReturnCallback:     交换机：lonelyDirectExchange
     * ReturnCallback:     路由键：TestDirectRouting
     *结合上面的打印信息，可以知道
     * 在这种情况下，生产者是成功地发送了消息的，但作为消费者的一端，由于没有找到工作队列，
     * 所以，消费消息的环节是失败的。消息只到了交换机部分
     * 此时触发的回调函数为：
     * setConfirmCallback 和  setReturnCallback
     *
     */
    @GetMapping("/TestMessageAck2")
    public String TestMessageAck2() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: lonelyDirectExchange test message ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        rabbitTemplate.convertAndSend("lonelyDirectExchange", "TestDirectRouting", map);
        return "ok";
    }

    /**
     * ③：无交换机，无队列的情况
     * 结合①和②，我们可以知道，消息在生产者和消费者间的传递大致过程如下
     * provider--->server--->exchange--->queue---->consumer
     * ↑<------------callBack-----------------------------------------↓
     * 如果交换机都没了，自然就不会有queue的什么事了。
     * 所以，这种情况下的回调信息，是跟单独没有交换的情况①是一样的。
     */
    /**
     * ④：消息成功推送，找到了交换机和队列
     *控制台打印的消息入下
     * ConfirmCallback:     相关数据：null
     * ConfirmCallback:     确认情况：true
     * ConfirmCallback:     原因：null
     *也就是说，生产者成功推得消息后，会触发
     * setConfirmCallback 回调函数
     */
    //=========================生产者推送消息的消息确认=========================

}
