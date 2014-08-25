/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.notify;

import org.springframework.beans.factory.annotation.Autowired;

import com.ccconsult.dao.ConsultantDAO;
import com.ccconsult.pojo.Consult;
import com.ccconsult.pojo.Consultant;

/**
 * 咨询师拒绝
 * 
 * @author jingyudan
 * @version $Id: ConsultRejectNotify.java, v 0.1 2014-7-27 上午10:09:16 jingyudan Exp $
 */
public class ConsultRejectNotify extends AbstractNotifySender {

    @Autowired
    private ConsultantDAO consultantDAO;

    /** 
     * @see com.ccconsult.notify.NotifySender#notify(java.lang.String, java.lang.Object)
     */
    @Override
    public void notify(String senderName, Object playload) {
        Consult consult = (Consult) playload;
        Consultant consultant = consultantDAO.findById(consult.getConsultantId());
        String mailContext = "<html><head></head><body><h1>很抱歉，您一次预约(ID:"
                             + consult.getId()
                             + ")被咨询师已经被拒绝,请登录<a href='http://www.zhenzi.me'>真咨网</a>查看详情</h1>拒绝原因：<small>"
                             + consult.getRejectReason() + "</small></body></html>";
        sendEmail(mailContext, "很抱歉，您的咨询被咨询师拒绝了", LOCAL_MAIL, consultant.getEmail());
    }

}
