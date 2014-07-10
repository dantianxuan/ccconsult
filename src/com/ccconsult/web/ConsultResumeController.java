/**
 * 
 */
package com.ccconsult.web;

import java.io.File;
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
import com.ccconsult.dao.ResumeDAO;
import com.ccconsult.dao.ServiceConfigDAO;
import com.ccconsult.enums.ConsultStepEnum;
import com.ccconsult.enums.DataStateEnum;
import com.ccconsult.enums.FileTypeEnum;
import com.ccconsult.enums.MessageRelTypeEnum;
import com.ccconsult.enums.PayStateEnum;
import com.ccconsult.pojo.Consult;
import com.ccconsult.pojo.Consultant;
import com.ccconsult.pojo.Resume;
import com.ccconsult.pojo.ResumeConsult;
import com.ccconsult.view.ConsultBase;
import com.ccconsult.view.CounselorVO;
import com.ccconsult.view.MessageVO;
import com.ccconsult.view.ServiceConfigVO;

/**
 * @author jingyu.dan
 * 
 */
@Controller
public class ConsultResumeController extends BaseController {

    @Autowired
    private CounselorDAO     counselorDAO;
    @Autowired
    private ConsultDAO       consultDAO;
    @Autowired
    private MessageDAO       messageDAO;
    @Autowired
    private ConsultComponent consultComponent;
    @Autowired
    private ResumeDAO        resumeDAO;
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
        final Consultant consultant = getConsultantInSession(request.getSession());
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
                Resume resume = resumeDAO.findByConsultantId(consultant.getId());
                modelMap.put("resume", resume);
                return new CcResult(serviceConfigVO);
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
                                     ModelMap modelMap) {
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
                AssertUtil.state(localFile != null && localFile.length == 1, "必须上传您的简历文件！");
            }

            @Override
            public CcResult executeService() {
                String fileName = "";
                String contextPath = request.getSession().getServletContext().getRealPath("/");
                for (MultipartFile myfile : localFile) {
                    if (!myfile.isEmpty()) {
                        File file = fileComponent.uploadFile(myfile, FileTypeEnum.RESUME,
                            contextPath);
                        fileName = file.getName();
                    }
                }
                //创建一个咨询记录
                consult.setGmtCreate(new Date());
                consult.setGmtModified(new Date());
                consult.setPayTag(PayStateEnum.WAIT_FOR_PAY.getValue());
                consult.setStep(ConsultStepEnum.CREATE.getValue());
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
}
