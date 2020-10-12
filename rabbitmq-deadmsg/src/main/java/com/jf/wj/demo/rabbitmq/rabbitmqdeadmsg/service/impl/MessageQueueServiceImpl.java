package com.jf.wj.demo.rabbitmq.rabbitmqdeadmsg.service.impl;

import com.jf.wj.demo.rabbitmq.rabbitmqdeadmsg.MessageException;
import com.jf.wj.demo.rabbitmq.rabbitmqdeadmsg.MsgEnum.MessageTypeEnum;
import com.jf.wj.demo.rabbitmq.rabbitmqdeadmsg.common.MessageQueueConstants;
import com.jf.wj.demo.rabbitmq.rabbitmqdeadmsg.entity.DLXMessage;
import com.jf.wj.demo.rabbitmq.rabbitmqdeadmsg.entity.QueueMessage;
import com.jf.wj.demo.rabbitmq.rabbitmqdeadmsg.service.MessageQueueService;
import com.jf.wj.demo.rabbitmq.rabbitmqdeadmsg.util.DateUtil;
import com.jf.wj.demo.rabbitmq.rabbitmqdeadmsg.util.JsonUtil;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Wangjie
 * @Date: 2020-10-12 13:02
 * @Description: 这里是描述
 * To change this template use File | Settings | File and Templates.
 */
@Service
public class MessageQueueServiceImpl implements MessageQueueService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendDirectMsg(String queueName, String msg) {
        System.out.println("当前接收信息的时间为--->"+ DateUtil.convert(DateUtil.DATE_FORMAT,new Date()));
        rabbitTemplate.convertAndSend(MessageQueueConstants.DEFAULT_EXCHANGE,queueName, msg);
    }

    @Override
    public void sendDeadMsg(String queueName, String msg, long times) {
        DLXMessage dlxMessage = new DLXMessage(queueName,msg,times);
        MessagePostProcessor processor = message -> {
            message.getMessageProperties().setExpiration(times + "");
            return message;
        };
        dlxMessage.setExchange(MessageQueueConstants.DEFAULT_EXCHANGE);
        rabbitTemplate.convertAndSend(MessageQueueConstants.DEFAULT_EXCHANGE, MessageQueueConstants.DEFAULT_DEAD_LETTER_QUEUE_NAME, JsonUtil.convertObjectJson(dlxMessage), processor);
    }

    @Override
    public void sendDelayMsg(QueueMessage message) {
//        this.checkMessage(message);
        //即时消息
        if (message.getType() == MessageTypeEnum.DEFAULT.getIndex()) {
            this.sendMessage(message.getExchange(), message.getQueueName(), message.getMessage());
        }
        //延时消息
        if (message.getType() == MessageTypeEnum.DELAYED.getIndex()) {
            sendTimeMessage(message);
        }
    }

        private void sendMessage(String exchange,String queueName,String msg){
            rabbitTemplate.convertAndSend(exchange,queueName, msg);
        }

        public void sendTimeMessage(QueueMessage message) {
            int seconds = message.getSeconds();
            // 直接发送，无需进入死信队列
            if(seconds <= 0){
                sendMessage(message.getExchange(),message.getQueueName(), message.getMessage());
            }else{
                //rabbit默认为毫秒级
                long times = seconds * 1000;
                MessagePostProcessor processor = s -> {
                    s.getMessageProperties().setHeader("x-delay", times);
                    return s;
                };
                //设置 x-delay 之后  将消息投递到专属交换机 ， 转发队列
                rabbitTemplate.convertAndSend(MessageQueueConstants.DEFAULT_DELAYED_EXCHANGE,MessageQueueConstants.DEFAULT_REPEAT_TRADE_QUEUE_NAME, JsonUtil.convertObjectJson(message), processor);
            }
        }

        private void checkMessage(QueueMessage message){
            if (StringUtils.isEmpty(message.getExchange())) {
                throw new MessageException(10, "发送消息格式错误: 消息交换机(exchange)不能为空!");
            }
            if(message.getGroup() == null){
                throw new MessageException(10, "发送消息格式错误: 消息组(group)不能为空!");
            }
            if(message.getType() == null){
                throw new MessageException(10, "发送消息格式错误: 消息类型(type)不能为空!");
            }
            if(message.getStatus() == null){
                throw new MessageException(10, "发送消息格式错误: 消息状态(status)不能为空!");
            }
            if(StringUtils.isEmpty(message.getQueueName())){
                throw new MessageException(10, "发送消息格式错误: 消息目标名称(queueName)不能为空!");
            }
            if (StringUtils.isEmpty(message.getMessage())) {
                throw new MessageException(10, "发送消息格式错误: 消息内容(message)不能为空!");
            }
        }

}
