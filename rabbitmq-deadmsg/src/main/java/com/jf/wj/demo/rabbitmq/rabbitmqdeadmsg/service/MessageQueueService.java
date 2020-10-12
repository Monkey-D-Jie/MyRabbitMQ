package com.jf.wj.demo.rabbitmq.rabbitmqdeadmsg.service;

import com.jf.wj.demo.rabbitmq.rabbitmqdeadmsg.entity.QueueMessage;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Wangjie
 * @Date: 2020-10-12 13:02
 * @Description: 这里是描述
 * To change this template use File | Settings | File and Templates.
 */

public interface MessageQueueService {
    /**
     * 发送消息到队列
     * @param queue 队列名称
     * @param message 消息内容
     */
     void sendDirectMsg(String queueName,String message);

    /**
     * 延迟发送消息到队列
     * @param queue 队列名称
     * @param message 消息内容
     * @param times 延迟时间 单位毫秒
     */
     void sendDeadMsg(String queueName,String message,long times);

    /**
     * 发送消息，返回是否发送成功
     * @param message
     * @return
     */
     void sendDelayMsg(QueueMessage message);
}
