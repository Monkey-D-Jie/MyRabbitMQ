package com.jf.wj.demo.rabbitmq.rabbitmqdeadmsg.MsgEnum;

import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Wangjie
 * @Date: 2020-10-12 15:58
 * @Description: 这里是描述
 * To change this template use File | Settings | File and Templates.
 */
@Getter
public enum MessageTypeEnum {

    DEFAULT("default",0),DELAYED("delayed",1);
    private String typeName;
    private int index;
    MessageTypeEnum(String name, int index) {
        this.typeName = name;
        this.index = index;
    }

}
