/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.view;

import com.ccconsult.pojo.ResumeConsult;

/**
 * 面试咨询
 * 
 * @author jingyudan
 * @version $Id: CounsultInterviewVO.java, v 0.1 2014-7-7 下午9:42:26 jingyudan Exp $
 */
public class ResumeConsultVO extends ConsultBase {

    private static final long serialVersionUID = 1L;

    private ResumeConsult     resumeConsult;

    public ResumeConsult getResumeConsult() {
        return resumeConsult;
    }

    public void setResumeConsult(ResumeConsult resumeConsult) {
        this.resumeConsult = resumeConsult;
    }

}
