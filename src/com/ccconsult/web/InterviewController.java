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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ccconsult.base.AssertUtil;
import com.ccconsult.base.BlankServiceCallBack;
import com.ccconsult.base.CcResult;
import com.ccconsult.dao.AppriseDAO;
import com.ccconsult.dao.ConsultantDAO;
import com.ccconsult.dao.CounselorDAO;
import com.ccconsult.dao.InterviewDAO;
import com.ccconsult.dao.MessageDAO;
import com.ccconsult.enums.AppriseRelTypeEnum;
import com.ccconsult.enums.DataStateEnum;
import com.ccconsult.enums.ConsultStepEnum;
import com.ccconsult.enums.MessageRelTypeEnum;
import com.ccconsult.pojo.Apprise;
import com.ccconsult.pojo.Consultant;
import com.ccconsult.pojo.Interview;
import com.ccconsult.util.DateUtil;
import com.ccconsult.view.CounselorVO;
import com.ccconsult.view.MessageVO;

/**
 * @author jingyu.dan
 * 
 */
@Controller
public class InterviewController extends BaseController {

    @Autowired
    private CounselorDAO  counselorDAO;
    @Autowired
    private InterviewDAO  interviewDAO;
    @Autowired
    private MessageDAO    messageDAO;
    @Autowired
    private ConsultantDAO consultantDAO;
    @Autowired
    private AppriseDAO    appriseDAO;

    @RequestMapping(value = "consultant/createInterview.htm", method = RequestMethod.GET)
    public ModelAndView toInterview(HttpServletRequest request, String counselorId,
                                    ModelMap modelMap) {
        ModelAndView view = new ModelAndView("consultant/createInterview");
        int viewerId = NumberUtils.toInt(counselorId);
        CounselorVO counselorVO = counselorDAO.findById(viewerId);
        modelMap.put("counselorVO", counselorVO);
        return view;
    }

    @RequestMapping(value = "consultant/createInterview.json", method = RequestMethod.POST)
    public @ResponseBody
    ModelMap createInterview(final HttpServletRequest request, final Interview interview,
                             ModelMap modelMap) {
        modelMap.clear();
        CcResult result = serviceTemplate.execute(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                Consultant consultant = getConsultantInSession(request.getSession());
                AssertUtil.notNull(consultant, "不合法的用户");
                CounselorVO counselorVO = counselorDAO.findById(interview.getCounselorId());
                AssertUtil.notNull(counselorVO, "咨询对象不存在，请检查");
                interview.setGmtCreate(new Date());
                interview.setStep(ConsultStepEnum.CREATE.getValue());
                interview.setGmtModified(new Date());
                interview.setState(DataStateEnum.NORMAL.getValue());
                interviewDAO.save(interview);
                return new CcResult(interview);
            }
        });
        modelMap.put("result", result);
        return modelMap;
    }

    @RequestMapping(value = "consultant/interview.htm")
    public ModelAndView createInterview(final HttpServletRequest request, final String interviewId,
                                        ModelMap modelMap) {
        Interview interview = interviewDAO.findById(NumberUtils.toInt(interviewId));
        modelMap.put("interview", interview);
        CounselorVO counselorVO = counselorDAO.findById(interview.getCounselorId());
        modelMap.put("counselorVO", counselorVO);
        List<MessageVO> messageVOs = messageDAO.queryByRelInfo(interview.getId(),
            MessageRelTypeEnum.INTERVIEW.getValue());
        modelMap.put("messageVOs", messageVOs);
        List<Apprise> apprises = appriseDAO.findByRelId(interview.getId(),
            AppriseRelTypeEnum.INTERVIEW.getValue());
        modelMap.put("apprises", apprises);
        return new ModelAndView("consultant/interview");
    }

    @RequestMapping(value = "consultant/deleteInterview.json")
    public @ResponseBody
    ModelMap deleteInterview(final HttpServletRequest request, final String interviewId,
                             ModelMap modelMap) {

        CcResult result = serviceTemplate.execute(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                Interview interview = interviewDAO.findById(NumberUtils.toInt(interviewId));
                interview.setState(DataStateEnum.DELETE.getValue());
                interviewDAO.update(interview);
                return new CcResult(interview);
            }
        });
        modelMap.put("result", result);
        return modelMap;
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~counselor ~~~~~~~~~~~~~~~~~~~~~~~

    @RequestMapping(value = "counselor/interview.htm")
    public ModelAndView interviewerInterview(final HttpServletRequest request,
                                             final String interviewId, ModelMap modelMap) {
        Interview interview = interviewDAO.findById(NumberUtils.toInt(interviewId));
        modelMap.put("interview", interview);
        Consultant consultant = consultantDAO.findById(interview.getConsultantId());
        modelMap.put("consultant", consultant);
        List<MessageVO> messageVOs = messageDAO.queryByRelInfo(interview.getId(),
            MessageRelTypeEnum.INTERVIEW.getValue());
        modelMap.put("messageVOs", messageVOs);
        List<Apprise> apprises = appriseDAO.findByRelId(interview.getId(),
            AppriseRelTypeEnum.INTERVIEW.getValue());
        modelMap.put("apprises", apprises);
        genMap(modelMap);
        return new ModelAndView("counselor/interview");
    }

    private ModelMap genMap(ModelMap modelMap) {
        Date now = new Date();
        modelMap.put("today", DateUtil.format(now, DateUtil.noSecondFormat));
        modelMap.put("after3day",
            DateUtil.format(DateUtil.addDays(now, 3), DateUtil.noSecondFormat));
        modelMap.put("after2day",
            DateUtil.format(DateUtil.addDays(now, 2), DateUtil.noSecondFormat));
        modelMap.put("todayOffWork6", DateUtil.format(now, DateUtil.webFormat) + " 18:00");
        modelMap.put("todayOffWork22", DateUtil.format(now, DateUtil.webFormat) + " 22:00");
        return modelMap;
    }

}
