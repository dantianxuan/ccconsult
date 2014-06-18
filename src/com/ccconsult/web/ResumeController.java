/**
 * 
 */
package com.ccconsult.web;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ccconsult.base.BlankServiceCallBack;
import com.ccconsult.base.CcResult;
import com.ccconsult.dao.ResumeDAO;
import com.ccconsult.pojo.Consultant;
import com.ccconsult.pojo.Resume;

/**
 * @author jinsaichen
 * 
 */
@Controller
public class ResumeController extends BaseController {

    @Autowired
    private ResumeDAO resumeDAO;

    @RequestMapping(value = "consultant/showResume.htm", method = RequestMethod.GET)
    public ModelAndView showResume(HttpServletRequest request, ModelMap modelMap) {
        String consultantId = request.getParameter("consultantId");

        if (!StringUtils.isBlank(consultantId)) {
            modelMap.put("resume", resumeDAO.findByConsultantId(NumberUtils.toInt(consultantId)));
        }
        ModelAndView view = new ModelAndView("consultant/showResume");

        return view;
    }

    @RequestMapping(value = "consultant/editResume.htm", method = RequestMethod.GET)
    public ModelAndView toInterview(HttpServletRequest request, ModelMap modelMap) {

        Consultant consultant = getConsultantInSession(request.getSession());
        Resume resume = resumeDAO.findByConsultantId(consultant.getId());
        modelMap.put("resume", resume);
        ModelAndView view = new ModelAndView("consultant/editResume");
        return view;
    }

    @RequestMapping(value = "consultant/editResume.htm", params = "action=save", method = RequestMethod.POST)
    public ModelAndView saveArtcile(final HttpServletRequest request, final Resume resume,
                                    ModelMap modelMap) {

        CcResult result = serviceTemplate.execute(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                if (resume.getId() != null && resume.getId() > 0) {
                    resume.setGmtModified(new Date());
                    resumeDAO.update(resume);
                } else {
                    resume.setGmtCreate(new Date());
                    resume.setGmtModified(new Date());
                    resumeDAO.save(resume);
                }
                return new CcResult(resume);
            }
        });
        result.setSuccess(true);
        modelMap.put("result", result);
        return new ModelAndView("redirect:/consultant/consultantSelf.htm");
    }
}
