/**
 * 
 */
package com.ccconsult.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.ccconsult.dao.ServiceDAO;
import com.ccconsult.pojo.Consultant;
import com.ccconsult.util.StringUtil;
import com.ccconsult.view.CounselorVO;

/**
 * @author jingyu.dan
 * 
 */
@Controller
public class LoginController extends BaseController {

    @Autowired
    private CounselorDAO  counselorDAO;
    @Autowired
    private ConsultantDAO consultantDAO;
    @Autowired
    private ServiceDAO    serviceDAO;

    @RequestMapping(value = "/login.htm", params = "action=COUNSELOR", method = RequestMethod.POST)
    public ModelAndView loginInterviewer(HttpServletRequest request, String account,
                                         String password, HttpServletResponse response,
                                         ModelMap modelMap) {
        CounselorVO counselorVO = counselorDAO.findByEmail(account);
        modelMap.put("account", account);
        if (counselorVO == null) {
            modelMap.put("result", CcResult.retFailure("用户不存在"));
            return new ModelAndView("content/login");
        }
        if (!StringUtils.equals(counselorVO.getCounselor().getPasswd(), password)) {
            modelMap.put("result", CcResult.retFailure("密码错误"));
            return new ModelAndView("content/login");
        }
        request.getSession().setAttribute(CcConstrant.SESSION_COUNSELOR_OBJECT, counselorVO);
        request.getSession().setAttribute(CcConstrant.ALL_SERVICES, serviceDAO.findAll());
        request.getSession().removeAttribute(CcConstrant.SESSION_CONSULTANT_OBJECT);
        String redirectUrl = (String) request.getSession().getAttribute("redirectUrl");
        if (!StringUtil.isBlank(redirectUrl) && !redirectUrl.contains("/consultant/")) {
            request.getSession().removeAttribute("redirectUrl");
            return new ModelAndView("redirect:" + redirectUrl);
        }
        return new ModelAndView("redirect:/counselor/consult/searchConsult.htm?step=1");

    }

    @RequestMapping(value = "/login.htm", params = "action=CONSULTANT", method = RequestMethod.POST)
    public ModelAndView loginJobseeker(HttpServletRequest request, String account, String password,
                                       ModelMap modelMap) {
        Consultant consultant = consultantDAO.findByEmail(account);
        modelMap.put("account", account);
        if (consultant == null) {
            modelMap.put("result", CcResult.retFailure("用户不存在"));
            return new ModelAndView("content/login");
        }
        if (!StringUtils.equals(consultant.getPasswd(), password)) {
            modelMap.put("result", CcResult.retFailure("密码错误"));
            return new ModelAndView("content/login");
        }
        request.getSession().setAttribute(CcConstrant.SESSION_CONSULTANT_OBJECT, consultant);
        request.getSession().setAttribute(CcConstrant.ALL_SERVICES, serviceDAO.findAll());
        request.getSession().removeAttribute(CcConstrant.SESSION_COUNSELOR_OBJECT);
        String redirectUrl = (String) request.getSession().getAttribute("redirectUrl");
        if (!StringUtil.isBlank(redirectUrl) && !redirectUrl.contains("/counselor/")) {
            request.getSession().removeAttribute("redirectUrl");
            return new ModelAndView("redirect:" + redirectUrl);
        }
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

}
