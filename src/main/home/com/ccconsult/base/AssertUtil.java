/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.base;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

/**
 * 
 * @author jingyu.dan
 * @version $Id: AssertUtil.java, v 0.1 2014-5-31 下午11:42:16 jingyu.dan Exp $
 */
public class AssertUtil {

    /**
     * 判断对象不为空
     *
     * @param object 对象
     * @exception ClcException 如果对象为空
     */
    public static void notNull(Object object) {
        if (object == null) {
            throw new CcException();
        }
    }

    /**
     * 判断对象不为空
     *
     * @param object  对象
     * @param message 错误信息
     * @exception ClcException 如果对象为空
     */
    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new CcException(message);
        }
    }

    /**
     * 判断一个Map不为空
     *
     * @param map 映射
     * @param message 错误信息
     * @exception CcException 如果Map为空
     */
    @SuppressWarnings("unchecked")
    public static void notEmpty(Map map, String message) {
        if (CollectionUtils.isEmpty(map)) {
            throw new CcException(message);
        }
    }

    /**
     * 判断一个List不为空
     *
     * @param map 映射
     * @param message 错误信息
     * @exception CcException 如果Map为空
     */
    @SuppressWarnings("unchecked")
    public static void notEmpty(List list, String message) {
        if (CollectionUtils.isEmpty(list)) {
            throw new CcException(message);
        }
    }

    /**
     * 判断一个List不为空
     *
     * @param map 映射
     * @param message 错误信息
     * @exception CcException 如果Map为空
     */
    @SuppressWarnings("unchecked")
    public static void notEmpty(HashSet list, String message) {
        if (CollectionUtils.isEmpty(list)) {
            throw new CcException(message);
        }
    }

    /**
     * 判断一个字符串不为空
     *
     * @param text 字符串
     * @param message 错误信息
     * @exception CcException 如果字符串为空
     */
    public static void notBlank(String text, String message) {
        if (StringUtils.isBlank(text)) {
            throw new CcException(message);
        }
    }

    /**
     * 判断一个表达式为中
     *
     * @param expression    布尔表达式
     * @param message       错误信息
     * @exception CcException 如果表达式不成立
     */
    public static void state(boolean expression, String message) {
        if (!expression) {
            throw new CcException(message);
        }
    }

}
