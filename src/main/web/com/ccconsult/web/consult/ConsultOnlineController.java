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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ccconsult.base.AssertUtil;
import com.ccconsult.base.BlankServiceCallBack;
import com.ccconsult.base.CcConstrant;
import com.ccconsult.base.CcResult;
import com.ccconsult.core.ConsultComponent;
import com.ccconsult.dao.ConsultDAO;
import com.ccconsult.dao.ConsultOnlineDAO;
import com.ccconsult.dao.CounselorDAO;
import com.ccconsult.dao.MessageDAO;
import com.ccconsult.dao.ServiceConfigDAO;
import com.ccconsult.dao.ServiceDAO;
import com.ccconsult.enums.ConsultStepEnum;
import com.ccconsult.enums.DataStateEnum;
import com.ccconsult.enums.MessageRelTypeEnum;
import com.ccconsult.enums.PayStateEnum;
import com.ccconsult.pojo.Consult;
import com.ccconsult.pojo.ConsultOnline;
import com.ccconsult.pojo.Consultant;
import com.ccconsult.pojo.Service;
import com.ccconsult.pojo.ServiceConfig;
import com.ccconsult.util.CodeGenUtil;
import com.ccconsult.util.DateUtil;
import com.ccconsult.util.ScheduleTimeUtil;
import com.ccconsult.view.ConsultBase;
import com.ccconsult.view.CounselorVO;
import com.ccconsult.view.MessageVO;
import com.ccconsult.web.BaseController;

/**
 * @author jingyu.dan
 * 
 */
@Controller
public class ConsultOnlineController extends BaseController {

    @Autowired
    private CounselorDAO     counselorDAO;
    @Autowired
    private ConsultDAO       consultDAO;
    @Autowired
    private ConsultOnlineDAO consultOnlineDAO;
    @Autowired
    private MessageDAO       messageDAO;
    @Autowired
    private ServiceDAO       serviceDAO;
    @Autowired
    private ConsultComponent consultComponent;
    @Autowired
    private ServiceConfigDAO serviceConfigDAO;

    @RequestMapping(value = "/consultant/consult/createConsultOnlineInit.htm", method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request, final String serviceConfigId,
                                      ModelMap modelMap) {
        ModelAndView view = new ModelAndView("consultant/consult/createConsultOnline");
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
    @RequestMapping(value = "/consultant/consult/createConsultOnline.json", method = RequestMethod.POST)
    public @ResponseBody
    ModelMap createInterviewJson(final HttpServletRequest request, final Consult consult,
                                 final String scheduleTime, final String scheduleDay,
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
                ConsultOnline consultOnline = new ConsultOnline();
                consultOnline.setGmtScheduleBegin(scheduleBegin);
                consultOnline.setGmtScheduleEnd(scheduleEnd);
                consultOnline
                    .setSchedueTime(scheduleDay + CcConstrant.ALT_SEPARATOR + scheduleTime);
                consultOnline.setConsultId(consult.getId());
                consultOnlineDAO.save(consultOnline);
                return new CcResult(consult);
            }
        });
        modelMap.put("result", result);
        return modelMap;
    }

    @RequestMapping(value = "/consultant/consult/goConsultOnline.htm", method = RequestMethod.GET)
    public ModelAndView innerConsult(final HttpServletRequest request, final String consultId,
                                     final ModelMap modelMap) {
        ModelAndView view = new ModelAndView("/consultant/consult/consultOnline");
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
    @RequestMapping(value = "/counselor/consult/consultOnline.htm", method = RequestMethod.GET)
    public ModelAndView counselorInnerConsult(final HttpServletRequest request,
                                              final String consultId, final ModelMap modelMap) {
        ModelAndView view = new ModelAndView("/counselor/consult/consultOnline");
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
    @RequestMapping(value = "/counselor/consult/consultOnlineComplete.htm", method = RequestMethod.GET)
    public ModelAndView interviewConsultComplete(final HttpServletRequest request,
                                                 final String consultId, final ModelMap modelMap) {
        ModelAndView view = new ModelAndView("/counselor/consult/consultOnlineComplete");
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
