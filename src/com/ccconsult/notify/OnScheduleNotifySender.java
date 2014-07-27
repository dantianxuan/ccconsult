/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.notify;

import org.springframework.beans.factory.annotation.Autowired;

import com.ccconsult.dao.ConsultantDAO;
import com.ccconsult.dao.InterviewConsultDAO;
import com.ccconsult.pojo.Consult;
import com.ccconsult.pojo.Consultant;
import com.ccconsult.pojo.InterviewConsult;
import com.ccconsult.util.DateUtil;

/**
 * 咨询师预约了您的信息通知器
 * 
 * @author jingyudan
 * @version $Id: OnScheduleNotifySender.java, v 0.1 2014-7-27 上午8:58:28 jingyudan Exp $
 */
public class OnScheduleNotifySender extends AbstractNotifySender {

    @Autowired
    private ConsultantDAO       consultantDAO;

    @Autowired
    private InterviewConsultDAO interviewConsultDAO;

    /** 
     * @see com.ccconsult.notify.NotifySender#notify(java.lang.String, java.lang.Object)
     */
    @Override
    public void notify(String senderName, Object playload) {
        Consult consult = (Consult) playload;
        Consultant consultant = consultantDAO.findById(consult.getConsultantId());
        if (consult.getServiceId().equals(4)) {
            InterviewConsult interviewConsult = interviewConsultDAO
                .findByConsultId(consult.getId());

            String begin = DateUtil.format(interviewConsult.getGmtScheduleBegin(),
                DateUtil.noSecondFormat);
            String end = DateUtil.format(interviewConsult.getGmtScheduleEnd(),
                DateUtil.noSecondFormat);
            String mailContext = "<html><head></head><body><h1>恭喜您，咨询师已经确认了您的预约" + consult.getId()
                                 + ",请登录<a href='http://www.zhenzi.me'>真咨网</a>查看详情</h1>"
                                 + "咨询师预约的时间是：" + begin + "到" + end
                                 + "，请登录平台完成咨询费用支付，支付完成后我们会在预约时间将咨询师的联系方式发送给您</body></html>";
            sendEmail(mailContext, "恭喜您，咨询师已经确认了您的预约", LOCAL_MAIL, consultant.getEmail());
            return;
        }//面试咨询
        String mailContext = "<html><head></head><body><h3>恭喜您，咨询师已经确认了您的预约" + consult.getId()
                             + ",请登录<a href='http://www.zhenzi.me'>真咨网</a>查看详情</h3>"
                             + "请登录平台完成咨询费用支付，支付后咨询师才能为您服务</body></html>";
        sendEmail(mailContext, "恭喜您，咨询师已经确认了您的预约", LOCAL_MAIL, consultant.getEmail());

    }
}
