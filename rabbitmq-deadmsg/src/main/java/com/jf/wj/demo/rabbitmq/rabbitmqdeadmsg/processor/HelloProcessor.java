package com.jf.wj.demo.rabbitmq.rabbitmqdeadmsg.processor;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Wangjie
 * @Date: 2020-10-12 14:20
 * @Description: 这里是描述
 * To change this template use File | Settings | File and Templates.
 */
//@Component
//@RabbitListener(queues = MessageQueueConstants.HELLO_QUEUE_NAME)
public class HelloProcessor {
    @RabbitHandler
    public void process(String content) {
        System.out.println("接受消息:" + content);
    }

}
