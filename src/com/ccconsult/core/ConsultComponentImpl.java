/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.core;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.ccconsult.base.PageList;
import com.ccconsult.dao.AppriseDAO;
import com.ccconsult.dao.ConsultDAO;
import com.ccconsult.dao.ConsultantDAO;
import com.ccconsult.dao.CounselorDAO;
import com.ccconsult.dao.InterviewConsultDAO;
import com.ccconsult.dao.ResumeConsultDAO;
import com.ccconsult.dao.ServiceConfigDAO;
import com.ccconsult.enums.UserRoleEnum;
import com.ccconsult.pojo.Apprise;
import com.ccconsult.pojo.Consult;
import com.ccconsult.view.ConsultBase;
import com.ccconsult.view.InterviewCounsultVO;
import com.ccconsult.view.ResumeConsultVO;

/**
 * 
 * @author jingyudan
 * @version $Id: CounsultComponentImpl.java, v 0.1 2014-7-7 下午10:15:33 jingyudan Exp $
 */
public class ConsultComponentImpl implements ConsultComponent {

    /** 
     * @see com.ccconsult.core.ConsultComponent#queryPaged()
     */
    @Override
    public PageList<ConsultBase> queryUnderStepPaged(int payTag, int step, int serviceId,
                                                     int counselorId, int consultantId,
                                                     int pageSize, int pageNo) {
        PageList pageList = consultDAO.queryUnderStepPaged(payTag, step, serviceId, counselorId,
            consultantId, pageSize, pageNo);
        if (CollectionUtils.isEmpty(pageList.getData())) {
            return pageList;
        }
        List<ConsultBase> consultBases = new ArrayList<ConsultBase>();
        for (Object consult : pageList.getData()) {
            consultBases.add(consConsultBase((Consult) consult));
        }
        pageList.setData(consultBases);
        return pageList;
    }

    @Override
    public PageList<ConsultBase> queryPaged(int payTag, int step, int serviceId, int counselorId,
                                            int consultantId, int pageSize, int pageNo) {
        PageList pageList = consultDAO.queryPaged(payTag, step, serviceId, counselorId,
            consultantId, pageSize, pageNo);
        if (CollectionUtils.isEmpty(pageList.getData())) {
            return pageList;
        }
        List<ConsultBase> consultBases = new ArrayList<ConsultBase>();
        for (Object consult : pageList.getData()) {
            consultBases.add(consConsultBase((Consult) consult));
        }
        pageList.setData(consultBases);
        return pageList;

    }

    @Override
    public PageList<ConsultBase> queryInnerConsultByGoal(String goal, int pageSize, int pageNo) {

        PageList pageList = consultDAO.queryInnerConsultByGoal(goal, pageSize, pageNo);
        if (CollectionUtils.isEmpty(pageList.getData())) {
            return pageList;
        }
        List<ConsultBase> consultBases = new ArrayList<ConsultBase>();
        for (Object consult : pageList.getData()) {
            consultBases.add(consConsultBase((Consult) consult));
        }
        pageList.setData(consultBases);
        return pageList;
    }

    /** 
     * @see com.ccconsult.core.ConsultComponent#queryById(int)
     */
    @Override
    public ConsultBase queryById(int id) {
        Consult consult = consultDAO.findById(id);
        if (consult == null) {
            return null;
        }
        return consConsultBase(consult);
    }

    /** 
     * @see com.ccconsult.core.ConsultComponent#payForConsult()
     */
    @Override
    public int payForConsult() {
        return 0;
    }

    private ConsultBase consConsultBase(Consult consult) {

        ConsultBase base = new ConsultBase();
        if (consult.getServiceId() == 2) {//简历修改服务
            base = new ResumeConsultVO();
            ((ResumeConsultVO) base).setResumeConsult(resumeConsultDAO.findByConsultId(consult
                .getId()));
        }
        if (consult.getServiceId() == 4) {
            base = new InterviewCounsultVO();
            ((InterviewCounsultVO) base).setInterviewConsult(interviewConsultDAO
                .findByConsultId(consult.getId()));
        }
        List<Apprise> apprises = appriseDAO.findByRelId(consult.getId());
        if (!CollectionUtils.isEmpty(apprises)) {
            for (Apprise apprise : apprises) {
                if (apprise.getCreatorRole().equals(UserRoleEnum.COUNSELOR.getValue())) {
                    base.setCounselorApprise(apprise);
                } else {
                    base.setConsultantApprise(apprise);
                }
            }
        }
        base.setConsult(consult);
        base.setConsultant(consultantDAO.findById(consult.getConsultantId()));
        base.setCounselorVO(counselorDAO.findById(consult.getCounselorId()));
        base.setServiceConfigVO(serviceConfigDAO.findVoById(consult.getServiceConfigId()));
        return base;
    }

    @Autowired
    private CounselorDAO        counselorDAO;
    @Autowired
    private ResumeConsultDAO    resumeConsultDAO;
    @Autowired
    private ConsultantDAO       consultantDAO;
    @Autowired
    private InterviewConsultDAO interviewConsultDAO;
    @Autowired
    private ConsultDAO          consultDAO;
    @Autowired
    private AppriseDAO          appriseDAO;
    @Autowired
    private ServiceConfigDAO    serviceConfigDAO;

}
