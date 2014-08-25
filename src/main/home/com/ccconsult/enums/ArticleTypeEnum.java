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
public enum ArticleTypeEnum {

    /** 网站新闻 */
    WEB_NEWS(1, "WEB_NEWS", "网站新闻"),

    /** 服务方式介绍 */
    SERVICE_INTRO(2, "SERVICE_INTRO", "服务方式介绍"),

    /** 联系我们*/
    CONTACT_US(3, "CONTACT_US", "联系我们"),

    /** 关于我们 */
    ABOAT_US(4, "ABOAT_US", "关于我们"), ;

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
    private ArticleTypeEnum(int value, String code, String description) {
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
    public static ArticleTypeEnum getByValue(int value) {
        for (ArticleTypeEnum type : values()) {
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
