/**
 * 
 */
package com.ccconsult.web;

import java.util.Date;

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
import com.ccconsult.base.CcConstrant;
import com.ccconsult.base.CcResult;
import com.ccconsult.dao.AppriseDAO;
import com.ccconsult.dao.ConsultDAO;
import com.ccconsult.dao.ConsultantDAO;
import com.ccconsult.dao.CounselorDAO;
import com.ccconsult.enums.AppriseEnum;
import com.ccconsult.enums.AppriseRelTypeEnum;
import com.ccconsult.enums.ConsultStepEnum;
import com.ccconsult.enums.UserRoleEnum;
import com.ccconsult.pojo.Apprise;
import com.ccconsult.pojo.Consult;
import com.ccconsult.pojo.Consultant;
import com.ccconsult.util.StringUtil;
import com.ccconsult.view.CounselorVO;

/**
 * @author jingyu.dan
 * 
 */
@Controller
public class AppriseController extends BaseController {

    @Autowired
    private CounselorDAO  counselorDAO;
    @Autowired
    private ConsultDAO    consultDAO;
    @Autowired
    private ConsultantDAO consultantDAO;
    @Autowired
    private AppriseDAO    appriseDAO;

    @RequestMapping(value = "consultant/appriseInterview.htm", method = RequestMethod.GET)
    public ModelAndView toConsultantAppriaseInterview(HttpServletRequest request,
                                                      final String consultId,
                                                      final ModelMap modelMap) {
        ModelAndView view = new ModelAndView("consultant/appriseInterview");
        final Consultant consultant = getConsultantInSession(request.getSession());
        CcResult result = serviceTemplate.execute(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                Consult consult = consultDAO.findById(NumberUtils.toInt(consultId));
                AssertUtil.notNull(consult, "记录不存在");
                CounselorVO counselorVO = counselorDAO.findById(consult.getCounselorId());
                AssertUtil.state(consultant.getId().equals(consult.getConsultantId()), "不是您的记录");
                modelMap.put("counselorVO", counselorVO);
                modelMap.put("consult", consult);
                return new CcResult(true);
            }
        });
        modelMap.put("result", result);
        return view;
    }

    @RequestMapping(value = "consultant/appriseInterview.json", method = RequestMethod.POST)
    public @ResponseBody
    ModelMap consultantApprise(final HttpServletRequest request, final Apprise apprise,
                               final ModelMap modelMap) {
        modelMap.clear();
        CcResult result = serviceTemplate.executeWithTx(CcResult.class, new BlankServiceCallBack() {
            /** 
             * @see com.ccconsult.base.ServiceCallBack#check()
             */
            @Override
            public void check() {
                AssertUtil.state((apprise.getRelId() != null && apprise.getRelId() > 0), "面试记录不存在");
                AssertUtil.notNull(
                    (apprise.getApprise() != null && AppriseEnum.getByValue(apprise.getApprise()) != null),
                    "请评价");
                if (!StringUtil.isBlank(apprise.getMemo())) {
                    AssertUtil.state(apprise.getMemo().length() < CcConstrant.COMMON_4096_LENGTH,
                        "评价长度太长");
                }

            }

            @Override
            public CcResult executeService() {
                Consultant consultant = getConsultantInSession(request.getSession());
                AssertUtil.notNull(consultant, "对不起，当前状态未登录，刷新当前页面后重新评价");
                Consult consult = consultDAO.findById(apprise.getRelId());
                AssertUtil.notNull(consult, "记录不存在，请检查或查询登录");
                AssertUtil.state(consultant.getId().equals(consult.getConsultantId()),
                    "对不起，您当前评价的不是您的记录");
                apprise.setGmtCreate(new Date());
                apprise.setCreator(consultant.getId());
                apprise.setCreatorRole(UserRoleEnum.CONSULTANT.getValue());
                apprise.setRelType(AppriseRelTypeEnum.INTERVIEW.getValue());
                Apprise localApprise = appriseDAO.findByRelId(apprise.getRelId(),
                    apprise.getRelType(), apprise.getCreator(), apprise.getCreatorRole());
                AssertUtil.state(localApprise == null, "对不起，您已经评价了，不能重复评价");
                appriseDAO.save(apprise);
                consultDAO.update(consult);
                return new CcResult(consult);
            }
        });
        modelMap.put("result", result);
        return modelMap;
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~counselor ~~~~~~~~~~~~~~~~~~~~~~~

    @RequestMapping(value = "counselor/appriseInterview.json", method = RequestMethod.POST)
    public @ResponseBody
    ModelMap interviewerInterview(final HttpServletRequest request, final Apprise apprise,
                                  ModelMap modelMap) {
        modelMap.clear();
        CcResult result = serviceTemplate.executeWithTx(CcResult.class, new BlankServiceCallBack() {
            /** 
             * @see com.ccconsult.base.ServiceCallBack#check()
             */
            @Override
            public void check() {
                AssertUtil.state((apprise.getRelId() != null && apprise.getRelId() > 0), "面试记录不存在");
                AssertUtil.notNull(
                    (apprise.getApprise() != null && AppriseEnum.getByValue(apprise.getApprise()) != null),
                    "请评价");
                if (!StringUtil.isBlank(apprise.getMemo())) {
                    AssertUtil.state(apprise.getMemo().length() < CcConstrant.COMMON_4096_LENGTH,
                        "评价长度太长");
                }

            }

            @Override
            public CcResult executeService() {
                CounselorVO counselorVO = getCounselorInSession(request.getSession());
                AssertUtil.notNull(counselorVO, "对不起，当前状态未登录，刷新当前页面后重新评价");
                Consult consult = consultDAO.findById(apprise.getRelId());
                AssertUtil.notNull(consult, "记录不存在，请检查或查询登录");
                AssertUtil.state(counselorVO.getCounselor().getId()
                    .equals(consult.getCounselorId()), "对不起，您当前评价的不是您的记录");
                apprise.setGmtCreate(new Date());
                apprise.setCreator(counselorVO.getCounselor().getId());
                apprise.setCreatorRole(UserRoleEnum.COUNSELOR.getValue());
                apprise.setRelType(AppriseRelTypeEnum.INTERVIEW.getValue());
                Apprise localApprise = appriseDAO.findByRelId(apprise.getRelId(),
                    apprise.getRelType(), apprise.getCreator(), apprise.getCreatorRole());
                AssertUtil.state(localApprise == null, "对不起，您已经评价了，不能重复评价");
                appriseDAO.save(apprise);
                consult.setStep(ConsultStepEnum.FIHSHED.getValue());
                consultDAO.update(consult);
                return new CcResult(consult);
            }

        });
        modelMap.put("result", result);
        return modelMap;
    }

    @RequestMapping(value = "counselor/appriseInterview.htm", method = RequestMethod.GET)
    public ModelAndView toCounselorAppriase(HttpServletRequest request, final String consultId,
                                            final ModelMap modelMap) {
        ModelAndView view = new ModelAndView("counselor/appriseInterview");
        final CounselorVO counselorVO = getCounselorInSession(request.getSession());
        CcResult result = serviceTemplate.execute(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                Consult consult = consultDAO.findById(NumberUtils.toInt(consultId));
                AssertUtil.notNull(consult, "记录不存在");
                Consultant consultant = consultantDAO.findById(consult.getConsultantId());
                AssertUtil.state(counselorVO.getCounselor().getId()
                    .equals(consult.getCounselorId()), "不是您的记录");
                modelMap.put("consultant", consultant);
                modelMap.put("consult", consult);
                return new CcResult(true);
            }
        });
        modelMap.put("result", result);
        return view;
    }

}
