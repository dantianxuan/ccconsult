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

    /** 支付咨询押金 */
    PAY_CONSULT_DEPOSIT(1, "PAY_CONSULT_DEPOSIT", "支付咨询押金"),

    /** 支付咨询师咨询费用 */
    PAY_COUNSELOR_CONSULT_MONEY(2, "PAY_COUNSELOR_CONSULT_MONEY", "支付咨询师咨询费用"),

    /** 支付平台服务费用 */
    PAY_PALTFORM_SERVICE_MONEY(3, "PAY_PALTFORM_SERVICE_MONEY", "支付平台服务费用"),

    /** 退还押金 */
    DEBACK_CONSULT_DEPOSIT(4, "DEBACK_CONSULT_DEPOSIT", "退还押金"),

    /** 解冻担保费用 */
    UN_FREEZE_DEABCK_MONEY(5, "UN_FREEZE_DEABCK_MONEY", "解冻担保费用"),

    /** 冻结咨询押金 */
    FREEZE_CONSULT_DEPOSIT(6, "FREEZE_CONSULT_DEPOSIT", "持有咨询押金"),

    /** 充值到账户 */
    CHARGE_IN_MONEY(7, "CHARGE_IN_MONEY", "充值到账户"),

    /** 转出账户（提现） */
    CHARGE_OUT_MONEY(8, "CHARGE_OUT_MONEY", "转出账户（提现）"),

    /** 退款记录 */
    DRAWBACK_MONEY(9, "DRAWBACK_MONEY", "退款记录"),

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
