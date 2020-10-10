package com.jf_isomc1000.rabbitmqconsumer.topic;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Wangjie
 * @Date: 2020-10-09 15:29
 * @Description: 这里是描述
 * To change this template use File | Settings | File and Templates.
 */
@Component
@RabbitListener(queues = "topic.woman")
public class TopicTotalReceiver {
    @RabbitHandler
    public void process(Map testMessage) {
        System.out.println("TopicTotalReceiver消费者收到消息  : " + testMessage.toString());
    }

}
