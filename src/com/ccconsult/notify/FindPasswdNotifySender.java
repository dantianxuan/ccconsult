/**
 * 
 */
package com.ccconsult.notify;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.ccconsult.base.CcException;
import com.ccconsult.pojo.Consultant;
import com.ccconsult.view.CounselorVO;

/**
 * @author jingyu.dan
 * 
 */
public class FindPasswdNotifySender implements NotifySender {

    /** 发送邮件 */
    @Autowired
    private JavaMailSender javaMailSender;

    /** 
     * @see com.ccconsult.notify.NotifySender#notify(java.lang.Object)
     */
    @Override
    public void notify(String senderName, Object playload) {
        MimeMessage mailMessage = javaMailSender.createMimeMessage();
        try {
            // 设置utf-8或GBK编码，否则邮件会有乱码
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");
            messageHelper.setFrom(LOCAL_MAIL);// 发送者
            messageHelper.setSubject("欢迎您找回密码");// 主题
            String passwd = "";
            if (playload instanceof CounselorVO) {
                passwd += ((CounselorVO) playload).getCounselor().getPasswd();
                messageHelper.setTo(((CounselorVO) playload).getCounselor().getEmail());// 接受者
                messageHelper.setText(
                    "<html><head></head><body><h1>您当前申请找回咨询师密码，您当前设定的密码如下，请迅速更换您的密码</h1>密码：<small>"
                            + passwd + "</small></body></html>", true);
            }
            
            if (playload instanceof Consultant) {
                passwd += ((Consultant) playload).getPasswd();
                messageHelper.setTo(((Consultant) playload).getEmail());// 接受者
                messageHelper.setText(
                    "<html><head></head><body><h1>您当前申请找回咨询者密码，您当前设定的密码如下，请迅速更换您的密码</h1>密码：<small>"
                            + passwd + "</small></body></html>", true);
            }
            javaMailSender.send(mailMessage);
        } catch (Exception e) {
            throw new CcException(e, "邮件发送失败,请检查您的邮箱是否合法或稍后再试");
        }
    }
}
