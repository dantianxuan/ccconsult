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
public enum ScheduleTypeEnum {

    /** 预约时间 */
    SCHEDULE_TIME(1, "SCHEDULE_TIME", "预约时间"),

    /** 预约地点 */
    SCHEDULE_ON_PLACE(2, "SCHEDULE_ON_PLACE", "预约地点"),

    /** 很好 */
    NO_SCHEDULE(3, "NO_SCHEDULE", "无需预约"), ;

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
    private ScheduleTypeEnum(int value, String code, String description) {
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
    public static ScheduleTypeEnum getByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (ScheduleTypeEnum type : values()) {
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
