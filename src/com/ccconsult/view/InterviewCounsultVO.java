/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.view;

import com.ccconsult.pojo.InterviewConsult;

/**
 * 面试咨询
 * 
 * @author jingyudan
 * @version $Id: CounsultInterviewVO.java, v 0.1 2014-7-7 下午9:42:26 jingyudan Exp $
 */
public class InterviewCounsultVO extends ConsultBase {

    /**  */
    private static final long serialVersionUID = 1L;

    private InterviewConsult  interviewConsult;

    public InterviewConsult getInterviewConsult() {
        return interviewConsult;
    }

    public void setInterviewConsult(InterviewConsult interviewConsult) {
        this.interviewConsult = interviewConsult;
    }

}
