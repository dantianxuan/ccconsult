/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.core;

/**
 * 短信发送接口 
 * 
 * @author jingyudan
 * @version $Id: SmsSender.java, v 0.1 2014-7-2 下午3:31:03 jingyudan Exp $
 */
public interface NotifySender {

    public static final String REG_MAIL              = "REG_MAIL";
    public static final String INTERVIEW_ON_SCHEDULE = "INTERVIEW_ON_SCHEDULE";
    public static final String CONSULTANT_FINISHED   = "CONSULTANT_FINISHED";

    /**
     * 发送消息
     * 
     * @param playload
     */
    public void notify(String senderName, Object playload);
}