/**
 * 
 */
package com.ccconsult.web;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ccconsult.base.AssertUtil;
import com.ccconsult.base.BlankServiceCallBack;
import com.ccconsult.base.CcConstrant;
import com.ccconsult.base.CcException;
import com.ccconsult.base.CcResult;
import com.ccconsult.dao.ConsultantDAO;
import com.ccconsult.dao.InnerMailDAO;
import com.ccconsult.dao.InterviewDAO;
import com.ccconsult.enums.DataStateEnum;
import com.ccconsult.enums.UserRoleEnum;
import com.ccconsult.pojo.Consultant;
import com.ccconsult.util.LogUtil;
import com.ccconsult.util.StringUtil;
import com.ccconsult.util.ValidateUtil;
import com.ccconsult.view.InterviewVO;

/**
 * @author jingyu.dan
 * 
 */
@Controller
public class ConsultantController extends BaseController {

    /**日志 */
    private static final Logger logger = Logger.getLogger(ConsultantController.class);
    @Autowired
    private InterviewDAO        interviewDAO;
    @Autowired
    private ConsultantDAO       consultantDAO;
    @Autowired
    private InnerMailDAO        innerMailDAO;

    @RequestMapping(value = "/consultant/consultantSelf.htm", method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request, ModelMap modelMap) {
        ModelAndView view = new ModelAndView("consultant/consultantSelf");
        final Consultant consultant = getConsultantInSession(request.getSession());
        CcResult result = serviceTemplate.execute(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                List<InterviewVO> interviewVOs = interviewDAO.findInterviewsConsultant(
                    consultant.getId(), DataStateEnum.NORMAL);
                return new CcResult(interviewVOs);
            }
        });
        modelMap.put("result", result);
        return view;
    }

    @RequestMapping(value = "consultant/personalInfo.htm", method = RequestMethod.GET)
    public ModelAndView showInformation(HttpServletRequest request, ModelMap modelMap) {

        Consultant consultant = getConsultantInSession(request.getSession());
        if (consultant != null) {
            modelMap.put("consultant", consultantDAO.findById(consultant.getId()));
        }
        ModelAndView view = new ModelAndView("consultant/personalInfo");
        return view;
    }

    @RequestMapping(value = "consultant/editPersonalInfo.htm", method = RequestMethod.GET)
    public ModelAndView toInterview(HttpServletRequest request, ModelMap modelMap) {

        Consultant consultant = getConsultantInSession(request.getSession());
        if (consultant != null) {
            modelMap.put("consultant", consultantDAO.findById(consultant.getId()));
        }
        ModelAndView view = new ModelAndView("consultant/editPersonalInfo");
        return view;
    }

    @RequestMapping(value = "consultant/innerMails.htm", method = RequestMethod.GET)
    public ModelAndView toMessages(HttpServletRequest request, ModelMap modelMap) {
        Consultant consultant = getConsultantInSession(request.getSession());
        if (consultant != null) {
            modelMap.put(
                "innerMails",
                innerMailDAO.findByByReceiver(consultant.getId(),
                    UserRoleEnum.CONSULTANT.getValue()));
        }
        ModelAndView view = new ModelAndView("consultant/innerMails");
        return view;
    }

    @RequestMapping(value = "consultant/editPersonalInfo.htm", params = "action=save", method = RequestMethod.POST)
    public ModelAndView editInformation(final HttpServletRequest request, ModelMap modelMap,
                                        @RequestParam final MultipartFile[] localPhoto,
                                        final Consultant consultant) {
        CcResult result = serviceTemplate.execute(CcResult.class, new BlankServiceCallBack() {

            @Override
            public void check() {
                AssertUtil.state(ValidateUtil.isMobile(consultant.getMobile()), "请输入正确的手机号！");
                AssertUtil.notBlank(consultant.getName(), "账户名称不能为空");
                AssertUtil.state(consultant.getName().length() < CcConstrant.COMMON_128_LENGTH,
                    "名称不能超过128个字符");
                Consultant sessionConsultant = getConsultantInSession(request.getSession());
                AssertUtil.notNull(sessionConsultant, "当前用户不存在或者页面过期，请刷新或重新登录");
                AssertUtil.state(sessionConsultant.getId().equals(consultant.getId()),
                    "不是您的记录，非法请求");
                AssertUtil.notBlank(consultant.getDescription(), "个人简介不能为空");
                AssertUtil.state(
                    consultant.getDescription().length() < CcConstrant.COMMON_4096_LENGTH,
                    "个人简介不能太长");
            }

            @Override
            public CcResult executeService() {

                Consultant localConsultant = consultantDAO.findById(consultant.getId());
                String fileName = "";
                try {
                    for (MultipartFile myfile : localPhoto) {
                        if (!myfile.isEmpty()) {
                            LogUtil.info(
                                logger,
                                "文件长度: " + myfile.getSize() + "文件类型: " + myfile.getContentType()
                                        + "文件名称: " + myfile.getName() + "文件原名: "
                                        + myfile.getOriginalFilename());
                            String path = request.getSession().getServletContext().getRealPath("/")
                                          + "UPLOAD";
                            File parentFile = new File(path);
                            if (!parentFile.exists()) {
                                parentFile.mkdirs();
                            }
                            fileName = UUID.randomUUID().toString() + myfile.getOriginalFilename();
                            FileCopyUtils.copy(myfile.getBytes(), new File(path, fileName));
                        }
                    }
                    if (!StringUtil.isBlank(fileName)) {
                        localConsultant.setPhoto(fileName);
                    }
                    localConsultant.setGmtModified(new Date());
                    localConsultant.setName(consultant.getName());
                    localConsultant.setMobile(consultant.getMobile());
                    localConsultant.setDescription(consultant.getDescription());
                    consultantDAO.update(localConsultant);
                } catch (Exception e) {
                    LogUtil.error(logger, e, "文件上传失败");
                    throw new CcException("文件上传失败");
                }
                return new CcResult(localConsultant);
            }
        });
        if (result.isSuccess()) {
            request.getSession().setAttribute(CcConstrant.SESSION_CONSULTANT_OBJECT,
                consultantDAO.findById(consultant.getId()));//更新到session中
            return new ModelAndView("redirect:/consultant/personalInfo.htm");
        }
        modelMap.put("result", result);
        modelMap.put("consultant", consultant);
        return new ModelAndView("consultant/editPersonalInfo");
    }
}
