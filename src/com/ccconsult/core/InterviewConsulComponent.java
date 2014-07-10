/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.core;

import com.ccconsult.pojo.InterviewConsult;

/**
 * 面试咨询服务
 * 
 * @author jingyudan
 * @version $Id: InterviewConsulComponent.java, v 0.1 2014-7-7 下午10:08:16 jingyudan Exp $
 */
public interface InterviewConsulComponent {

    /**
     * 创建咨询服务
     * 
     * @return
     */
    public InterviewConsult createInterviewCounsul();

    /**
     * 预约咨询信息
     * 
     * @return
     */
    public InterviewConsult onScheduleInterview();

}
