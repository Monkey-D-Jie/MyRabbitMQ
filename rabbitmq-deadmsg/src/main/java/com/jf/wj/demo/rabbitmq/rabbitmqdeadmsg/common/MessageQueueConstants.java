package com.jf.wj.demo.rabbitmq.rabbitmqdeadmsg.common;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Wangjie
 * @Date: 2020-10-12 12:57
 * @Description: 消息队列相关的常量类
 * To change this template use File | Settings | File and Templates.
 */

public class MessageQueueConstants {
    private MessageQueueConstants(){
    }

    //exchange name
    public static final String DEFAULT_EXCHANGE = "KSHOP";

    //DLX QUEUE
    public static final String DEFAULT_DEAD_LETTER_QUEUE_NAME = "kshop.dead.letter.queue";

    //DLX repeat QUEUE 死信转发队列
    public static final String DEFAULT_REPEAT_TRADE_QUEUE_NAME = "kshop.repeat.trade.queue";


    //Hello 测试消息队列名称
    public static final String HELLO_QUEUE_NAME = "HELLO";

    /**
     * 默认即时消息交换机名称
     */
    public static final String DEFAULT_DIRECT_EXCHANGE_NAME = "default.direct.exchange";


    /**
     * 默认延迟交换机
     */
    public static final String DEFAULT_DELAYED_EXCHANGE = "default.delayed.exchange";

    /**
     * 默认延迟消息类型
     */
    public static final String DEFAULT_DELAYED_TYPE_NAME= "x-delayed-message";


    /**
     * 默认作为延时消息转发的接收队列名称
     */
    public static final String DEFAULT_REPEAT_TRADE_REPEAT_QUEUE_NAME = "default.repeat.trade.queue";


    /**
     * hello消息队列名称
     */
    public static final String QUEUE_HELLO_NAME = "app.queue.hello";

}
