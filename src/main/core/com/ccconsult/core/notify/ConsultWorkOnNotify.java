/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.core.notify;

import org.springframework.beans.factory.annotation.Autowired;

import com.ccconsult.dao.CounselorDAO;
import com.ccconsult.pojo.Consult;
import com.ccconsult.pojo.Counselor;

/**
 * 
 * @author jingyudan
 * @version $Id: ConsultWorkOnNotify.java, v 0.1 2014-7-27 上午10:07:51 jingyudan Exp $
 */
public class ConsultWorkOnNotify extends AbstractNotifySender {

    @Autowired
    private CounselorDAO counselorDAO;

    /** 
     * @see com.ccconsult.core.notify.NotifySender#notify(java.lang.String, java.lang.Object)
     */
    @Override
    public void notify(String senderName, Object playload) {
        Consult consult = (Consult) playload;
        Counselor counselor = counselorDAO.getById(consult.getCounselorId());
        String mailContext = "<html><head></head><body><h1>您有一次预约记录已被支付,预约ID(" + consult.getId()
                             + ")请登录<a href='http://www.zhenzi.me'>真咨网</a>查看详情</h1>"
                             + "</body></html>";
        sendEmail(mailContext, "您有一次预约记录已被支付", LOCAL_MAIL, counselor.getEmail());
    }
}
