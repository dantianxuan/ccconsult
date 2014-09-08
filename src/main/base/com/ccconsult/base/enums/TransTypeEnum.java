/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.base.enums;

/**
 * 评价枚举
 * 
 * @author jingyu.dan
 * @version $Id: AppriseEnum.java, v 0.1 2014-6-10 下午6:06:53 jingyu.dan Exp $
 */
public enum TransTypeEnum {

    /** 充值 */
    CHARGE_IN(1, "CHARGE_IN", "充值"),

    /** 提出 */
    CHARGE_OUT(2, "CHARGE_OUT", "转出到账户"),

    /** 支付咨询押金 */
    PAY_CONSULT_DEPOSIT(3, "PAY_CONSULT_DEPOSIT", "支付咨询押金"),

    /** 支付咨询师咨询费用 */
    PAY_COUNSELOR_CONSULT_MONEY(4, "PAY_COUNSELOR_CONSULT_MONEY", "支付咨询师咨询费用"),

    /** 支付平台服务费用 */
    PAY_PALTFORM_SERVICE_MONEY(5, "PAY_PALTFORM_SERVICE_MONEY", "支付平台服务费用"),

    /** 退换费用 */
    DEABCK_MONEY(6, "DEABCK_MONEY", "支付平台服务费用"),

    ;

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
    private TransTypeEnum(int value, String code, String description) {
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
    public static TransTypeEnum getByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (TransTypeEnum type : values()) {
            if (value.equals(type.getValue())) {
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
