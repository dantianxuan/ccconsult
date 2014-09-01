/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.ccconsult.base.CcConstrant;
import com.ccconsult.base.ServiceTemplate;
import com.ccconsult.base.enums.UserRoleEnum;
import com.ccconsult.core.cache.CachedComponent;
import com.ccconsult.dao.ConsultantDAO;
import com.ccconsult.dao.CounselorDAO;
import com.ccconsult.pojo.Consultant;
import com.ccconsult.web.view.CounselorVO;

/**
 * 
 * controller的基类
 * @author jingyu.dan
 * @version $Id: BaseController.java, v 0.1 2014-5-29 下午11:17:50 jingyu.dan Exp $
 */
public class BaseController {

    /**日志 */
    @Autowired
    protected ServiceTemplate serviceTemplate;
    @Autowired
    protected CounselorDAO    counselorDAO;
    @Autowired
    protected ConsultantDAO   consultantDAO;
    @Autowired
    protected CachedComponent cachedComponent;
    public final static int   COUNSELOR  = 0;
    public final static int   CONSULTANT = 1;

    public Consultant getConsultantInSession(HttpSession session) {
        Object consultant = session.getAttribute(CcConstrant.SESSION_CONSULTANT_OBJECT);
        if (consultant != null) {
            return (Consultant) consultant;
        } else {
            return null;
        }
    }

    public void reflushCouselorSession(int counselorId, HttpSession session) {
        CounselorVO counselorVO = counselorDAO.findById(counselorId);
        session.setAttribute(CcConstrant.SESSION_COUNSELOR_OBJECT, counselorVO);
    }

    public void reflushConsultantSession(int consultantId, HttpSession session) {
        Consultant consultant = consultantDAO.findById(consultantId);
        session.setAttribute(CcConstrant.SESSION_COUNSELOR_OBJECT, consultant);
    }

    /**
     *  session中用户校验
     * 
     * @param id
     * @param role
     * @param session
     * @return
     */
    public boolean validInSession(int id, UserRoleEnum role, HttpSession session) {
        if (role == UserRoleEnum.CONSULTANT) {
            Consultant consultant = getConsultantInSession(session);
            if (consultant == null || !consultant.getId().equals(id)) {
                return false;
            }
            return true;
        }
        if (role == UserRoleEnum.COUNSELOR) {
            CounselorVO counselorVO = getCounselorInSession(session);
            if (counselorVO == null || counselorVO.getCounselor().getId().equals(id)) {
                return false;
            }
            return true;
        }
        return false;
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
