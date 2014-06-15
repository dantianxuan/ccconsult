/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.service;

import com.ccconsult.base.CcResult;
import com.ccconsult.pojo.Consultant;
import com.ccconsult.pojo.Counselor;
import com.ccconsult.pojo.RegMail;

/**
 * 用户注册服务
 * 
 * @author jingyu.dan
 * @version $Id: RegistService.java, v 0.1 2014-5-28 下午7:59:20 jingyu.dan Exp $
 */
public interface RegistService {

    /**
     * 注册信息
     * @param regMail
     * @return
     */
    public CcResult regMail(RegMail regMail);

    /***
     * 通个token获取注册信息
     * @param token
     * @return
     */
    public CcResult getRegMainInfo(String token);

    /**
     * 注册信息
     * @param counselor
     * @param regMailId
     * @return
     */
    public CcResult regCounselor(Counselor counselor, int regMailId);

    /**
     * 注册一个用户
     * @param consultant
     * @return
     */
    public CcResult regConsultant(final Consultant consultant);

}
