package com.jf.wj.demo.rabbitmq.rabbitmqdeadmsg.processor;

import com.jf.wj.demo.rabbitmq.rabbitmqdeadmsg.common.MessageQueueConstants;
import com.jf.wj.demo.rabbitmq.rabbitmqdeadmsg.entity.DLXMessage;
import com.jf.wj.demo.rabbitmq.rabbitmqdeadmsg.service.MessageQueueService;
import com.jf.wj.demo.rabbitmq.rabbitmqdeadmsg.util.JsonUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Wangjie
 * @Date: 2020-10-12 13:22
 * @Description: 死信接收处理消费者
 * To change this template use File | Settings | File and Templates.
 */
@Component
@RabbitListener(queues = MessageQueueConstants.DEFAULT_REPEAT_TRADE_QUEUE_NAME)
public class TradeProcessor {
    @Autowired
    private MessageQueueService messageQueueService;

    @RabbitHandler
    public void process(String content) {
        DLXMessage message = JsonUtil.convertJsonCollection(content, DLXMessage.class);
        messageQueueService.sendDirectMsg(message.getQueueName(), message.getContent());
    }


}
