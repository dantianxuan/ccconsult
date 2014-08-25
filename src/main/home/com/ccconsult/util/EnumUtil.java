package com.ccconsult.util;

/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2011 All Rights Reserved.
 */

import java.lang.reflect.Method;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.WeakHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 */
@SuppressWarnings("unchecked")
public class EnumUtil implements InitializingBean {

    /** LOG */
    private static final Log                logger         = LogFactory.getLog(EnumUtil.class);

    /** getValue 方法映射 */
    private static Map<Class<Enum>, String> valueMethodMap = new WeakHashMap<Class<Enum>, String>();

    /** getMessage方法映射 */
    private static Map<Class<Enum>, String> msgMethodMap   = new WeakHashMap<Class<Enum>, String>();

    /**
     * @throws Exception
     * 
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
    }

    /**
     * 遍历枚举
     * 
     * @param clazz
     * @return
     */
    public static Object[] getEnumList(String clazz) {
        Class<Enum> c = getEnumClass(clazz);

        if (c != null) {
            EnumSet set = EnumSet.allOf(c);

            if (set != null) {
                return set.toArray();
            }
        }

        return null;
    }

    /**
     * <p>
     * 将枚举类型转换为Map<value,message>的形式以便于页面显示
     * </p>
     * 
     * @param enumName
     * @return Map<value,message>
     */
    public static Map getEnumMap(String enumName) {
        if (StringUtil.isBlank(enumName)) {
            return null;
        }
        Map enumMap = new LinkedHashMap();

        Object[] enums = getEnumList(enumName);
        if (enums == null || enums.length == 0) {
            return null;
        }

        for (int i = 0; i < enums.length; i++) {
            try {
                Class clazz = enums[i].getClass();
                Method getValueMethod = null;
                try {
                    getValueMethod = clazz.getMethod("getCode");
                } catch (NoSuchMethodException e) {
                    getValueMethod = clazz.getMethod("getValue");
                }
                Object value = getValueMethod.invoke(enums[i]);
                Method getMsgMethod = clazz.getMethod("getDescription");
                Object msg = getMsgMethod.invoke(enums[i]);
                enumMap.put(value, msg);
            } catch (NoSuchMethodException e) {
                logger.error("获取供页面显示的枚举值时出现错误，枚举类型没有getValue()或getMessage()方法！传入枚举类型：" + enumName,
                    e);
                continue;
            } catch (Exception e) {
                logger.error("获取供页面显示的枚举值时出现错误！", e);
                continue;
            }
        }

        return enumMap;
    }

    /**
     * 根据 枚举类名 和 属性code 获取枚举
     * 
     * @param clazz
     * @param code
     * @return
     */
    public static Enum getByCode(String clazz, String code) {
        Class<Enum> c = getEnumClass(clazz);

        if (c != null) {
            String methodName = valueMethodMap.get(c);
            if (StringUtil.isBlank(methodName)) {
                methodName = "getCode";
            }

            try {

                Method m = c.getMethod(methodName, new Class[0]);
                EnumSet set = EnumSet.allOf(c);

                if (set != null) {
                    Object[] o = set.toArray();

                    for (Object item : o) {
                        Object r = m.invoke(item);

                        if (code.equals(r)) {
                            return (Enum) item;
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("处理异常", e);
            }
        }
        return null;
    }

    /**
     * 根据 枚举类名 和 属性value 获取枚举
     * 
     * @param clazz
     * @param value
     *            <code>Integer</code>类型
     * @return
     */
    public static Enum getByValue(String clazz, Integer value) {
        Class<Enum> c = getEnumClass(clazz);

        if (c != null) {
            String methodName = "getByValue";
            Method m = null;
            // 有些Enum只有getCode方法
            try {
                m = c.getMethod(methodName, Integer.class);
            } catch (SecurityException e1) {
                logger.error("处理异常", e1);
            } catch (NoSuchMethodException e1) {
                logger.error("处理异常", e1);
                methodName = "getCode";
            }
            try {
                if (m == null) {
                    return null;
                }
                return (Enum) m.invoke(c, value);
            } catch (Exception e) {
                logger.error("处理异常", e);
            }
        }
        return null;
    }

    /**
     * 根据 枚举类名 和 属性msg 获取枚举
     * 
     * @param clazz
     * @param msg
     * @return
     */
    public static Enum getByMessage(String clazz, String msg) {
        Class<Enum> c = getEnumClass(clazz);

        if (c != null) {
            String methodName = msgMethodMap.get(c);
            if (StringUtil.isBlank(methodName)) {
                methodName = "getMessage";
            }

            Method m = null;
            try {
                m = c.getMethod(methodName, new Class[0]);
            } catch (SecurityException e) {
                logger.error("处理异常", e);
            } catch (NoSuchMethodException e) {
                methodName = "getDsc";
                logger.error("处理异常", e);
            }

            try {

                if (m == null) {
                    m = c.getMethod(methodName, new Class[0]);
                }
                msgMethodMap.put(c, methodName);
                EnumSet set = EnumSet.allOf(c);

                if (set != null) {
                    Object[] o = set.toArray();

                    for (Object item : o) {
                        Object r = m.invoke(item);

                        if (msg.equals(r)) {
                            return (Enum) item;
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("处理异常", e);
            }
        }
        return null;
    }

    // /////////////////////////////////////////////// public - private
    /**
     * 根据类名 或 类全路径名 获取对应枚举Class
     * 
     * @param className
     *            类名
     * @return Class<Enum>
     */
    public static Class<Enum> getEnumClass(String className) {
        Class<Enum> c = null;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            if (StringUtil.contains(className, ".")) {// 针对传全路径类名
                c = (Class<Enum>) classLoader.loadClass(className);
            }
        } catch (Exception ee) {
            try {
                c = (Class<Enum>) Class.forName(className);
            } catch (Exception e) {
                logger.error("获取出现异常", e);
            }
        }
        return c;
    }

}