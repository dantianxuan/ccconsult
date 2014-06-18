/**
 * 
 */
package com.ccconsult.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ccconsult.base.CcConstrant;
import com.ccconsult.base.CcResult;
import com.ccconsult.dao.ConsultantDAO;
import com.ccconsult.dao.CounselorDAO;
import com.ccconsult.pojo.Consultant;
import com.ccconsult.view.CounselorVO;

/**
 * @author jingyu.dan
 * 
 */
@Controller
public class LoginController {

    @Autowired
    private CounselorDAO  counselorDAO;
    @Autowired
    private ConsultantDAO consultantDAO;

    @RequestMapping(value = "/login.htm", params = "action=COUNSELOR", method = RequestMethod.POST)
    public ModelAndView loginInterviewer(HttpServletRequest request, String account,
                                         String password, ModelMap modelMap) {
        CounselorVO counselorVO = counselorDAO.findByEmail(account);
        modelMap.put("account", account);
        if (counselorVO == null) {
            modelMap.put("result", new CcResult("用户不存在"));
            return new ModelAndView("content/login");
        }
        if (!StringUtils.equals(counselorVO.getCounselor().getPasswd(), password)) {
            modelMap.put("result", new CcResult("密码错误"));
            return new ModelAndView("content/login");
        }
        request.getSession().setAttribute(CcConstrant.SESSION_COUNSELOR_OBJECT, counselorVO);
        return new ModelAndView("redirect:/counselor/counselorSelf.htm");
    }

    @RequestMapping(value = "/login.htm", params = "action=CONSULTANT", method = RequestMethod.POST)
    public ModelAndView loginJobseeker(HttpServletRequest request, String account, String password,
                                       ModelMap modelMap) {
        Consultant consultant = consultantDAO.findByEmail(account);
        modelMap.put("account", account);
        if (consultant == null) {
            modelMap.put("result", new CcResult("用户不存在"));
            return new ModelAndView("content/login");
        }
        if (!StringUtils.equals(consultant.getPasswd(), password)) {
            modelMap.put("result", new CcResult("密码错误"));
            return new ModelAndView("content/login");
        }
        request.getSession().setAttribute(CcConstrant.SESSION_CONSULTANT_OBJECT, consultant);
        return new ModelAndView("redirect:/consultant/consultantSelf.htm");
    }

    @RequestMapping(value = "/login.htm", method = RequestMethod.GET)
    public ModelAndView initPage(HttpServletRequest request, ModelMap modelMap) {
        ModelAndView view = new ModelAndView("content/login");
        return view;
    }

    @RequestMapping(value = "/logout.htm", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request) {
        request.getSession().removeAttribute(CcConstrant.SESSION_CONSULTANT_OBJECT);
        request.getSession().removeAttribute(CcConstrant.SESSION_COUNSELOR_OBJECT);
        ModelAndView view = new ModelAndView("redirect:/index.htm");
        return view;
    }

    @RequestMapping(value = "/findPasswd.htm", method = RequestMethod.GET)
    public ModelAndView findPasswd(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("content/findPasswd");
        return view;
    }

}
