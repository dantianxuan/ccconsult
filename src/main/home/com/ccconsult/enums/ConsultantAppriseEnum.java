/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.enums;

/**
 * 评价枚举
 * 
 * @author jingyu.dan
 * @version $Id: AppriseEnum.java, v 0.1 2014-6-10 下午6:06:53 jingyu.dan Exp $
 */
public enum ConsultantAppriseEnum {

    /** 非常好 */
    VERY_GOOD(1, "VERY_GOOD", "非常优秀的咨询者"),

    /** 很好 */
    GOOD(2, "GOOD", "还不错，基本工作应该没啥问题"),

    /** 一般 */
    NORMAL(3, "NORMAL", "一般咨询者"),

    /** 差评 */
    BAD(4, "BAD", "专业水准有所欠缺"), ;

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
    private ConsultantAppriseEnum(int value, String code, String description) {
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
    public static ConsultantAppriseEnum getByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (ConsultantAppriseEnum type : values()) {
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
