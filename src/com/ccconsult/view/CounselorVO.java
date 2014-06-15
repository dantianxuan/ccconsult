/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.view;

import com.ccconsult.base.ToString;
import com.ccconsult.pojo.Company;
import com.ccconsult.pojo.Counselor;

/**
 * 面试官基本信息对象
 * 包含面试官基本信息，公司信息等
 * 
 * @author jingyu.dan
 * @version $Id: InterviewerVO.java, v 0.1 2014-6-1 下午5:49:47 jingyu.dan Exp $
 */
public class CounselorVO extends ToString {

    /**  */
    private static final long serialVersionUID = 1L;

    /**面试官*/
    private Counselor         counselor;

    /**公司信息*/
    private Company           company;

    public CounselorVO(Counselor counselor, Company company) {
        this.counselor = counselor;
        this.company = company;
    }

    public Counselor getCounselor() {
        return counselor;
    }

    public void setCounselor(Counselor counselor) {
        this.counselor = counselor;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

}
