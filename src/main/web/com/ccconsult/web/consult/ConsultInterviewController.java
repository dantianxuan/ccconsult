/**
 * 
 */
package com.ccconsult.web.consult;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ccconsult.base.AssertUtil;
import com.ccconsult.base.BlankServiceCallBack;
import com.ccconsult.base.CcConstrant;
import com.ccconsult.base.CcResult;
import com.ccconsult.base.enums.AppriseRelTypeEnum;
import com.ccconsult.base.enums.ConsultStepEnum;
import com.ccconsult.base.enums.DataStateEnum;
import com.ccconsult.base.enums.FileTypeEnum;
import com.ccconsult.base.enums.MessageRelTypeEnum;
import com.ccconsult.base.enums.UserRoleEnum;
import com.ccconsult.base.util.StringUtil;
import com.ccconsult.core.consult.ConsultInterviewComponent;
import com.ccconsult.core.consult.ConsultQueryComponent;
import com.ccconsult.core.file.FileComponent;
import com.ccconsult.dao.AppriseDAO;
import com.ccconsult.dao.ConsultDAO;
import com.ccconsult.dao.ConsultInterviewDAO;
import com.ccconsult.dao.MessageDAO;
import com.ccconsult.dao.ServiceConfigDAO;
import com.ccconsult.pojo.Apprise;
import com.ccconsult.pojo.Consult;
import com.ccconsult.pojo.ConsultInterview;
import com.ccconsult.pojo.Consultant;
import com.ccconsult.pojo.ServiceConfig;
import com.ccconsult.web.BaseController;
import com.ccconsult.web.view.ConsultBase;
import com.ccconsult.web.view.CounselorVO;
import com.ccconsult.web.view.CounsultInterviewVO;
import com.ccconsult.web.view.MessageVO;

/**
 * @author jingyu.dan
 * 
 */
@Controller
public class ConsultInterviewController extends BaseController {

    @Autowired
    private ConsultDAO                consultDAO;
    @Autowired
    private AppriseDAO                appriseDAO;
    @Autowired
    private ConsultInterviewDAO       consultInterviewDAO;
    @Autowired
    private ConsultInterviewComponent consultInterviewComponent;
    @Autowired
    private MessageDAO                messageDAO;
    @Autowired
    private ConsultQueryComponent     consultComponent;
    @Autowired
    private ServiceConfigDAO          serviceConfigDAO;
    @Autowired
    private FileComponent             fileComponent;

    @RequestMapping(value = "/consultant/consult/createConsultInterviewInit.htm", method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request, final String serviceConfigId,
                                      ModelMap modelMap) {
        ModelAndView view = new ModelAndView("consultant/consult/createConsultInterview");
        CcResult result = serviceTemplate.execute(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                int configId = NumberUtils.toInt(serviceConfigId);
                AssertUtil.state(configId > 0, "非法的创建请求！");
                ServiceConfig serviceConfig = serviceConfigDAO.findById(configId);
                AssertUtil.state(
                    serviceConfig != null
                            && serviceConfig.getState().equals(DataStateEnum.NORMAL.getValue()),
                    "当前服务设定过期或者被修改，请重新预约！");
                return new CcResult(serviceConfig);
            }
        });
        modelMap.put("result", result);
        return view;
    }

    /**
     * 公司邮箱链接注册
     * @return
     */
    @RequestMapping(value = "/consultant/consult/createConsultInterview.htm", method = RequestMethod.POST)
    public @ResponseBody
    ModelMap createInterviewJson(final HttpServletRequest request, final Consult consult,
                                 final String scheduleTime, final String scheduleDay,
                                 @RequestParam final MultipartFile[] localFile,
                                 final ModelMap modelMap) {
        modelMap.clear();
        CcResult result = serviceTemplate.executeWithTx(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                AssertUtil.state(
                    validInSession(consult.getConsultantId(), UserRoleEnum.CONSULTANT,
                        request.getSession()), "不合法的用户");
                String fileName = "";
                String contextPath = request.getSession().getServletContext().getRealPath("/");
                for (MultipartFile myfile : localFile) {
                    AssertUtil.state(!myfile.isEmpty(), "必须上传您的简历文件！");
                    fileName = fileComponent.uploadFile(myfile, FileTypeEnum.RESUME, contextPath,
                        CcConstrant.FILE_2M_SIZE, "doc|pdf|docx");
                }
                consultInterviewComponent.createConsult(consult, fileName, scheduleTime,
                    scheduleDay);
                return new CcResult(consult);
            }
        });

        modelMap.put("result", result);
        return modelMap;
    }

    @RequestMapping(value = "/consultant/consult/goConsultInterview.htm", method = RequestMethod.GET)
    public ModelAndView consultInterview(final HttpServletRequest request, final String consultId,
                                         final ModelMap modelMap) {
        ModelAndView view = new ModelAndView("/consultant/consult/consultInterview");
        CcResult result = serviceTemplate.execute(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                int consuid = NumberUtils.toInt(consultId);
                AssertUtil.state(consuid > 0, "非法请求！");
                ConsultBase consultBase = consultComponent.queryById(consuid);
                Consultant consultant = getConsultantInSession(request.getSession());
                AssertUtil.state(
                    consultant != null
                            && consultBase.getConsultant().getId().equals(consultant.getId()),
                    "非法请求！");
                List<MessageVO> messageVOs = messageDAO.queryByRelInfo(consuid,
                    MessageRelTypeEnum.CONSULT.getValue());
                modelMap.put("consultBase", consultBase);
                modelMap.put("messageVOs", messageVOs);
                return new CcResult(true);
            }
        });
        modelMap.put("result", result);
        return view;
    }

    //----------counselor--------------------
    @RequestMapping(value = "/counselor/consult/goConsultInterview.htm", method = RequestMethod.GET)
    public ModelAndView counselorInnerConsult(final HttpServletRequest request,
                                              final String consultId, final ModelMap modelMap) {
        ModelAndView view = new ModelAndView("/counselor/consult/consultInterview");
        CcResult result = serviceTemplate.execute(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                int consuid = NumberUtils.toInt(consultId);
                AssertUtil.state(consuid > 0, "非法请求！");
                ConsultBase consultBase = consultComponent.queryById(consuid);
                CounselorVO counselorVO = getCounselorInSession(request.getSession());
                AssertUtil.state(
                    counselorVO != null
                            && consultBase.getCounselorVO().getCounselor().getId()
                                .equals(counselorVO.getCounselor().getId()), "非法请求！");
                List<MessageVO> messageVOs = messageDAO.queryByRelInfo(consuid,
                    MessageRelTypeEnum.CONSULT.getValue());
                modelMap.put("consultBase", consultBase);
                modelMap.put("messageVOs", messageVOs);
                return new CcResult(true);
            }
        });
        modelMap.put("result", result);
        return view;
    }

    //----------counselor--------------------
    @RequestMapping(value = "/counselor/consult/consultInterviewComplete.htm", method = RequestMethod.GET)
    public ModelAndView interviewConsultComplete(final HttpServletRequest request,
                                                 final String consultId, final ModelMap modelMap) {
        ModelAndView view = new ModelAndView("/counselor/consult/consultInterviewComplete");
        CcResult result = serviceTemplate.execute(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                int consuid = NumberUtils.toInt(consultId);
                AssertUtil.state(consuid > 0, "非法请求！");
                ConsultBase consultBase = consultComponent.queryById(consuid);
                CounselorVO counselorVO = getCounselorInSession(request.getSession());
                AssertUtil.state(
                    counselorVO != null
                            && consultBase.getCounselorVO().getCounselor().getId()
                                .equals(counselorVO.getCounselor().getId()), "非法请求！");
                modelMap.put("consultBase", consultBase);
                return new CcResult(true);
            }
        });
        modelMap.put("result", result);
        return view;
    }

    //完成预约记录
    @RequestMapping(value = "/counselor/consult/confirmInterview.json", method = RequestMethod.POST)
    public @ResponseBody
    ModelMap confirmWithIndetityCode(final HttpServletRequest request, final Integer consultId,
                                     final Apprise apprise, final String indentityCode,
                                     ModelMap modelMap) {
        modelMap.clear();
        CcResult result = serviceTemplate.executeWithTx(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                AssertUtil.state(consultId != null && consultId > 0, "非法请求，当前记录不存在");
                CounselorVO counselorVO = getCounselorInSession(request.getSession());
                AssertUtil.notNull(counselorVO, "当前请求过期，请刷新页面或登录后重试");
                ConsultBase consultBase = consultComponent.queryById(consultId);
                AssertUtil.state(
                    consultBase.getConsult().getCounselorId()
                        .equals(counselorVO.getCounselor().getId()), "请不要尝试修改不属于您的记录");
                ConsultInterview consultInterview = ((CounsultInterviewVO) consultBase)
                    .getConsultInterview();
                Consult consult = consultBase.getConsult();
                AssertUtil.state(consult.getStep().equals(ConsultStepEnum.ON_CONSULT.getValue()),
                    "当前记录不是进行状态，不能验证");
                AssertUtil.state(consult.getServiceId().equals(4), "当前记录不是面试咨询服务，不能进行确认");
                AssertUtil.state(apprise != null, "必须对咨询者进行评价");
                AssertUtil.state(apprise.getMemo() != null, "评价信息不能为空");
                consultInterview.setInterviewMemo(apprise.getMemo());
                consult.setGmtModified(new Date());
                consult.setStep(ConsultStepEnum.FIHSHED.getValue());
                AssertUtil.state(
                    indentityCode != null
                            && StringUtil.equals(consult.getIndetityCode(), indentityCode),
                    "对不起，验证码不准确");
                consultDAO.update(consult);
                consultInterviewDAO.update(consultInterview);
                apprise.setCreator(counselorVO.getCounselor().getId());
                apprise.setCreatorRole(UserRoleEnum.COUNSELOR.getValue());
                apprise.setRelId(consultId);
                apprise.setRelType(AppriseRelTypeEnum.APPRISE_INTERVIEW_USER.getValue());
                appriseDAO.save(apprise);
                return new CcResult(true);
            }
        });
        modelMap.put("result", result);
        return modelMap;
    }

    //完成预约记录
    @RequestMapping(value = "/counselor/consult/consultInterviewComplete.json", method = RequestMethod.POST)
    public @ResponseBody
    ModelMap completeConsultInterview(final HttpServletRequest request, final Integer consultId,
                                      String indentityCode, ModelMap modelMap) {
        modelMap.clear();
        CcResult result = serviceTemplate.executeWithTx(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                AssertUtil.state(consultId != null && consultId > 0, "非法请求，当前记录不存在");
                CounselorVO counselorVO = getCounselorInSession(request.getSession());
                AssertUtil.notNull(counselorVO, "当前请求过期，请刷新页面或登录后重试");
                ConsultBase consultBase = consultComponent.queryById(consultId);
                AssertUtil.state(
                    consultBase.getConsult().getCounselorId()
                        .equals(counselorVO.getCounselor().getId()), "请不要尝试修改不属于您的记录");
                ConsultInterview consultInterview = ((CounsultInterviewVO) consultBase)
                    .getConsultInterview();
                Consult consult = consultBase.getConsult();
                consult.setStep(ConsultStepEnum.FIHSHED.getValue());//将状态设定为进行中
                consult.setGmtModified(new Date());
                consultDAO.update(consult);
                consultInterviewDAO.update(consultInterview);
                return new CcResult(true);
            }
        });
        modelMap.put("result", result);
        return modelMap;
    }

}
