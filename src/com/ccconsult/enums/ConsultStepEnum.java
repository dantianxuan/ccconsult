/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.enums;

/**
 * 订单状态枚举
 * 
 * @author jingyu.dan
 * @version $Id: ConsultStepEnum.java, v 0.1 2014-6-10 下午6:06:53 jingyu.dan Exp $
 */
public enum ConsultStepEnum {

    /** 创建状态 */
    CREATE(1, "CREATE", "创建状态"),

    /** 确认预约 */
    ON_SCHEDULE(2, "ON_SCHEDULE", "确认预约"),

    /** 咨询中 */
    ON_CONSULT(3, "ON_CONSULT", "咨询中"),

    /** 咨询者评价 */
    APPRAISE_CONSULTANT(4, "APPRAISE_CONSULTANT", "咨询者评价"),

    /** 咨询师评价 */
    APPRAISE_COUNSELOR(5, "APPRAISE_COUNSELOR", "咨询师评价"),

    /** 确认完成 */
    FIHSHED(6, "FIHSHED", "完成咨询");

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
    private ConsultStepEnum(int value, String code, String description) {
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
    public static ConsultStepEnum getByValue(int value) {
        for (ConsultStepEnum type : values()) {
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
