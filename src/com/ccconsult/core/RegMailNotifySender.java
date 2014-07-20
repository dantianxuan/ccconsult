/**
 * 
 */
package com.ccconsult.core;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.ccconsult.base.CcException;
import com.ccconsult.pojo.RegMail;
import com.ccconsult.util.ResourceUtil;

/**
 * @author jingyu.dan
 * 
 */
public class RegMailNotifySender implements NotifySender {

    /** 发送邮件 */
    @Autowired
    private JavaMailSender javaMailSender;

    /** 
     * @see com.ccconsult.core.NotifySender#notify(java.lang.Object)
     */
    @Override
    public void notify(String senderName, Object playload) {
        RegMail regMail = (RegMail) playload;
        MimeMessage mailMessage = javaMailSender.createMimeMessage();
        try {
            // 设置utf-8或GBK编码，否则邮件会有乱码
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");
            messageHelper.setTo(regMail.getMail());// 接受者
            messageHelper.setFrom("dantianxuan@163.com");// 发送者
            messageHelper.setSubject("欢迎您注册真咨网");// 主题
            // 邮件内容，注意加参数true，表示启用html格式
            String link = ResourceUtil.getByKey("webapp_domain") + "regist/regCounselor.htm?token="
                          + regMail.getToken();
            messageHelper.setText(
                "<html><head></head><body><h1>欢迎注册成为咨询师，请使用如下链接完成注册！<br/>" + "<a href=" + link
                        + ">" + link + "</a>" + "</h1></body></html>", true);
            javaMailSender.send(mailMessage);
        } catch (Exception e) {
            throw new CcException(e, "邮件发送失败,请检查您的邮箱是否合法或稍后再试");
        }
    }

}
