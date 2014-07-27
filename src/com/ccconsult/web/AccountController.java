/**
 * 
 */
package com.ccconsult.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ccconsult.dao.ConsultDAO;
import com.ccconsult.dao.ConsultantDAO;
import com.ccconsult.dao.CounselorDAO;

/**
 * @author jingyu.dan
 * 
 */
@Controller
public class AccountController extends BaseController {

    @Autowired
    private CounselorDAO  counselorDAO;
    @Autowired
    private ConsultDAO    consultDAO;
    @Autowired
    private ConsultantDAO consultantDAO;

    @RequestMapping(value = "consultant/personalAccount.htm", method = RequestMethod.GET)
    public ModelAndView toConsultantAppriaseInterview(HttpServletRequest request,
                                                      final String consultId,
                                                      final ModelMap modelMap) {
        ModelAndView view = new ModelAndView("consultant/personalAccount");
        return view;
    }

}
