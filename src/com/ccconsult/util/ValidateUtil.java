/**
 * CCONSULT.ME Inc.
 * Copyright (c) 2004-2013 All Rights Reserved.
 */
package com.ccconsult.util;

import java.util.regex.Pattern;

/**
 * 个人信息校验
 * 
 * @author jingyu.dan
 * @version $Id: ValidateUtil.java, v 0.1 2013-7-24 下午07:21:50  $
 */
public class ValidateUtil {

    /** 大陆手机号验证正则表达式 */
    private static final Pattern CHINA_MOBILE_REGEX = Pattern
                                                        .compile("^(([+]?[0]{0,2}86)|([+]?0{0,2}86-))?1(3|4|5|7|8)\\d{9}$");

    private static final Pattern EMAIL_REGEX        = Pattern
                                                        .compile("[a-zA-Z0-9_\\-\\.]+@[a-zA-Z0-9]+(\\.(com|cn|org|edu|hk))");

    /**
     * 验证是否是大陆手机号
     * 
     * @param mobile 手机号
     * @return       格式正确与否
     */
    public static boolean isMobile(String mobile) {
        if (StringUtil.isNotBlank(mobile) && CHINA_MOBILE_REGEX.matcher(mobile).find()) {
            return true;
        }
        return false;
    }

    /**
     * 验证是否手机邮箱
     * 
     * @param email  邮箱格式
     * @return       格式正确与否
     */
    public static boolean isEmail(String email) {
        if (StringUtil.isNotBlank(email) && EMAIL_REGEX.matcher(email).find()) {
            return true;
        }
        return false;
    }

}
