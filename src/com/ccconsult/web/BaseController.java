/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.ccconsult.base.CcConstrant;
import com.ccconsult.base.ServiceTemplate;
import com.ccconsult.pojo.Consultant;
import com.ccconsult.view.CounselorVO;

/**
 * 
 * controller的基类
 * @author jingyu.dan
 * @version $Id: BaseController.java, v 0.1 2014-5-29 下午11:17:50 jingyu.dan Exp $
 */
public class BaseController {

    @Autowired
    protected ServiceTemplate serviceTemplate;

    public Consultant getConsultantInSession(HttpSession session) {
        Object consultant = session.getAttribute(CcConstrant.SESSION_CONSULTANT_OBJECT);
        if (consultant != null) {
            return (Consultant) consultant;
        } else {
            return null;
        }
    }

    public CounselorVO getCounselorInSession(HttpSession session) {
        Object counselorVO = session.getAttribute(CcConstrant.SESSION_COUNSELOR_OBJECT);
        if (counselorVO != null) {
            return (CounselorVO) counselorVO;
        } else {
            return null;
        }
    }
}
