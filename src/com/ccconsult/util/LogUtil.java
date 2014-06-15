/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2012 All Rights Reserved.
 */
package com.ccconsult.util;

import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

/**
 * 
 * 规范化日志打印工具<br>
 * <p>
 * <ol>
 * <li>DEBUG 输出详细的应用状态
 * <li>INFO 输出应用生命周期中的<b>正常重要事件</b>
 * <li>WARN 输出应用中的<b>可预期的异常事件</b>
 * <li>ERROR 输出应用中的<b>未预期的异常事件</b>
 * </ol>
 * </p>
 * 
 * @author jingyu.dan
 * @version $Id: LogUtil.java, v 0.1 2012-11-7 下午8:39:46 jingyu.dan Exp $
 */
public class LogUtil {

    /** 日志的信息分割符号 */
    public static final String LOG_SEPATOR    = ",";

    /** 左括号 */
    public static final String LEFT_BRACKETS  = "(";

    /** 右括号 */
    public static final String RIGHT_BRACKETS = ")";

    /** 右括号 */
    public static final String SUCCESS        = "Y";

    /** 右括号 */
    public static final String ERROR          = "N";

    /**
     * 禁用构造函数
     */
    private LogUtil() {
        // 禁用构造函数
    }

    /**
     * 生成<font color="blue">调试</font>级别日志<br>
     * 可处理任意多个输入参数，并避免在日志级别不够时字符串拼接带来的资源浪费
     * 
     * @param logger
     *            日志打印
     * @param obj
     *            参数对象
     */
    public static void debug(Logger logger, Object... obj) {

        if (logger != null) {
            if (logger.isDebugEnabled()) {
                logger.debug(getLogString(obj));
            }
        }

    }

    /**
     * 生成<font color="blue">通知</font>级别日志<br>
     * 可处理任意多个输入参数，并避免在日志级别不够时字符串拼接带来的资源浪费
     * 
     * @param logger
     *            日志打印
     * @param obj
     *            参数对象
     */
    public static void info(Logger logger, Object... obj) {

        if (logger != null) {
            if (logger.isInfoEnabled()) {
                logger.info(getLogString(obj));
            }
        }

    }

    /**
     * 可以生成固定格式的日志 格式 <font color="blue">(x,y,z)</font>方法会使用括号包含，逗号分隔
     * 
     * @param logger
     *            日志
     * @param level
     *            日志等级
     * @param attrs
     *            参数集合
     */
    public static void log(Logger logger, Level level, List<String> attrs) {

        if (logger.isEnabledFor(level)) {
            StringBuffer info = new StringBuffer();
            if (CollectionUtils.isEmpty(attrs)) {
                return;
            }
            info.append(LEFT_BRACKETS);
            for (String attr : attrs) {
                info.append(attr);
                info.append(LOG_SEPATOR);
            }
            info.setCharAt(info.length() - 1, RIGHT_BRACKETS.toCharArray()[0]);
            logger.log(level, info.toString());
        }
    }

    /**
     * 生成<font color="brown">警告</font>级别日志<br>
     * 可处理任意多个输入参数，并避免在日志级别不够时字符串拼接带来的资源浪费
     * 
     * @param logger
     *            日志打印
     * @param obj
     *            参数对象
     */
    public static void warn(Logger logger, Object... obj) {

        if (logger != null) {
            logger.warn(getLogString(obj));
        }

    }

    /**
     * 生成<font color="brown">警告</font>级别日志<br>
     * 可处理任意多个输入参数，并避免在日志级别不够时字符串拼接带来的资源浪费
     * 
     * @param logger
     *            日志打印
     * @param obj
     *            参数对象
     */
    public static void warn(Logger logger, Throwable t, Object... obj) {

        if (logger != null) {
            logger.warn(getLogString(obj), t);
        }

    }

    /**
     * 生成<font color="red">警告</font>级别日志<br>
     * 针对三个参数的日志打印：logger，信息，异常堆栈
     * 
     * @param logger
     * @param obj
     * @param t
     */
    public static void warn(Logger logger, Object obj, Throwable t) {

        if (logger != null) {
            logger.warn(getLogString(obj), t);
        }
    }

    /**
     * 生成<font color="red">错误</font>级别日志<br>
     * 可处理任意多个输入参数，并避免在日志级别不够时字符串拼接带来的资源浪费
     * 
     * @param logger
     *            日志对象
     * @param obj
     *            参数对象
     */
    public static void error(Logger logger, Throwable t, Object... obj) {

        if (logger != null) {
            logger.error(getLogString(obj), t);
        }
    }

    /**
     * 生成<font color="red">错误</font>级别日志<br>
     * 可处理任意多个输入参数，并避免在日志级别不够时字符串拼接带来的资源浪费
     * 
     * @param logger
     *            日志对象
     * @param obj
     *            参数对象
     */
    public static void error(Logger logger, Object... obj) {

        if (logger != null) {
            logger.error(getLogString(obj));
        }
    }

    /**
     * 生成<font color="red">错误</font>级别日志<br>
     * 针对三个参数的日志打印：logger，信息，异常堆栈
     * 
     * @param logger
     * @param obj
     * @param t
     */
    public static void error(Logger logger, Object obj, Throwable t) {

        if (logger != null) {
            logger.error(getLogString(obj), t);
        }
    }

    /**
     * 生成输出到日志的字符串
     * 
     * @param obj
     *            任意个要输出到日志的参数
     * @return 日志字符串
     */
    private static String getLogString(Object... obj) {

        StringBuilder log = new StringBuilder();
        for (Object o : obj) {
            log.append(o == null ? "-" : o);
            log.append(LOG_SEPATOR);
        }
        return log.toString();
    }
}
