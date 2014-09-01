/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.core.notify;

/**
 * 短信发送接口 
 * 
 * @author jingyudan
 * @version $Id: SmsSender.java, v 0.1 2014-7-2 下午3:31:03 jingyudan Exp $
 */
public interface NotifySender {

    public static final String LOCAL_MAIL = "ccconsult@163.com";

    /**
     * 发送消息
     * 
     * @param playload
     */
    public void notify(String senderName, Object playload);
}
