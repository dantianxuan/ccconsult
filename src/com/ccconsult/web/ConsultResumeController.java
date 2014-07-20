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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ccconsult.base.AssertUtil;
import com.ccconsult.base.BlankServiceCallBack;
import com.ccconsult.base.CcConstrant;
import com.ccconsult.base.CcResult;
import com.ccconsult.core.ConsultComponent;
import com.ccconsult.core.FileComponent;
import com.ccconsult.dao.ConsultDAO;
import com.ccconsult.dao.CounselorDAO;
import com.ccconsult.dao.MessageDAO;
import com.ccconsult.dao.ResumeConsultDAO;
import com.ccconsult.dao.ServiceConfigDAO;
import com.ccconsult.dao.ServiceDAO;
import com.ccconsult.enums.ConsultStepEnum;
import com.ccconsult.enums.DataStateEnum;
import com.ccconsult.enums.FileTypeEnum;
import com.ccconsult.enums.MessageRelTypeEnum;
import com.ccconsult.enums.PayStateEnum;
import com.ccconsult.pojo.Consult;
import com.ccconsult.pojo.Consultant;
import com.ccconsult.pojo.ResumeConsult;
import com.ccconsult.pojo.Service;
import com.ccconsult.util.CodeGenUtil;
import com.ccconsult.util.DateUtil;
import com.ccconsult.util.StringUtil;
import com.ccconsult.view.ConsultBase;
import com.ccconsult.view.CounselorVO;
import com.ccconsult.view.MessageVO;
import com.ccconsult.view.ResumeConsultVO;
import com.ccconsult.view.ServiceConfigVO;

/**
 * @author jingyu.dan
 * 
 */
@Controller
public class ConsultResumeController extends BaseController {

    @Autowired
    private ServiceDAO       serviceDAO;
    @Autowired
    private CounselorDAO     counselorDAO;
    @Autowired
    private ConsultDAO       consultDAO;
    @Autowired
    private MessageDAO       messageDAO;
    @Autowired
    private ConsultComponent consultComponent;
    @Autowired
    private FileComponent    fileComponent;
    @Autowired
    private ResumeConsultDAO resumeConsultDAO;
    @Autowired
    private ServiceConfigDAO serviceConfigDAO;

    @RequestMapping(value = "/consultant/consult/createresumeConsultInit.htm", method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request, final String serviceConfigId,
                                      final ModelMap modelMap) {
        ModelAndView view = new ModelAndView("consultant/consult/createResumeConsult");
        CcResult result = serviceTemplate.execute(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                int configId = NumberUtils.toInt(serviceConfigId);
                AssertUtil.state(configId > 0, "非法的创建请求！");
                ServiceConfigVO serviceConfigVO = serviceConfigDAO.findVoById(configId);
                AssertUtil.state(
                    serviceConfigVO != null
                            && serviceConfigVO.getServiceConfig().getState()
                                .equals(DataStateEnum.NORMAL.getValue()), "当前服务设定过期或者被修改，请重新预约！");
                modelMap.put("serviceConfigVO", serviceConfigVO);
                return new CcResult(true);
            }
        });
        modelMap.put("result", result);
        return view;
    }

    /**
     * 公司邮箱链接注册
     * @return
     */
    @RequestMapping(value = "consultant/consult/createResumeConsult.htm", method = RequestMethod.POST)
    public ModelAndView createResume(final HttpServletRequest request, final Consult consult,
                                     @RequestParam final MultipartFile[] localFile,
                                     final ModelMap modelMap) {
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
                ServiceConfigVO serviceConfigVO = serviceConfigDAO.findVoById(consult
                    .getServiceConfigId());
                AssertUtil.state(
                    serviceConfigVO != null
                            && serviceConfigVO.getServiceConfig().getState()
                                .equals(DataStateEnum.NORMAL.getValue()), "服务配置对象当前过期，请查询发起咨询");
                modelMap.put("serviceConfigVO", serviceConfigVO);
                //创建一个咨询记录
                consult.setGmtCreate(new Date());
                consult.setGmtModified(new Date());
                consult.setPayTag(PayStateEnum.WAIT_FOR_PAY.getValue());
                consult.setStep(ConsultStepEnum.CREATE.getValue());
                Service service = serviceDAO.findById(consult.getServiceId());
                AssertUtil.notNull(service, "服务不存在，请检查");
                consult.setGmtEffectEnd(DateUtil.addHours(new Date(), service.getEffectTime()));
                consult.setIndetityCode(CodeGenUtil.getFixLenthString(6));
                consultDAO.save(consult);
                //创建面试咨询记录
                ResumeConsult resumeConsult = new ResumeConsult();
                resumeConsult.setConsultId(consult.getId());
                resumeConsult.setResumeFiles(fileName);
                resumeConsultDAO.save(resumeConsult);
                return new CcResult(consult);
            }
        });
        if (!result.isSuccess()) {
            ServiceConfigVO serviceConfigVO = serviceConfigDAO.findVoById(consult
                .getServiceConfigId());
            modelMap.put("serviceConfigVO", serviceConfigVO);
            modelMap.put("consult", consult);
            modelMap.put("result", result);
            return new ModelAndView("consultant/consult/createResumeConsult");
        }
        return new ModelAndView("redirect:/consultant/consult/payForConsult.htm?consultId="
                                + consult.getId());
    }

    @RequestMapping(value = "/consultant/consult/resumeConsult.htm", method = RequestMethod.GET)
    public ModelAndView innerConsult(final HttpServletRequest request, final String consultId,
                                     final ModelMap modelMap) {
        ModelAndView view = new ModelAndView("consultant/consult/resumeConsult");
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

    //~~~~~~~~~~counselor~~~~~~~~~~~~    
    @RequestMapping(value = "/counselor/consult/resumeConsult.htm", method = RequestMethod.GET)
    public ModelAndView innerConsultCounselor(final HttpServletRequest request,
                                              final String consultId, final ModelMap modelMap) {
        ModelAndView view = new ModelAndView("counselor/consult/resumeConsult");
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

    /**
     * 公司邮箱链接注册
     * @return
     */
    @RequestMapping(value = "counselor/consult/resumeConsultReview.htm", method = RequestMethod.POST)
    public @ResponseBody
    ModelMap updateResumeReview(final HttpServletRequest request, final Integer consultId,
                                final String review, @RequestParam final MultipartFile[] localFile,
                                final ModelMap modelMap) {
        modelMap.clear();
        CcResult result = serviceTemplate.executeWithTx(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                AssertUtil.state(consultId != null && consultId > 0, "非法请求！");
                ConsultBase consultBase = consultComponent.queryById(consultId);
                CounselorVO counselorVO = getCounselorInSession(request.getSession());
                AssertUtil.state(review != null, "必须添加备注信息");
                AssertUtil.state(review.length() <= CcConstrant.COMMON_4096_LENGTH, "备注长度不能太长");
                AssertUtil.state(
                    counselorVO != null
                            && consultBase.getCounselorVO().getCounselor().getId()
                                .equals(counselorVO.getCounselor().getId()), "非法请求！");
                ResumeConsultVO resumeConsultVO = (ResumeConsultVO) consultBase;
                String contextPath = request.getSession().getServletContext().getRealPath("/");
                String reviewFile = "";
                for (MultipartFile myfile : localFile) {
                    AssertUtil.state(!myfile.isEmpty(), "必须上传您的简历文件！");
                    reviewFile = fileComponent.uploadFile(myfile, FileTypeEnum.RESUME, contextPath,
                        CcConstrant.FILE_2M_SIZE, "doc|pdf|docx");
                }
                ResumeConsult localResult = resumeConsultVO.getResumeConsult();
                localResult.setReviewFiles(reviewFile);
                localResult.setReview(review);
                resumeConsultDAO.update(localResult);
                return new CcResult(localResult);
            }
        });
        modelMap.put("result", result);
        return modelMap;
    }

    //完成预约记录
    @RequestMapping(value = "counselor/consult/completeResumeConsult.json", method = RequestMethod.POST)
    public @ResponseBody
    ModelMap completeConsult(final HttpServletRequest request, final Integer consultId,
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
                ResumeConsult resumeConsult = ((ResumeConsultVO) consultBase).getResumeConsult();
                AssertUtil.state(StringUtil.isNotBlank(resumeConsult.getReview()),
                    "您未做任何评价不能完成这次咨询");
                Consult consult = consultBase.getConsult();
                consult.setStep(ConsultStepEnum.FIHSHED.getValue());//将状态设定为已经完成
                consult.setGmtModified(new Date());
                consultDAO.update(consult);
                return new CcResult(consult);
            }
        });
        modelMap.put("result", result);
        return modelMap;
    }
}
