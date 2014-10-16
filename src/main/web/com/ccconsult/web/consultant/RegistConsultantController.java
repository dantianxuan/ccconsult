/**
 * 
 */
package com.ccconsult.web.consultant;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ccconsult.base.CcConstrant;
import com.ccconsult.base.CcResult;
import com.ccconsult.pojo.Consultant;
import com.ccconsult.service.ConsultantService;
import com.ccconsult.web.BaseController;

/**
 * @author jingyu.dan
 * 
 */
@Controller
public class RegistConsultantController extends BaseController {
    @Autowired
    private ConsultantService consultantService;

    /**
     * @return
     */
    @RequestMapping(value = "regist/regConsultant.htm", method = RequestMethod.GET)
    public ModelAndView submitRegJobseeker(HttpServletRequest request, ModelMap modelMap) {
        return new ModelAndView("regist/regConsultant");
    }

    /**
     * 注册一个用户
     * 
     * @return
     * @throws IOException 
     * @throws Exception
     */
    @RequestMapping(value = "regist/regConsultant.htm", params = "action=regist")
    public ModelAndView submitRegConsultant(HttpServletRequest request,
                                            final Consultant consultant, final String repasswd,
                                            final String token, ModelMap modelMap) {

        CcResult result = consultantService.registConsultant(consultant, repasswd, token);
        if (result.isSuccess()) {
            request.getSession().setAttribute(CcConstrant.SESSION_CONSULTANT_OBJECT,
                result.getObject());
            return new ModelAndView("redirect:/consultant/consultantSelf.htm?consultantId="
                                    + consultant.getId());
        }
        modelMap.put("token", token);
        modelMap.put("result", result);
        modelMap.put("consultant", consultant);
        return new ModelAndView("regist/regConsultant");
    }

}
