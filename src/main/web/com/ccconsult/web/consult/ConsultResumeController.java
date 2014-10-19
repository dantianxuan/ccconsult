/**
 * 
 */
package com.ccconsult.web.consult;

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
import com.ccconsult.base.enums.DataStateEnum;
import com.ccconsult.base.enums.FileTypeEnum;
import com.ccconsult.base.enums.MessageRelTypeEnum;
import com.ccconsult.core.consult.ConsultQueryComponent;
import com.ccconsult.core.file.FileComponent;
import com.ccconsult.dao.ConsultDAO;
import com.ccconsult.dao.ConsultResumeDAO;
import com.ccconsult.dao.MessageDAO;
import com.ccconsult.dao.ServiceConfigDAO;
import com.ccconsult.pojo.Consult;
import com.ccconsult.pojo.ConsultResume;
import com.ccconsult.pojo.Consultant;
import com.ccconsult.service.ConsultResumeService;
import com.ccconsult.web.BaseController;
import com.ccconsult.web.view.ConsultBase;
import com.ccconsult.web.view.ConsultResumeVO;
import com.ccconsult.web.view.CounselorVO;
import com.ccconsult.web.view.MessageVO;
import com.ccconsult.web.view.ServiceConfigVO;

/**
 * @author jingyu.dan
 * 
 */
@Controller
public class ConsultResumeController extends BaseController {

    @Autowired
    private ConsultDAO            consultDAO;
    @Autowired
    private MessageDAO            messageDAO;
    @Autowired
    private FileComponent         fileComponent;
    @Autowired
    private ConsultResumeDAO      consultResumeDAO;
    @Autowired
    private ServiceConfigDAO      serviceConfigDAO;
    @Autowired
    private ConsultQueryComponent consultComponent;
    @Autowired
    private ConsultResumeService  consultResumeService;

    @RequestMapping(value = "/consultant/consult/createConsultResumeInit.htm", method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request, final String serviceConfigId,
                                      final ModelMap modelMap) {
        ModelAndView view = new ModelAndView("consultant/consult/createConsultResume");
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
    @RequestMapping(value = "/consultant/consult/createConsultResume.json", method = RequestMethod.POST)
    public @ResponseBody ModelMap createResume(final HttpServletRequest request,
                                               final Consult consult,
                                               @RequestParam final MultipartFile[] localFile,
                                               final ModelMap modelMap) {
        modelMap.clear();
        String contextPath = request.getSession().getServletContext().getRealPath("/");
        CcResult result = consultResumeService.create(consult, localFile, contextPath);
        ServiceConfigVO serviceConfigVO = serviceConfigDAO.findVoById(consult.getServiceConfigId());
        modelMap.put("serviceConfigVO", serviceConfigVO);
        modelMap.put("result", result);
        return modelMap;
    }

    @RequestMapping(value = "/consultant/consult/goConsultResume.htm", method = RequestMethod.GET)
    public ModelAndView innerConsult(final HttpServletRequest request, final String consultId,
                                     final ModelMap modelMap) {
        ModelAndView view = new ModelAndView("consultant/consult/consultResume");
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
    @RequestMapping(value = "/counselor/consult/goConsultResume.htm", method = RequestMethod.GET)
    public ModelAndView innerConsultCounselor(final HttpServletRequest request,
                                              final String consultId, final ModelMap modelMap) {
        ModelAndView view = new ModelAndView("counselor/consult/consultResume");
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
    @RequestMapping(value = "counselor/consult/consultResumeReview.htm", method = RequestMethod.POST)
    public @ResponseBody ModelMap updateResumeReview(final HttpServletRequest request,
                                                     final Integer consultId, final String review,
                                                     @RequestParam final MultipartFile[] localFile,
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
                ConsultResumeVO resumeConsultVO = (ConsultResumeVO) consultBase;
                String contextPath = request.getSession().getServletContext().getRealPath("/");
                String reviewFile = "";
                for (MultipartFile myfile : localFile) {
                    AssertUtil.state(!myfile.isEmpty(), "必须上传您的简历文件！");
                    reviewFile = fileComponent.uploadFile(myfile, FileTypeEnum.RESUME, contextPath,
                        CcConstrant.FILE_2M_SIZE, "doc|pdf|docx");
                }
                ConsultResume localResult = resumeConsultVO.getConsultResume();
                localResult.setReviewFiles(reviewFile);
                localResult.setReview(review);
                consultResumeDAO.update(localResult);
                return new CcResult(localResult);
            }
        });
        modelMap.put("result", result);
        return modelMap;
    }

}
