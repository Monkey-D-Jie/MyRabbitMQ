package com.jf_isomc1000.rabbitmqconsumer.direct;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Wangjie
 * @Date: 2020-09-30 13:58
 * @Description: 这里是描述
 * To change this template use File | Settings | File and Templates.
 */
@Component
@RabbitListener(queues = "TestDirectQueue")//监听的队列名称 TestDirectQueue
public class DirectReceiverNew {
    @RabbitHandler
    public void process(Map testMessage) {
        System.out.println("第二个DirectReceiver消费者收到消息  : " + testMessage.toString());
    }
}
