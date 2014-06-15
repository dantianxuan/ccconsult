/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ccconsult.dao.InnerMailDAO;
import com.ccconsult.pojo.Consultant;

/**
 * 
 * @author jingyudan
 * @version $Id: InnerMailController.java, v 0.1 2014-6-14 下午12:52:29 jingyudan Exp $
 */
@Controller
public class InnerMailController extends BaseController {
    @Autowired
    private InnerMailDAO innerMailDAO;

    @RequestMapping(value = "jobseeker/innerMail.htm", method = RequestMethod.GET)
    public ModelAndView toInterview(HttpServletRequest request, ModelMap modelMap) {
        ModelAndView view = new ModelAndView("jobseeker/createInterview");
        Consultant consultant = getConsultantInSession(request.getSession());
        return view;
    }
}
