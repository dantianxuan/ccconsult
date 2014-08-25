/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.base.enums;

/**
 * 订单状态枚举
 * 
 * @author jingyu.dan
 * @version $Id: AppriseRelTypeEnum.java, v 0.1 2014-6-10 下午6:06:53 jingyu.dan Exp $
 */
public enum FileTypeEnum {

    /** 用户头像 */
    USER_PHOTO(1, "USER_PHOTO", "UPLOAD/USER_PHOTO", "用户头像"),

    /** 服务图片 */
    SERVICE_PHOTO(2, "SERVICE_PHOTO", "UPLOAD/SERVICE_PHOTO", "服务图片"),

    /** 公司图片 */
    COMPANY_PHOTO(23, "COMPANY_PHOTO", "UPLOAD/COMPANY_PHOTO", "公司图片"),

    /** 简历文件 */
    RESUME(10, "RESUME", "UPLOAD/RESUME", "简历文件");

    /** 枚举码 */
    private int    value;

    /** 编码 */
    private String code;

    /** 编码 */
    private String basePath;

    /** 描述 */
    private String description;

    /**
     * 私有构造方法
     * @param value         枚举值
     * @param code          枚举code
     * @param description   枚举描述
     */
    private FileTypeEnum(int value, String code, String basePath, String description) {
        this.value = value;
        this.code = code;
        this.basePath = basePath;
        this.description = description;
    }

    /**
     * 通过枚举<code>value</code>获得枚举。
     * 
     * @param value 枚举值
     * @return      枚举对象
     */
    public static FileTypeEnum getByValue(int value) {
        for (FileTypeEnum type : values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        return null;
    }

    /**
     * 通过枚举<code>value</code>获得枚举。
     * 
     * @param value 枚举值
     * @return      枚举对象
     */
    public static FileTypeEnum getByCode(String code) {
        for (FileTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
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

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
