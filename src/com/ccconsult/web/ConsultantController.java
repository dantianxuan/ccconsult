/**
 * 
 */
package com.ccconsult.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ccconsult.base.BlankServiceCallBack;
import com.ccconsult.base.CcResult;
import com.ccconsult.dao.InterviewDAO;
import com.ccconsult.enums.DataStateEnum;
import com.ccconsult.enums.InterviewStepEnum;
import com.ccconsult.pojo.Consultant;
import com.ccconsult.view.InterviewVO;

/**
 * @author jingyu.dan
 * 
 */
@Controller
public class ConsultantController extends BaseController {

    @Autowired
    private InterviewDAO interviewDAO;

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

}
