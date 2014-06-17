/**
 * 
 */
package com.ccconsult.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import com.ccconsult.pojo.Resume;
import com.ccconsult.service.ResumeService;


/**
 * @author jinsaichen
 * 
 */
@Controller
public class ResumeController extends BaseController {

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private ResumeDAO     resumeDAO;

    @RequestMapping(value = "jobseeker/editResume.htm", method = RequestMethod.GET)
    public ModelAndView toInterview(HttpServletRequest request, ModelMap modelMap) {
        String resumeId = request.getParameter("resumeId");

        if (!StringUtils.isBlank(resumeId)) {
            modelMap.put("result", new CcResult(resumeDAO.findById(NumberUtils.toInt(resumeId))));
        }
        ModelAndView view = new ModelAndView("jobseeker/editResume");

        return view;
    }

    @RequestMapping(value = "jobseeker/editResume.htm", params = "action=save", method = RequestMethod.POST)
    public ModelAndView saveArtcile(final HttpServletRequest request, final Resume resume,
                                    ModelMap modelMap) {

        CcResult result = serviceTemplate.execute(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                //??·å??æ¯?ä¸???¶é??
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String startWorkDateYear = request.getParameter("startWorkDateYear");
                String startWorkDateMonth = request.getParameter("startWorkDateMonth");
                String graduation = startWorkDateYear + "-" + startWorkDateMonth;

                //è®¾ç½®??ºç????¶é??
                String startBirthDateYear = request.getParameter("startWorkDateYear");
                String startBirthDateMonth = request.getParameter("startWorkDateMonth");
                String birth = startBirthDateYear + "-" + startBirthDateMonth;

                if (resume.getId() != null && resume.getId() > 0) {
                    resumeService.updateResume(resume);
                } else {
                    resumeService.saveResume(resume);
                }
                return new CcResult(resume);
            }
        });
        result.setSuccess(true);
        modelMap.put("result", result);
        return new ModelAndView("redirect:/jobseeker/jobseekerSelf.htm");
    }
}
