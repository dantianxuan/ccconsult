/**
 * 
 */
package com.ccconsult.notify;

import com.ccconsult.pojo.Consultant;
import com.ccconsult.web.view.CounselorVO;

/**
 * 寻找密码通知发送器
 * 
 * @author jingyu.dan
 * 
 */
public class FindPasswdNotifySender extends AbstractNotifySender {

    /** 
     * @see com.ccconsult.notify.NotifySender#notify(java.lang.Object)
     */
    @Override
    public void notify(String senderName, Object playload) {

        String passwd = "";
        if (playload instanceof CounselorVO) {
            passwd += ((CounselorVO) playload).getCounselor().getPasswd();
            String mailContext = "<html><head></head><body><h1>您当前申请找回咨询师密码，您当前设定的密码如下，请迅速更换您的密码</h1>密码：<small>"
                                 + passwd + "</small></body></html>";
            sendEmail(mailContext, "欢迎您找回密码", LOCAL_MAIL, ((CounselorVO) playload).getCounselor()
                .getEmail());
        }
        if (playload instanceof Consultant) {
            passwd += ((CounselorVO) playload).getCounselor().getPasswd();
            String mailContext = "<html><head></head><body><h1>您当前申请找回咨询者密码，您当前设定的密码如下，请迅速更换您的密码</h1>密码：<small>"
                                 + passwd + "</small></body></html>";
            sendEmail(mailContext, "欢迎您找回密码", LOCAL_MAIL, ((Consultant) playload).getEmail());
        }
    }
}
