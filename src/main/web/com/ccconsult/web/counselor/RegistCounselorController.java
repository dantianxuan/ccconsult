/**
 * 
 */
package com.ccconsult.web.counselor;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ccconsult.base.AssertUtil;
import com.ccconsult.base.CcConstrant;
import com.ccconsult.base.CcResult;
import com.ccconsult.dao.CompanyDAO;
import com.ccconsult.dao.CounselorDAO;
import com.ccconsult.dao.RegMailDAO;
import com.ccconsult.pojo.Counselor;
import com.ccconsult.pojo.RegMail;
import com.ccconsult.service.CounselorService;
import com.ccconsult.service.RegistService;
import com.ccconsult.web.BaseController;
import com.ccconsult.web.view.CounselorVO;

/**
 * @author jingyu.dan
 * 
 */
@Controller
public class RegistCounselorController extends BaseController {
    @Autowired
    private RegistService    registService;
    @Autowired
    private CounselorService counselorService;
    @Autowired
    private RegMailDAO       regMailDAO;
    @Autowired
    private CounselorDAO     counselorDAO;
    @Autowired
    private CompanyDAO       companyDAO;

    /**
     * 注册面试官init页面
     * 
     * @return
     */
    @RequestMapping(value = "regist/regCounselorInit.htm", method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest httpservletrequest, ModelMap modelMap) {
        modelMap.put("companys", companyDAO.findAll());
        ModelAndView view = new ModelAndView("regist/regCounselorInit");
        return view;
    }

    /**
     * 使用公司邮箱进行注册
     * 
     * @return
     */
    @RequestMapping(value = "regist/regCounselorMail.htm", method = RequestMethod.GET)
    public ModelAndView registMail(HttpServletRequest httpservletrequest, String email,
                                   String subPrefix, ModelMap modelMap) {
        ModelAndView view = new ModelAndView("regist/regCounselorInit");
        RegMail regMail = new RegMail();
        regMail.setMail(email + subPrefix);
        regMail.setGmtCreate(new Date());
        regMail.setToken(UUID.randomUUID().toString());
        CcResult result = registService.regMail(regMail);
        modelMap.put("companys", companyDAO.findAll());
        modelMap.put("result", result);
        return view;
    }

    /**
     * 公司邮箱链接注册
     * 
     * @return
     */
    @RequestMapping(value = "regist/regCounselor.htm", method = RequestMethod.GET)
    public ModelAndView initRegInterviewer(String token, ModelMap modelMap) {
        RegMail regMail = regMailDAO.findByToken(token);
        if (regMail == null) {
            modelMap.put("message", "记录不存在，请重新注册");
            return new ModelAndView("error");
        }
        modelMap.put("regMail", regMail);
        return new ModelAndView("regist/regCounselor");
    }

    /**
     * 公司邮箱链接注册
     * @return
     */
    @RequestMapping(value = "regist/regCounselor.htm", params = "action=regist")
    public ModelAndView submitRegCounselor(final HttpServletRequest request,
                                           final Counselor counselor, final String repasswd,
                                           final String token, final Integer regMailId,
                                           final ModelMap modelMap) {
        AssertUtil.state(StringUtils.equals(counselor.getPasswd(), repasswd), "重复密码输入不一致");
        CcResult result = counselorService.registCounselor(counselor, regMailId, token);
        if (result.isSuccess()) {
            CounselorVO counselorVO = counselorDAO.findById(((Counselor) result.getObject())
                .getId());
            request.getSession().setAttribute(CcConstrant.SESSION_COUNSELOR_OBJECT, counselorVO);
            return new ModelAndView("redirect:/counselor/counselorSelf.htm");
        }
        modelMap.put("result", result);
        return new ModelAndView("regist/regCounselor");
    }
}
