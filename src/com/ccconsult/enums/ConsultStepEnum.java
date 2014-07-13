/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.enums;

/**
 * 订单状态枚举
 * 
 * 创建（支付）->[接受预约]->进行中-[个性化服务流程]->完成咨询
 * 创建（支付）->[拒绝]->已经拒绝
 * 
 * @author jingyu.dan
 * @version $Id: ConsultStepEnum.java, v 0.1 2014-6-10 下午6:06:53 jingyu.dan Exp $
 */
public enum ConsultStepEnum {

    /** 创建状态 */
    CREATE(1, "CREATE", "创建状态"),

    /** 咨询中 */
    ON_CONSULT(2, "ON_CONSULT", "进行中"),

    /** 确认完成 */
    FIHSHED(3, "FIHSHED", "完成咨询"),

    /** 已过期 */
    EXPIRED(4, "EXPIRED", "已过期"),

    /** 已拒绝 */
    REJECT(5, "REJECT", "已拒绝"),

    /** 已删除 */
    DELETE(6, "DELETE", "已删除");

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
