/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.core.consult;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.ccconsult.base.AssertUtil;
import com.ccconsult.base.CcConstrant;
import com.ccconsult.base.enums.ConsultStepEnum;
import com.ccconsult.base.enums.DataStateEnum;
import com.ccconsult.base.enums.PayStateEnum;
import com.ccconsult.base.util.CodeGenUtil;
import com.ccconsult.base.util.DateUtil;
import com.ccconsult.base.util.ScheduleTimeUtil;
import com.ccconsult.dao.ConsultDAO;
import com.ccconsult.dao.ConsultInterviewDAO;
import com.ccconsult.dao.CounselorDAO;
import com.ccconsult.dao.ServiceConfigDAO;
import com.ccconsult.dao.ServiceDAO;
import com.ccconsult.pojo.Consult;
import com.ccconsult.pojo.ConsultInterview;
import com.ccconsult.pojo.Service;
import com.ccconsult.pojo.ServiceConfig;
import com.ccconsult.web.view.CounselorVO;

/**
 * 
 * @author jingyudan
 * @version $Id: ConsultInterviewComponentImpl.java, v 0.1 2014-8-31 下午9:11:14 jingyudan Exp $
 */
public class ConsultInterviewComponentImpl implements ConsultInterviewComponent {
    @Autowired
    private CounselorDAO        counselorDAO;
    @Autowired
    private ConsultDAO          consultDAO;
    @Autowired
    private ConsultInterviewDAO consultInterviewDAO;
    @Autowired
    private ServiceDAO          serviceDAO;
    @Autowired
    private ServiceConfigDAO    serviceConfigDAO;

    /** 
     * @see com.ccconsult.core.consult.ConsultInterviewComponent#createConsult(com.ccconsult.pojo.Consult)
     */
    public void createConsult(Consult consult, String fileName, String scheduleTime,
                              String scheduleDay) {
        CounselorVO counselorVO = counselorDAO.findById(consult.getCounselorId());
        AssertUtil.notNull(counselorVO, "咨询对象不存在，请检查");
        AssertUtil.notBlank(consult.getGoal(), "请填写您的咨询目的，请检查");
        AssertUtil.state(consult.getGoal().length() <= CcConstrant.COMMON_4096_LENGTH,
            "描述问题太长，请简洁描述");
        ServiceConfig serviceConfig = serviceConfigDAO.findById(consult.getServiceConfigId());
        AssertUtil.state(
            serviceConfig != null
                    && serviceConfig.getState().equals(DataStateEnum.NORMAL.getValue()),
            "服务配置对象当前过期，请查询发起咨询");
        //创建一个咨询记录
        consult.setGmtCreate(new Date());
        consult.setGmtModified(new Date());
        consult.setPayTag(PayStateEnum.WAIT_FOR_PAY.getValue());
        consult.setStep(ConsultStepEnum.CREATE.getValue());
        Service service = serviceDAO.findById(consult.getServiceId());
        AssertUtil.notNull(service, "服务不存在，请检查");

        //查询当天预约的信息
        List<String> myScheduleTime = ScheduleTimeUtil.getSchuleTime(serviceConfig.getWorkOnTime());
        AssertUtil.state(myScheduleTime.contains(scheduleTime), "对不起，该时间段不能预约");
        AssertUtil.notBlank(scheduleDay, "预约日期必须正常设置");
        int day = NumberUtils.toInt(scheduleDay);
        String times = ScheduleTimeUtil.getScheduleMap().get(scheduleTime);
        Date scheduleBegin = DateUtil.parseDate(
            DateUtil.format(DateUtil.addDays(new Date(), day), "yyyy-MM-dd ")
                    + ScheduleTimeUtil.getBegin(times), "yyyy-MM-dd HH:mm");
        Date scheduleEnd = DateUtil.parseDate(
            DateUtil.format(DateUtil.addDays(new Date(), day), "yyyy-MM-dd ")
                    + ScheduleTimeUtil.getEnd(times), "yyyy-MM-dd HH:mm");
        AssertUtil.state(scheduleBegin.after(new Date()), "对不起，当前预约时间已经过期，请重新选择");
        consult.setGmtEffectBegin(new Date());
        consult.setPrice(NumberUtils.toDouble(service.getPriceRegion()));
        consult.setGmtEffectEnd(DateUtil.addHours(new Date(), service.getEffectTime()));
        consult.setIndetityCode(CodeGenUtil.getFixLenthString(6));
        consultDAO.save(consult);

        //创建面试咨询记录
        ConsultInterview consultInterview = new ConsultInterview();
        consultInterview.setGmtScheduleBegin(scheduleBegin);
        consultInterview.setGmtScheduleEnd(scheduleEnd);
        consultInterview.setSchedueTime(scheduleDay + CcConstrant.ALT_SEPARATOR + scheduleTime);
        consultInterview.setConsultId(consult.getId());
        consultInterview.setResumeFile(fileName);
        consultInterviewDAO.save(consultInterview);
    }

}
