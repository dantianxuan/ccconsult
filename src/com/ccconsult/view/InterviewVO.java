/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.view;

import java.util.List;

import com.ccconsult.base.ToString;
import com.ccconsult.pojo.Apprise;
import com.ccconsult.pojo.Consultant;
import com.ccconsult.pojo.Interview;

/**
 * 
 * @author jingyudan
 * @version $Id: InterviewVO.java, v 0.1 2014-6-11 下午8:21:25 jingyudan Exp $
 */
public class InterviewVO extends ToString {

    /**  */
    private static final long serialVersionUID = 1L;

    private Interview         interview;

    private Consultant        consultant;

    private CounselorVO       counselorVO;

    private List<Apprise>     apprises;

    public Interview getInterview() {
        return interview;
    }

    public void setInterview(Interview interview) {
        this.interview = interview;
    }

    public Consultant getConsultant() {
        return consultant;
    }

    public void setConsultant(Consultant consultant) {
        this.consultant = consultant;
    }

    public CounselorVO getCounselorVO() {
        return counselorVO;
    }

    public void setCounselorVO(CounselorVO counselorVO) {
        this.counselorVO = counselorVO;
    }

    public List<Apprise> getApprises() {
        return apprises;
    }

    public void setApprises(List<Apprise> apprises) {
        this.apprises = apprises;
    }

}
