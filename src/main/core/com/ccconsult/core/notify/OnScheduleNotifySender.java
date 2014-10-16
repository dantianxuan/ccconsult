/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.core.notify;

import org.springframework.beans.factory.annotation.Autowired;

import com.ccconsult.dao.CounselorDAO;
import com.ccconsult.pojo.Consult;
import com.ccconsult.web.view.CounselorVO;

/**
 * 预约请记录，用户付费并生成咨询记录后发送
 * 
 * @author jingyudan
 * @version $Id: OnScheduleNotifySender.java, v 0.1 2014-7-27 上午8:58:28 jingyudan Exp $
 */
public class OnScheduleNotifySender extends AbstractNotifySender {

    @Autowired
    private CounselorDAO counselorDAO;

    /** 
     * @see com.ccconsult.core.notify.NotifySender#notify(java.lang.String, java.lang.Object)
     */
    @Override
    public void notify(String senderName, Object playload) {
        Consult consult = (Consult) playload;
        CounselorVO counselorVO = counselorDAO.findById(consult.getCounselorId());
        sendSMS(counselorVO.getCounselor().getMobile(), TEMPLATE_CONSULT_NOTIFY, new String[] {
                counselorVO.getCounselor().getName(), String.valueOf(consult.getServiceId()) });
    }
}
