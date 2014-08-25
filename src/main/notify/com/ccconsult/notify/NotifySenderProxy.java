/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.notify;

import java.util.HashMap;
import java.util.Map;

/**
 * 短信发送接口 
 * 
 * @author jingyudan
 * @version $Id: SmsSender.java, v 0.1 2014-7-2 下午3:31:03 jingyudan Exp $
 */
public class NotifySenderProxy implements NotifySender {

    private Map<String, NotifySender> notifySenders = new HashMap<String, NotifySender>();

    /**
     * 发送消息
     * 
     * @param playload
     */
    @Override
    public void notify(String senderName, Object playload) {
        notifySenders.get(senderName).notify(senderName, playload);
    }

    public void setNotifySenders(Map<String, NotifySender> notifySenders) {
        this.notifySenders = notifySenders;
    }

}
