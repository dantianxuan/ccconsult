/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.core.notify;

import com.ccconsult.pojo.MobileToken;

/**
 * 咨询师拒绝通知
 * （发送邮件）
 * 
 * @author jingyudan
 * @version $Id: ConsultRejectNotify.java, v 0.1 2014-7-27 上午10:09:16 jingyudan Exp $
 */
public class RegistSmsNotifySender extends AbstractNotifySender {

    /** 
     * @see com.ccconsult.core.notify.NotifySender#notify(java.lang.String, java.lang.Object)
     */
    @Override
    public void notify(String senderName, Object playload) {
        MobileToken mobileToken = (MobileToken) playload;
        sendSMS(mobileToken.getMobile(), TEMPLATE_SMS_TOKEN,
            new String[] { mobileToken.getToken() });
    }
}
