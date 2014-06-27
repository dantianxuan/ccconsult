/**
 * 
 */
package com.ccconsult.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
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
import com.ccconsult.dao.InnerMailDAO;
import com.ccconsult.dao.InterviewDAO;
import com.ccconsult.enums.DataStateEnum;
import com.ccconsult.enums.UserRoleEnum;
import com.ccconsult.pojo.Consultant;
import com.ccconsult.pojo.Interview;
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
    @Autowired
    private InnerMailDAO  innerMailDAO;

    @RequestMapping(value = "/consultant/consultantSelf.htm", method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request, ModelMap modelMap) {
        ModelAndView view = new ModelAndView("consultant/consultantSelf");
        final Consultant consultant = getConsultantInSession(request.getSession());
        CcResult result = serviceTemplate.execute(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                List<InterviewVO> interviewVOs = interviewDAO.findInterviewsConsultant(
                    consultant.getId(), DataStateEnum.NORMAL);
                return new CcResult(interviewVOs);
            }
        });
        modelMap.put("result", result);
        return view;
    }

    @RequestMapping(value = "consultant/personalInfo.htm", method = RequestMethod.GET)
    public ModelAndView showInformation(HttpServletRequest request, ModelMap modelMap) {

        Consultant consultant = getConsultantInSession(request.getSession());
        if (consultant != null) {
            modelMap.put("consultant", consultantDAO.findById(consultant.getId()));
        }
        ModelAndView view = new ModelAndView("consultant/personalInfo");
        return view;
    }

    @RequestMapping(value = "consultant/editPersonalInfo.htm", method = RequestMethod.GET)
    public ModelAndView toInterview(HttpServletRequest request, ModelMap modelMap) {

        Consultant consultant = getConsultantInSession(request.getSession());
        if (consultant != null) {
            modelMap.put("consultant", consultantDAO.findById(consultant.getId()));
        }
        ModelAndView view = new ModelAndView("consultant/editPersonalInfo");
        return view;
    }

    @RequestMapping(value = "consultant/innerMails.htm", method = RequestMethod.GET)
    public ModelAndView toMessages(HttpServletRequest request, ModelMap modelMap) {
        Consultant consultant = getConsultantInSession(request.getSession());
        if (consultant != null) {
            modelMap.put(
                "innerMails",
                innerMailDAO.findByByReceiver(consultant.getId(),
                    UserRoleEnum.CONSULTANT.getValue()));
        }
        ModelAndView view = new ModelAndView("consultant/innerMails");
        return view;
    }

    @RequestMapping(value = "consultant/editPersonalInfo.htm", params = "action=save", method = RequestMethod.POST)
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
                    Consultant originConsultant = consultantDAO.findById(consultant.getId());
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
            return new ModelAndView("redirect:/consultant/personalInfo.htm");
        }
        modelMap.put("result", result);
        modelMap.put("consultant", consultant);
        return new ModelAndView("consultant/editPersonalInfo");
    }
}
