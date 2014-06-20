/**
 * 
 */
package com.ccconsult.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ccconsult.base.AssertUtil;
import com.ccconsult.base.BlankServiceCallBack;
import com.ccconsult.base.CcResult;
import com.ccconsult.dao.ConsultantDAO;
import com.ccconsult.dao.InterviewDAO;
import com.ccconsult.enums.DataStateEnum;
import com.ccconsult.enums.InterviewStepEnum;
import com.ccconsult.pojo.Consultant;
import com.ccconsult.pojo.Resume;
import com.ccconsult.view.InterviewVO;

/**
 * @author jingyu.dan
 * 
 */
@Controller
public class ConsultantController extends BaseController {

    @Autowired
    private InterviewDAO  interviewDAO;

    @Autowired
    private ConsultantDAO consultantDAO;

    @RequestMapping(value = "/consultant/consultantSelf.htm", method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request, ModelMap modelMap) {
        ModelAndView view = new ModelAndView("consultant/consultantSelf");
        final Consultant consultant = getConsultantInSession(request.getSession());
        CcResult result = serviceTemplate.execute(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                List<InterviewVO> interviewVOs = interviewDAO.findInterviewsConsultant(
                    consultant.getId(), InterviewStepEnum.CREATE, DataStateEnum.NORMAL);
                return new CcResult(interviewVOs);
            }
        });
        modelMap.put("result", result);
        return view;
    }

    @RequestMapping(value = "consultant/consultantInformation.htm", method = RequestMethod.GET)
    public ModelAndView showInformation(HttpServletRequest request, ModelMap modelMap) {

        Consultant consultant = getConsultantInSession(request.getSession());
        if (consultant != null) {
            modelMap.put("consultant", consultantDAO.findById(consultant.getId()));
        }
        ModelAndView view = new ModelAndView("consultant/consultantInformation");
        return view;
    }

    @RequestMapping(value = "consultant/editInformation.htm", method = RequestMethod.GET)
    public ModelAndView toInterview(HttpServletRequest request, ModelMap modelMap) {

        Consultant consultant = getConsultantInSession(request.getSession());
        if (consultant != null) {
            modelMap.put("consultant", consultantDAO.findById(consultant.getId()));
        }
        ModelAndView view = new ModelAndView("consultant/editInformation");
        return view;
    }
    @RequestMapping(value = "consultant/editInformation.htm",  params = "action=save", method = RequestMethod.POST)
    public ModelAndView editInformation(HttpServletRequest request, ModelMap modelMap,
                                        final Consultant consultant) {
        CcResult result = serviceTemplate.execute(CcResult.class, new BlankServiceCallBack() {

            @Override
            public void check() {
                AssertUtil.notBlank(consultant.getName(), "姓名不能为空！");
                AssertUtil.notBlank(consultant.getMobile(), "联系方式不能为空！");
                AssertUtil.notBlank(consultant.getEmail(), "邮箱号码不能为空！");
            }

            @Override
            public CcResult executeService() {
                
                if (consultant.getId() != null && consultant.getId() > 0) {
                    Consultant originConsultant =   consultantDAO.findById(consultant.getId());
                    originConsultant.setName(consultant.getName());
                    originConsultant.setMobile(consultant.getMobile());
                    originConsultant.setEmail(consultant.getEmail());
                    originConsultant.setGmtModified(new Date());
                    consultantDAO.merge(originConsultant);
                }
                return new CcResult(consultant);
            }
        });
        if (result.isSuccess()) {
            return new ModelAndView("redirect:/consultant/consultantInformation.htm");
        }
        modelMap.put("result", result);
        modelMap.put("consultant", consultant);
        return new ModelAndView("consultant/editInformation");
    }
}
