/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.view;

import java.util.List;

import com.ccconsult.base.ToString;
import com.ccconsult.pojo.Apprise;
import com.ccconsult.pojo.Consult;
import com.ccconsult.pojo.Consultant;

/**
 * 咨询对象基类
 * 
 * @author jingyudan
 * @version $Id: CounsultBase.java, v 0.1 2014-6-11 下午8:21:25 jingyudan Exp $
 */
public class ConsultBase extends ToString {
    private static final long serialVersionUID = 1L;

    /**咨询对象*/
    protected Consult         consult;

    /**咨询者*/
    protected Consultant      consultant;

    /**咨询服务*/
    protected ServiceConfigVO serviceConfigVO;

    /**咨询师对象*/
    protected CounselorVO     counselorVO;

    /**评价对象*/
    protected List<Apprise>   apprises;

    public Consult getConsult() {
        return consult;
    }

    public void setConsult(Consult consult) {
        this.consult = consult;
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

    public ServiceConfigVO getServiceConfigVO() {
        return serviceConfigVO;
    }

    public void setServiceConfigVO(ServiceConfigVO serviceConfigVO) {
        this.serviceConfigVO = serviceConfigVO;
    }

}