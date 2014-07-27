/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.enums;

/**
 * 通知枚举
 * 
 * @author jingyu.dan
 * @version $Id: AppriseEnum.java, v 0.1 2014-6-10 下午6:06:53 jingyu.dan Exp $
 */
public enum NotifySenderEnum {

    /** 注册面试官通知处理器 */
    REG_MAIL_NOTIFY(1, "REG_MAIL_NOTIFY", "注册面试官消息发送器"),

    /** 咨询师已经完成预约通知 */
    ON_SCHEDULE_NOTIFY(2, "ON_SCHEDULE_NOTIFY", "咨询师已经完成预约通知"),

    /**咨询就行中通知(支付完成) */
    CONSULT_WORKON_NOTIFY(3, "CONSULT_WORKON_NOTIFY", "咨询就行中通知(支付完成)"),

    /**咨询被拒绝通知 */
    CONSULT_REJECT_NOTIFY(4, "CONSULT_REJECT_NOTIFY", "咨询被拒绝通知"),

    /**找回密码通知 */
    FINDPASSWD_NOTIFY(5, "FINDPASSWD_NOTIFY", "找回密码通知"),

    /** 一般 */
    WORK_TASK_NOTIFY(6, "WORK_TASK_NOTIFY", "工作任务通知"), ;

    /** 枚举码 */
    private int    value;

    /** 编码 */
    private String code;

    /** 描述 */
    private String description;

    /**
     * 私有构造方法
     * @param value         枚举值
     * @param code          枚举code
     * @param description   枚举描述
     */
    private NotifySenderEnum(int value, String code, String description) {
        this.value = value;
        this.code = code;
        this.description = description;
    }

    /**
     * 通过枚举<code>value</code>获得枚举。
     * 
     * @param value 枚举值
     * @return      枚举对象
     */
    public static NotifySenderEnum getByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (NotifySenderEnum type : values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public int getValue() {
        return value;
    }
}
