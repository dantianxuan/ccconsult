/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.view;

import com.ccconsult.pojo.ConsultInterview;

/**
 * 面试咨询
 * 
 * @author jingyudan
 * @version $Id: CounsultInterviewVO.java, v 0.1 2014-7-7 下午9:42:26 jingyudan Exp $
 */
public class CounsultInterviewVO extends ConsultBase {

    private static final long serialVersionUID = 1L;

    private ConsultInterview  consultInterview;

    public ConsultInterview getConsultInterview() {
        return consultInterview;
    }

    public void setConsultInterview(ConsultInterview consultInterview) {
        this.consultInterview = consultInterview;
    }

}
