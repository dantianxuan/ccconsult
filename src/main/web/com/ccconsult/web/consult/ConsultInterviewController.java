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
import com.ccconsult.base.enums.ConsultStepEnum;
import com.ccconsult.base.enums.ConsultantAppriseEnum;
import com.ccconsult.base.enums.DataStateEnum;
import com.ccconsult.base.enums.FileTypeEnum;
import com.ccconsult.base.enums.MessageRelTypeEnum;
import com.ccconsult.base.enums.PayStateEnum;
import com.ccconsult.base.util.CodeGenUtil;
import com.ccconsult.base.util.DateUtil;
import com.ccconsult.base.util.ScheduleTimeUtil;
import com.ccconsult.base.util.StringUtil;
import com.ccconsult.core.ConsultComponent;
import com.ccconsult.core.FileComponent;
import com.ccconsult.dao.ConsultDAO;
import com.ccconsult.dao.ConsultInterviewDAO;
import com.ccconsult.dao.CounselorDAO;
import com.ccconsult.dao.MessageDAO;
import com.ccconsult.dao.ServiceConfigDAO;
import com.ccconsult.dao.ServiceDAO;
import com.ccconsult.pojo.Consult;
import com.ccconsult.pojo.ConsultInterview;
import com.ccconsult.pojo.Consultant;
import com.ccconsult.pojo.Service;
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
    private CounselorDAO        counselorDAO;
    @Autowired
    private ConsultDAO          consultDAO;
    @Autowired
    private ConsultInterviewDAO consultInterviewDAO;
    @Autowired
    private MessageDAO          messageDAO;
    @Autowired
    private ServiceDAO          serviceDAO;
    @Autowired
    private ConsultComponent    consultComponent;
    @Autowired
    private ServiceConfigDAO    serviceConfigDAO;
    @Autowired
    private FileComponent       fileComponent;

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
            /** 
             * @see com.ccconsult.base.ServiceCallBack#check()
             */
            @Override
            public void check() {
                Consultant consultant = getConsultantInSession(request.getSession());
                AssertUtil.notNull(consultant, "不合法的用户");
                consult.getCounselorId();
                CounselorVO counselorVO = counselorDAO.findById(consult.getCounselorId());
                AssertUtil.notNull(counselorVO, "咨询对象不存在，请检查");
                AssertUtil.notBlank(consult.getGoal(), "请填写您的咨询目的，请检查");
                AssertUtil.state(consult.getGoal().length() <= CcConstrant.COMMON_4096_LENGTH,
                    "描述问题太长，请简洁描述");
            }

            @Override
            public CcResult executeService() {
                String fileName = "";
                String contextPath = request.getSession().getServletContext().getRealPath("/");
                for (MultipartFile myfile : localFile) {
                    AssertUtil.state(!myfile.isEmpty(), "必须上传您的简历文件！");
                    fileName = fileComponent.uploadFile(myfile, FileTypeEnum.RESUME, contextPath,
                        CcConstrant.FILE_2M_SIZE, "doc|pdf|docx");
                }
                ServiceConfig serviceConfig = serviceConfigDAO.findById(consult
                    .getServiceConfigId());
                AssertUtil.state(
                    serviceConfig != null
                            && serviceConfig.getState().equals(DataStateEnum.NORMAL.getValue()),
                    "服务配置对象当前过期，请查询发起咨询");
                modelMap.put("serviceConfig", serviceConfig);
                //创建一个咨询记录
                consult.setGmtCreate(new Date());
                consult.setGmtModified(new Date());
                consult.setPayTag(PayStateEnum.WAIT_FOR_PAY.getValue());
                consult.setStep(ConsultStepEnum.CREATE.getValue());
                Service service = serviceDAO.findById(consult.getServiceId());
                AssertUtil.notNull(service, "服务不存在，请检查");
                //查询当天预约的信息
                List<String> myScheduleTime = ScheduleTimeUtil.getSchuleTime(serviceConfig
                    .getWorkOnTime());

                AssertUtil.state(myScheduleTime.contains(scheduleTime), "对不起，该时间段不能预约");
                AssertUtil.notBlank(scheduleDay, "预约日期必须正常设置");
                int day = NumberUtils.toInt(scheduleDay);
                String times = ScheduleTimeUtil.getScheduleMap().get(scheduleTime);
                Date scheduleBegin = DateUtil.parseDate(
                    DateUtil.format(DateUtil.addDays(new Date(), day), "yyyy-MM-dd ")
                            + ScheduleTimeUtil.getBegin(times), "yyyy-MM-dd HH:mm");
                Date scheduleEnd = DateUtil.parseDate(
                    DateUtil.format(DateUtil.addDays(new Date(), day), "yyyy-MM-dd ")
                            + ScheduleTimeUtil.getEnd(times), "yyyy-MM-dd HH:mm");
                AssertUtil.state(scheduleBegin.after(new Date()), "对不起，当前预约时间已经过期，请重新选择");

                consult.setGmtEffectEnd(DateUtil.addHours(new Date(), service.getEffectTime()));
                consult.setIndetityCode(CodeGenUtil.getFixLenthString(6));
                consultDAO.save(consult);

                //创建面试咨询记录
                ConsultInterview consultInterview = new ConsultInterview();
                consultInterview.setGmtScheduleBegin(scheduleBegin);
                consultInterview.setGmtScheduleEnd(scheduleEnd);
                consultInterview.setSchedueTime(scheduleDay + CcConstrant.ALT_SEPARATOR
                                                + scheduleTime);
                consultInterview.setConsultId(consult.getId());
                consultInterview.setResumeFile(fileName);
                consultInterviewDAO.save(consultInterview);
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
    @RequestMapping(value = "/counselor/consult/consultInterview.htm", method = RequestMethod.GET)
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
        genMap(modelMap);
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
        genMap(modelMap);
        modelMap.put("result", result);
        return view;
    }

    //完成预约记录
    @RequestMapping(value = "/counselor/consult/confirmConsultInterview.json", method = RequestMethod.POST)
    public @ResponseBody
    ModelMap confirmWithIndetityCode(final HttpServletRequest request, final Integer consultId,
                                     final String memo, final String indentityCode,
                                     final Integer consultantApprise, ModelMap modelMap) {
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
                consultInterview.setInterviewMemo(memo);
                AssertUtil.state(consultantApprise != null, "必须对咨询者进行评价");
                ConsultantAppriseEnum consultantAppriseEnum = ConsultantAppriseEnum
                    .getByValue(consultantApprise);
                AssertUtil.state(consultantAppriseEnum != null, "评价信息不能为空");
                consult.setGmtModified(new Date());
                consult.setStep(ConsultStepEnum.FIHSHED.getValue());
                AssertUtil.state(
                    indentityCode != null
                            && StringUtil.equals(consult.getIndetityCode(), indentityCode),
                    "对不起，验证码不准确");
                consultDAO.update(consult);
                consultInterviewDAO.update(consultInterview);
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

    //完成预约记录
    @RequestMapping(value = "/counselor/consult/scheduleInterview.json", method = RequestMethod.POST)
    public @ResponseBody
    ModelMap completeConsult(final HttpServletRequest request, final Integer consultId,
                             final String scheduleBegin, final String scheduleEnd, ModelMap modelMap) {
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
                setScheduleTime(consultInterview, scheduleBegin, scheduleEnd);
                Consult consult = consultBase.getConsult();
                consult.setStep(ConsultStepEnum.ON_CONSULT.getValue());//将状态设定为进行中
                consult.setGmtModified(new Date());
                consultDAO.update(consult);
                consultInterviewDAO.update(consultInterview);
                return new CcResult(true);
            }
        });
        modelMap.put("result", result);
        return modelMap;
    }

    private void setScheduleTime(ConsultInterview consultInterview, String begin, String end) {
        AssertUtil.state(StringUtil.isNotBlank(begin) && StringUtil.isNotBlank(end), "预约时间不能设置为空");
        Date beginTime = DateUtil.parseDate(begin, DateUtil.noSecondFormat);
        Date endTime = DateUtil.parseDate(end, DateUtil.noSecondFormat);
        AssertUtil.state(beginTime != null && end != null, "预约时间不能设置为空");
        AssertUtil.state(beginTime.before(endTime), "预约时间不能倒置");
        AssertUtil.state(DateUtil.getDiffMinutes(endTime, beginTime) >= 60, "预约时间长度不能低于一小时");
        AssertUtil.state(DateUtil.getDiffMinutes(endTime, beginTime) >= 24 * 7, "预约时间最长不能超过7天");
        consultInterview.setGmtScheduleBegin(beginTime);
        consultInterview.setGmtScheduleEnd(endTime);
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
