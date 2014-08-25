/**
 * 
 */
package com.ccconsult.notify;

import com.ccconsult.pojo.RegMail;
import com.ccconsult.util.ResourceUtil;

/**
 * @author jingyu.dan
 * 
 */
public class RegMailNotifySender extends AbstractNotifySender {

    /** 
     * @see com.ccconsult.notify.NotifySender#notify(java.lang.Object)
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
