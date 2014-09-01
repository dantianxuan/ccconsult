/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.base.enums;

/**
 * 通知枚举
 * 
 * @author jingyu.dan
 * @version $Id: AppriseEnum.java, v 0.1 2014-6-10 下午6:06:53 jingyu.dan Exp $
 */
public enum NotifyTagEnum {

    /** 无需通知 */
    NOT_NEED_NOTIFY(1, "NOT_NEED_NOTIFY", "无需通知"),

    /** 需要通知 */
    NEED_NOTIFY(2, "NEED_NOTIFY", "需要通知"),

    /**通知完成*/
    NITIFYED(3, "NITIFYED", "通知完成"), ;

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
    private NotifyTagEnum(int value, String code, String description) {
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
    public static NotifyTagEnum getByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (NotifyTagEnum type : values()) {
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
