/**
 * 
 */
package com.ccconsult.web;

import java.util.Date;
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
import com.ccconsult.dao.ResumeDAO;
import com.ccconsult.pojo.Consultant;
import com.ccconsult.pojo.Resume;

/**
 * 简历操作类
 * 
 * @author jinsaichen
 * @version $Id: ResumeController.java, v 0.1 2014-6-20 下午5:19:59 jinsaichen Exp $
 */
@Controller
public class ResumeController extends BaseController {

    @Autowired
    private ResumeDAO resumeDAO;

    @RequestMapping(value = "consultant/showResume.htm", method = RequestMethod.GET)
    public ModelAndView showResume(HttpServletRequest request, ModelMap modelMap) {

        Consultant consultant = getConsultantInSession(request.getSession());
        if (consultant != null) {
            modelMap.put("resume", resumeDAO.findByConsultantId(consultant.getId()));
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

        CcResult result = serviceTemplate.executeWithTx(CcResult.class, new BlankServiceCallBack() {

            @Override
            public void check() {
                AssertUtil.notBlank(resume.getRealName(), "简历的真实姓名不能为空！");
                AssertUtil.notBlank(resume.getSexy(), "性别不能为空！");
                AssertUtil.notBlank(resume.getSchool(), "毕业学校不能为空！");
                AssertUtil.notBlank(resume.getProfession(), "专业不能为空！");
            }

            @Override
            public CcResult executeService() {
                if (resume.getId() != null && resume.getId() > 0) {
                    Resume originResume = resumeDAO.findById(resume.getId());
                    originResume.setRealName(resume.getRealName());
                    originResume.setSchool(resume.getSchool());
                    originResume.setProfession(resume.getProfession());
                    originResume.setResume(resume.getResume());
                    originResume.setSexy(resume.getSexy());
                    originResume.setWorkExperience(resume.getWorkExperience());
                    originResume.setGmtModified(new Date());
                    resumeDAO.update(originResume);
                } else {
                    resume.setGmtCreate(new Date());
                    resume.setGmtModified(new Date());
                    resumeDAO.save(resume);
                }
                return new CcResult(resume);
            }
        });
        if (result.isSuccess()) {
            return new ModelAndView("redirect:/consultant/showResume.htm");
        }
        modelMap.put("result", result);
        modelMap.put("resume", resume);
        return new ModelAndView("consultant/editResume");

    }
}
