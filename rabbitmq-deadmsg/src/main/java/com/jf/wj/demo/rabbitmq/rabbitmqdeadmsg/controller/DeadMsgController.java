package com.jf.wj.demo.rabbitmq.rabbitmqdeadmsg.controller;

import com.jf.wj.demo.rabbitmq.rabbitmqdeadmsg.MsgEnum.MessageTypeEnum;
import com.jf.wj.demo.rabbitmq.rabbitmqdeadmsg.common.MessageQueueConstants;
import com.jf.wj.demo.rabbitmq.rabbitmqdeadmsg.entity.QueueMessage;
import com.jf.wj.demo.rabbitmq.rabbitmqdeadmsg.service.MessageQueueService;
import com.jf.wj.demo.rabbitmq.rabbitmqdeadmsg.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Wangjie
 * @Date: 2020-10-12 13:37
 * @Description: 这里是描述
 * To change this template use File | Settings | File and Templates.
 */
@RestController
public class DeadMsgController {

    @Autowired
    private MessageQueueService messageQueueService;
    //死信延时队列实现-----start
    @GetMapping("/sendDirect")
    public String sendDirectMessage() {
        messageQueueService.sendDirectMsg(MessageQueueConstants.HELLO_QUEUE_NAME,"Direct：Hello world！");
        return "ok ! direct msg has been sent";
    }

    @GetMapping("/sendDead")
    public String sendDeadMessage() {
        System.out.println(DateUtil.convert(DateUtil.DATE_FORMAT,new Date())+"----->>>开始发送延迟类消息----->>>20s");
        messageQueueService.sendDeadMsg(MessageQueueConstants.HELLO_QUEUE_NAME,"我是延迟20s后到达的信息",20000);
        return "ok ! dead msg has been sent";
    }
    //死信延时队列实现-----end


    //插件化延时队列实现-----start
    @RequestMapping("/send")
    public String send(){
        QueueMessage message = new QueueMessage(MessageQueueConstants.QUEUE_HELLO_NAME,MessageQueueConstants.DEFAULT_DIRECT_EXCHANGE_NAME, "测试及时消息...");
        message.setType(0);
        messageQueueService.sendDelayMsg(message);
        return "ok";
    }

    @RequestMapping("/send/delayed")
    public String sendDelayed(){
        System.out.println("发送延迟消息:" + (System.currentTimeMillis() / 1000));
        {
            //消息1    10秒延迟
            QueueMessage message = new QueueMessage(MessageQueueConstants.QUEUE_HELLO_NAME,MessageQueueConstants.DEFAULT_DELAYED_EXCHANGE, "测试延时消息 001 --> 10s");
            message.setType(MessageTypeEnum.DELAYED.getIndex());
            message.setSeconds(10);
            messageQueueService.sendDelayMsg(message);
        }
        {
            //消息2  30 秒延迟
            QueueMessage message = new QueueMessage(MessageQueueConstants.QUEUE_HELLO_NAME,MessageQueueConstants.DEFAULT_DELAYED_EXCHANGE, "测试延时消息 002 --> 30s");
            message.setType(MessageTypeEnum.DELAYED.getIndex());
            message.setSeconds(30);
            messageQueueService.sendDelayMsg(message);
        }
        {
            //消息3  5秒延迟
            QueueMessage message = new QueueMessage(MessageQueueConstants.QUEUE_HELLO_NAME,MessageQueueConstants.DEFAULT_DELAYED_EXCHANGE, "测试延时消息 003 --> 5s");
            message.setType(MessageTypeEnum.DELAYED.getIndex());
            message.setSeconds(5);
            messageQueueService.sendDelayMsg(message);
        }

        {
            //消息4  没延迟
            QueueMessage message = new QueueMessage(MessageQueueConstants.QUEUE_HELLO_NAME, MessageQueueConstants.DEFAULT_DELAYED_EXCHANGE,"测试普通消息 004");
            message.setType(MessageTypeEnum.DEFAULT.getIndex());
            messageQueueService.sendDelayMsg(message);
        }
        return "ok";
    }
    //插件化延时队列实现-----end


}
