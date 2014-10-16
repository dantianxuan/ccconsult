/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.service;

import com.ccconsult.base.CcResult;
import com.ccconsult.pojo.Consultant;

/**
 * 咨询师服务
 * 
 * @author jingyu.dan
 * @version $Id: CounselorService.java, v 0.1 2014-5-28 下午8:36:13 jingyu.dan Exp $
 */
public interface ConsultantService {

    /**
     * 注册
     * 
     * @return
     */
    public CcResult registConsultant(Consultant consultant, String repasswd, String token);

}
