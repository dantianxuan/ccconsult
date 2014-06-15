/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.view;

import com.ccconsult.base.ToString;
import com.ccconsult.pojo.Company;

/**
 * 
 * @author jingyudan
 * @version $Id: CompanyBriefVO.java, v 0.1 2014-6-6 上午7:13:34 jingyudan Exp $
 */
public class CompanyBriefVO extends ToString {
    /**  */
    private static final long serialVersionUID = 1L;

    private Company           company;

    private int               interviewCount;

    private int               counselorCount;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public int getInterviewCount() {
        return interviewCount;
    }

    public void setInterviewCount(int interviewCount) {
        this.interviewCount = interviewCount;
    }

    public int getCounselorCount() {
        return counselorCount;
    }

    public void setCounselorCount(int counselorCount) {
        this.counselorCount = counselorCount;
    }

}
