/**
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.ccconsult.base;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ccconsult.base.enums.DataStateEnum;

/**
 * <pre> ToString基类
 * 
 * 如果作为入参对象继承该类时，请重写toString方法，用于摘要日志打印
 * </pre>
 * @author jingyu.dan
 * @version $Id: ToString.java, v 0.1 2012-11-7 下午8:19:07 jingyu.dan Exp $
 */
public class ToString implements Serializable {

    /**  */
    private static final long serialVersionUID = 1L;

    /** 
     * toString方法重写
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public DataStateEnum getStateEnum(Integer state) {
        return DataStateEnum.getByValue(state);
    }
}
