/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.enums;

/**
 * 支付状态枚举
 * 
 * 
 * @author jingyu.dan
 * @version $Id: PayStateEnum.java, v 0.1 2014-6-10 下午6:06:53 jingyu.dan Exp $
 */
public enum PayStateEnum {

    /** 等待支付*/
    WAIT_FOR_PAY(1, "WAIT_FOR_PAY", "等待支付"),

    /** 支付成功 */
    PAY_SUCCESS(2, "PAY_SUCCESS", "支付成功"),

    /** 支付失败 */
    PAY_ERROR(3, "PAY_ERROR", "支付失败");

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
    private PayStateEnum(int value, String code, String description) {
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
    public static PayStateEnum getByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (PayStateEnum type : values()) {
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
