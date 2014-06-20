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
import com.ccconsult.base.CcException;
import com.ccconsult.base.CcResult;
import com.ccconsult.dao.CounselorDAO;
import com.ccconsult.dao.InterviewDAO;
import com.ccconsult.enums.InterviewStepEnum;
import com.ccconsult.pojo.Interview;
import com.ccconsult.util.DateUtil;
import com.ccconsult.view.CounselorVO;
import com.ccconsult.view.InterviewVO;

/**
 * @author jingyu.dan
 * 
 */
@Controller
public class CounselorController extends BaseController {
    @Autowired
    private CounselorDAO counselorDAO;
    @Autowired
    private InterviewDAO interviewDAO;

    @RequestMapping(value = "counselor/counselorSelf.htm", method = RequestMethod.GET)
    public ModelAndView counselorSelf(HttpServletRequest request, ModelMap modelMap) {
        ModelAndView view = new ModelAndView("counselor/counselorSelf");
        final CounselorVO counselorVO = getCounselorInSession(request.getSession());
        CcResult result = serviceTemplate.execute(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                List<InterviewVO> counselors = interviewDAO
                    .findUnDelInterviewsByCounselor(counselorVO.getCounselor().getId());
                return new CcResult(counselors);
            }
        });
        modelMap.put("result", result);
        return view;
    }

    @RequestMapping(value = "counselor/scheduleInterview.json", method = RequestMethod.POST)
    public @ResponseBody
    ModelMap scheduleInterview(final HttpServletRequest request, final String scheduleBegin,
                               final String scheduleEnd, final String interviewId, ModelMap modelMap) {
        modelMap.clear();

        CcResult result = serviceTemplate.execute(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                CounselorVO counselorVO = getCounselorInSession(request.getSession());
                Interview interview = interviewDAO.findById(NumberUtils.toInt(interviewId));
                AssertUtil.notNull(interview, "记录不存在，请检查或查询登录");
                AssertUtil.state(
                    interview.getCounselorId().equals(counselorVO.getCounselor().getId()),
                    "当前记录不是您的记录，非法操作");
                AssertUtil.state(interview.getStep().equals(InterviewStepEnum.CREATE.getValue()),
                    "当前记录不能设定预约时间");
                Date begin, end;
                try {
                    begin = DateUtil.parseDateNoTime(scheduleBegin, DateUtil.webFormat);
                    end = DateUtil.parseDateNoTime(scheduleEnd, DateUtil.webFormat);
                } catch (Exception e) {
                    throw new CcException("错误的时间格式");
                }
                AssertUtil.state(begin.before(end), "您选择的时间倒置");
                AssertUtil.state(DateUtil.getDiffDays(end, begin) <= 3, "最长时间不能超过3天");
                interview.setGmtShceduleBegin(begin);
                interview.setGmtScheduleEnd(end);
                interview.setStep(InterviewStepEnum.ON_SCHEDULE.getValue());
                interviewDAO.update(interview);
                return new CcResult(interview);
            }
        });
        modelMap.put("result", result);
        return modelMap;
    }

    @RequestMapping(value = "counselor/personal.htm", method = RequestMethod.GET)
    public ModelAndView counselorPage(HttpServletRequest request, ModelMap modelMap) {
        ModelAndView view = new ModelAndView("counselor/personal");
        return view;
    }

    @RequestMapping(value = "counselorInfo.htm", method = RequestMethod.GET)
    public ModelAndView counselorInfo(HttpServletRequest request, String counselorId,
                                      ModelMap modelMap) {
        ModelAndView view = new ModelAndView("content/counselorInfo");
        int counsId = NumberUtils.toInt(counselorId);
        CounselorVO counselorVO = counselorDAO.findById(counsId);
        modelMap.put("counselorVO", counselorVO);
        return view;
    }

    @RequestMapping(value = "counselor/messages.htm", method = RequestMethod.GET)
    public ModelAndView interviewMessage(HttpServletRequest request, ModelMap modelMap) {
        ModelAndView view = new ModelAndView("counselor/messages");
        return view;
    }

}
