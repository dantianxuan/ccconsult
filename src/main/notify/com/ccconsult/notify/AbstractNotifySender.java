/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.notify;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.ccconsult.base.CcException;
import com.ccconsult.dao.InnerMailDAO;

/**
 * 抽象通知器
 * 
 * @author jingyudan
 * @version $Id: AbstractNotifySender.java, v 0.1 2014-7-27 上午9:41:31 jingyudan Exp $
 */
public class AbstractNotifySender implements NotifySender {

    /** 发送邮件 */
    @Autowired
    protected JavaMailSender javaMailSender;

    /** 站内信通知 */
    @Autowired
    protected InnerMailDAO   innerMailDAO;

    /** 
     * @see com.ccconsult.notify.NotifySender#notify(java.lang.String, java.lang.Object)
     */
    @Override
    public void notify(String senderName, Object playload) {
    }

    /** 
     * @see com.ccconsult.notify.NotifySender#notify(java.lang.Object)
     */
    protected void sendEmail(String mailContext, String title, String from, String to) {
        MimeMessage mailMessage = javaMailSender.createMimeMessage();
        try {
            // 设置utf-8或GBK编码，否则邮件会有乱码
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");
            messageHelper.setFrom(from);// 发送者
            messageHelper.setTo(to);//目的地
            messageHelper.setSubject(title);// 主题
            messageHelper.setText(mailContext, true);
            javaMailSender.send(mailMessage);
        } catch (Exception e) {
            throw new CcException(e, "邮件发送失败,请检查您的邮箱是否合法或稍后再试");
        }
    }

}
