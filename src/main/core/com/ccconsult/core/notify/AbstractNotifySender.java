/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.core.notify;

import java.util.HashMap;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.ccconsult.base.CcException;
import com.ccconsult.base.CcLogger;
import com.ccconsult.base.util.LogUtil;
import com.cloopen.rest.sdk.CCPRestSDK;

/**
 * 抽象通知器
 * 
 * @author jingyudan
 * @version $Id: AbstractNotifySender.java, v 0.1 2014-7-27 上午9:41:31 jingyudan Exp $
 */
public class AbstractNotifySender implements NotifySender {
    /** 发送邮件 */
    @Autowired
    protected JavaMailSender      javaMailSender;
    /**短信模板，格式为：【真咨网】您当前的验证码为{1},请输入您获取的最近一条验证码，验证码1小时内有效。*/
    protected final static String TEMPLATE_SMS_TOKEN      = "3751";
    /**短信模板，格式为：【真咨网】您好{1}，当前您今天有{2}次预约请求需要处理 */
    protected final static String TEMPLATE_CONSULT_NOTIFY = "3391";
    /**local邮件地址*/
    public static final String    LOCAL_MAIL              = "ccconsult@163.com";

    /**
     * 发送短信信箱
     * 
     * 
     * @param mobile
     * @param templateId
     * @param message
     */
    protected void sendSMS(String mobile, String templateId, String[] message) {
        HashMap<String, Object> result = null;
        CCPRestSDK restAPI = new CCPRestSDK();
        restAPI.init("sandboxapp.cloopen.com", "8883");// 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
        restAPI.setAccount("8a48b5514767145d01477fcf5f2907d4", "72a27e3d8d314aaab7f495ffb5cde4e5");// 初始化主帐号名称和主帐号令牌
        restAPI.setAppId("8a48b55147d7c67d0147d88b473b018e");// 初始化应用ID
        result = restAPI.sendTemplateSMS(mobile, templateId, message);
        if ("000000".equals(result.get("statusCode"))) {
            return;
        } else {
            //异常返回输出错误码和错误信息
            LogUtil.info(CcLogger.smsDigest,
                "错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
            throw new CcException("对不起短信发送失败，请稍后再试:" + result.get("statusMsg"));
        }
    }

    /** 
     * @see com.ccconsult.core.notify.NotifySender#notify(java.lang.String, java.lang.Object)
     */
    @Override
    public void notify(String senderName, Object playload) {
    }

    /** 
     * @see com.ccconsult.core.notify.NotifySender#notify(java.lang.Object)
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
