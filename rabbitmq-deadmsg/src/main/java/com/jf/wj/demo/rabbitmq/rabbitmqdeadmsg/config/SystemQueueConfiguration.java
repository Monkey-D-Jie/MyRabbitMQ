package com.jf.wj.demo.rabbitmq.rabbitmqdeadmsg.config;

import com.jf.wj.demo.rabbitmq.rabbitmqdeadmsg.common.MessageQueueConstants;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Wangjie
 * @Date: 2020-10-12 15:41
 * @Description: 系统队列配置
 *  * 主要定义默认交换机bean以及延迟消息相关队列
 * To change this template use File | Settings | File and Templates.
 */
@Configuration
public class SystemQueueConfiguration {
    /**
     * 默认及时消息交换机
     * @return
     */
    @Bean("defaultDirectExchange")
    public DirectExchange defaultDirectExchange() {
        return new DirectExchange(MessageQueueConstants.DEFAULT_DIRECT_EXCHANGE_NAME, true, false);
    }

    /**
     * 配置默认延迟的 交换机
     */
    @Bean("defaultDelayedExchange")
    public CustomExchange defaultDelayedExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        //第二个参数是固定的  x-delayed-message
        return new CustomExchange(MessageQueueConstants.DEFAULT_DELAYED_EXCHANGE, MessageQueueConstants.DEFAULT_DELAYED_TYPE_NAME, true, false, args);
    }

    /**
     * 默认延迟消息接受并转发消息队列
     * @return
     */
    @Bean
    public Queue defaultRepeatTradeQueue() {
        return new Queue(MessageQueueConstants.DEFAULT_REPEAT_TRADE_REPEAT_QUEUE_NAME,true,false,false);
    }

    /**
     * 建立延迟转发队列和交换机之间的关系
     * @return
     */
    @Bean
    public Binding defaultRepeatTradeBinding() {
        return BindingBuilder.bind(defaultRepeatTradeQueue()).to(defaultDelayedExchange()).with(MessageQueueConstants.DEFAULT_REPEAT_TRADE_REPEAT_QUEUE_NAME).noargs();
    }

}
