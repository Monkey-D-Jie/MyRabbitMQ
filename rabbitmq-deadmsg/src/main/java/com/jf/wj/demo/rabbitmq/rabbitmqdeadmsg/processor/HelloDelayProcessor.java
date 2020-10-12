package com.jf.wj.demo.rabbitmq.rabbitmqdeadmsg.processor;

import com.jf.wj.demo.rabbitmq.rabbitmqdeadmsg.common.MessageQueueConstants;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Wangjie
 * @Date: 2020-10-12 14:20
 * @Description: 这里是描述
 * To change this template use File | Settings | File and Templates.
 */
@Component
@RabbitListener(queues = MessageQueueConstants.QUEUE_HELLO_NAME)
public class HelloDelayProcessor {
    @RabbitHandler
    public void process(String content) {
        System.out.println("Hello 接受消息：" + content );
        System.out.println("发送延迟消息：" + (System.currentTimeMillis() / 1000) );
        System.out.println("---------");
    }


}
