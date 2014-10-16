/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.core.notify;

import com.ccconsult.base.util.ResourceUtil;
import com.ccconsult.pojo.RegMail;

/**
 * 用户注册信箱发送
 * 
 * @author jingyu.dan
 * 
 */
public class RegMailNotifySender extends AbstractNotifySender {

    /** 
     * 咨询师使用邮箱就行注册
     * 
     * @see com.ccconsult.core.notify.NotifySender#notify(java.lang.Object)
     */
    @Override
    public void notify(String senderName, Object playload) {
        RegMail regMail = (RegMail) playload;
        // 邮件内容，注意加参数true，表示启用html格式
        String link = ResourceUtil.getByKey("webapp_domain") + "regist/regCounselor.htm?token="
                      + regMail.getToken();
        String mailContext = "<html><head></head><body><h1>欢迎注册成为咨询师，请使用如下链接完成注册！<br/>"
                             + "<a href=" + link + ">" + link + "</a>" + "</h1></body></html>";
        sendEmail(mailContext, "欢迎您注册真咨网", LOCAL_MAIL, regMail.getMail());
    }
}
