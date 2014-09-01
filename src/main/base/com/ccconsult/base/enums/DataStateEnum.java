/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.base.enums;

/**
 * 订单状态枚举
 * 
 * @author jingyu.dan
 * @version $Id: DataStateEnum.java, v 0.1 2014-6-10 下午6:06:53 jingyu.dan Exp $
 */
public enum DataStateEnum {

    /** 正常 */
    NORMAL(1, "NORMAL", "正常"),

    /** 停用 */
    DISABLE(2, "DISABLE", "停用"),

    /** 删除 */
    DELETE(3, "DELETE", "删除");

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
    private DataStateEnum(int value, String code, String description) {
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
    public static DataStateEnum getByValue(int value) {
        for (DataStateEnum type : values()) {
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
