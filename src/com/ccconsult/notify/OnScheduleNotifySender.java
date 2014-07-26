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
import com.ccconsult.dao.ConsultantDAO;
import com.ccconsult.pojo.Consultant;
import com.ccconsult.pojo.Interview;
import com.ccconsult.util.DateUtil;

/**
 * 预约邮件发送器
 * 
 * @author jingyudan
 * @version $Id: OnScheduleNotifySender.java, v 0.1 2014-7-2 下午10:26:28 jingyudan Exp $
 */
public class OnScheduleNotifySender implements NotifySender {

    /** 发送邮件 */
    @Autowired
    private JavaMailSender javaMailSender;
    /** 发送邮件 */
    @Autowired
    private ConsultantDAO  consultantDAO;

    /** 
     * @see com.ccconsult.notify.NotifySender#notify(java.lang.Object)
     */
    @Override
    public void notify(String senderName, Object playload) {

        MimeMessage mailMessage = javaMailSender.createMimeMessage();
        try {
            Interview interview = (Interview) playload;
            Consultant consultant = consultantDAO.findById(interview.getConsultantId());
            // 设置utf-8或GBK编码，否则邮件会有乱码
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");
            messageHelper.setTo(consultant.getEmail());// 接受者
            messageHelper.setFrom(LOCAL_MAIL);// 发送者
            messageHelper.setSubject("欢迎使用真咨网，您的预约信息");// 主题

            String content = "<html><head></head><body><h1>欢迎使用真咨网！</h1>"
                             + "你预约的咨询师已经接受了您的预约，预约时间为<span style='font-size:18px;color:red;'>"
                             + DateUtil.format(interview.getGmtShceduleBegin(),
                                 DateUtil.noSecondFormat)
                             + "-"
                             + DateUtil.format(interview.getGmtScheduleEnd(),
                                 DateUtil.noSecondFormat) + "</span></body></html>";
            messageHelper.setText(content, true);
            javaMailSender.send(mailMessage);
        } catch (Exception e) {
            throw new CcException(e, "邮件发送失败");
        }
    }

}
