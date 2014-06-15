/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ccconsult.dao.CompanyDAO;
import com.ccconsult.dao.CounselorDAO;
import com.ccconsult.pojo.Company;
import com.ccconsult.pojo.Counselor;

/**
 * 
 * @author jingyu.dan
 * @version $Id: CompanyController.java, v 0.1 2014-5-31 上午10:19:52 jingyu.dan Exp $
 */
@Controller
public class CompanyController extends BaseController {
    @Autowired
    private CompanyDAO   companyDAO;
    @Autowired
    private CounselorDAO counselorDAO;

    @RequestMapping(value = "/company.htm", method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request, ModelMap modelMap) {
        ModelAndView view = new ModelAndView("content/company");
        int companyId = NumberUtils.toInt(request.getParameter("companyId"));
        //查询公司信息
        Company company = companyDAO.findById(companyId);
        //查询公司注册员工
        List<Counselor> counselors = counselorDAO.findByCompanyId(companyId);
        modelMap.put("company", company);
        modelMap.put("counselors", counselors);
        return view;
    }
}
