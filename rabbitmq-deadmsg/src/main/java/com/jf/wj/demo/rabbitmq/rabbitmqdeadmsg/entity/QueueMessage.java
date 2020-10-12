package com.jf.wj.demo.rabbitmq.rabbitmqdeadmsg.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Wangjie
 * @Date: 2020-10-12 15:47
 * @Description: 这里是描述
 * To change this template use File | Settings | File and Templates.
 */
@Getter
@Setter
public class QueueMessage implements Serializable {
    //交换机
    private String exchange;
    //队列
    private String queueName;
    //类型 ， 主要区分普通消息和延迟消息 ，
    private Integer type;
    //分组
    private Integer group;
    //时间戳
    private Date timestamp;
    //消息内容
    private String message;
    //状态
    private Integer status;
    //重试次数
    private int retry = 0;
    //最大重试次数
    private int maxRetry = 10;
    //延迟的秒数
    private int seconds = 1;

    public QueueMessage(String queueName,String exchange,String message){
        this.queueName = queueName;
        this.exchange = exchange;
        this.message = message;
    }

}
