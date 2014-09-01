/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.web.view;

import com.ccconsult.pojo.ConsultOnline;

/**
 * 面试咨询
 * 
 * @author jingyudan
 * @version $Id: CounsultInterviewVO.java, v 0.1 2014-7-7 下午9:42:26 jingyudan Exp $
 */
public class CounsultOnlineVO extends ConsultBase {

    private static final long serialVersionUID = 1L;

    private ConsultOnline     consultOnline;

    public ConsultOnline getConsultOnline() {
        return consultOnline;
    }

    public void setConsultOnline(ConsultOnline consultOnline) {
        this.consultOnline = consultOnline;
    }

}
