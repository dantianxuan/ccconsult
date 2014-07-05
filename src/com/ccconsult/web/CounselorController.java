/**
 * 
 */
package com.ccconsult.web;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
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
import com.ccconsult.dao.CounselorDAO;
import com.ccconsult.dao.InnerMailDAO;
import com.ccconsult.dao.InterviewDAO;
import com.ccconsult.enums.ConsultStepEnum;
import com.ccconsult.enums.UserRoleEnum;
import com.ccconsult.pojo.Consultant;
import com.ccconsult.pojo.Counselor;
import com.ccconsult.util.LogUtil;
import com.ccconsult.util.StringUtil;
import com.ccconsult.util.ValidateUtil;
import com.ccconsult.view.CounselorVO;
import com.ccconsult.view.InterviewVO;

/**
 * @author jingyu.dan
 * 
 */
@Controller
public class CounselorController extends BaseController {

    /**日志 */
    private static final Logger logger = Logger.getLogger(CounselorController.class);

    @Autowired
    private CounselorDAO        counselorDAO;
    @Autowired
    private InterviewDAO        interviewDAO;
    @Autowired
    private InnerMailDAO        innerMailDAO;

    /**
     * 公共个人信息介绍页面
     * 
     * @param request
     * @param counselorId
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "counselorInfo.htm", method = RequestMethod.GET)
    public ModelAndView counselorSelf(HttpServletRequest request, final String counselorId,
                                      final ModelMap modelMap) {
        ModelAndView view = new ModelAndView("content/counselorInfo");
        CcResult result = serviceTemplate.execute(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                CounselorVO counselorVO = counselorDAO.findById(NumberUtils.toInt(counselorId));
                List<InterviewVO> interviews = interviewDAO.findUnderStepCounselor(counselorVO
                    .getCounselor().getId(), ConsultStepEnum.FIHSHED);
                modelMap.put("counselorVO", counselorVO);
                modelMap.put("interviews", interviews);
                return new CcResult(true);
            }
        });
        modelMap.put("result", result);
        return view;
    }

    @RequestMapping(value = "counselor/counselorSelf.htm", method = RequestMethod.GET)
    public ModelAndView counselorSelf(HttpServletRequest request, ModelMap modelMap) {
        ModelAndView view = new ModelAndView("counselor/counselorSelf");
        final CounselorVO counselorVO = getCounselorInSession(request.getSession());
        CcResult result = serviceTemplate.execute(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                List<InterviewVO> counselors = interviewDAO.findUnderStepCounselor(counselorVO
                    .getCounselor().getId(), ConsultStepEnum.FIHSHED);
                return new CcResult(counselors);
            }
        });
        modelMap.put("result", result);
        return view;
    }

    @RequestMapping(value = "counselor/personalInfo.htm", method = RequestMethod.GET)
    public ModelAndView counselorPage(HttpServletRequest request, ModelMap modelMap) {
        ModelAndView view = new ModelAndView("counselor/personalInfo");
        return view;
    }

    @RequestMapping(value = "counselor/innerMails.htm", method = RequestMethod.GET)
    public ModelAndView toMessages(HttpServletRequest request, ModelMap modelMap) {
        Consultant consultant = getConsultantInSession(request.getSession());
        if (consultant != null) {
            modelMap.put(
                "innerMails",
                innerMailDAO.findByByReceiver(consultant.getId(),
                    UserRoleEnum.CONSULTANT.getValue()));
        }
        ModelAndView view = new ModelAndView("counselor/innerMails");
        return view;
    }

    @RequestMapping(value = "counselor/editPersonalInfo.htm", method = RequestMethod.GET)
    public ModelAndView toInterview(HttpServletRequest request, ModelMap modelMap) {
        ModelAndView view = new ModelAndView("counselor/editPersonalInfo");
        return view;
    }

    /**
     * 公司邮箱链接注册
     * @return
     */
    @RequestMapping(value = "counselor/editPersonalInfo.htm", params = "action=save")
    public ModelAndView editCounselor(final HttpServletRequest request, final Counselor counselor,
                                      @RequestParam final MultipartFile[] localPhoto,
                                      ModelMap modelMap) {
        CcResult result = serviceTemplate.execute(CcResult.class, new BlankServiceCallBack() {
            /** 
             * @see com.ccconsult.base.ServiceCallBack#check()
             */
            @Override
            public void check() {
                CounselorVO sessionCounselorVO = getCounselorInSession(request.getSession());
                AssertUtil.state(ValidateUtil.isMobile(counselor.getMobile()), "请输入正确的手机号！");
                AssertUtil.notBlank(counselor.getDescription(), "个人简介不能为空");
                AssertUtil.state(
                    counselor.getDescription().length() < CcConstrant.COMMON_4096_LENGTH,
                    "个人简介不能太长");
                AssertUtil.notBlank(counselor.getDepartment(), "个人所在部门信息不能为空");
                AssertUtil.state(
                    counselor.getDepartment().length() < CcConstrant.COMMON_256_LENGTH,
                    "部门长度不能超过256个字符");
                AssertUtil.notBlank(counselor.getName(), "个人名称不能为空");
                AssertUtil.state(counselor.getName().length() < CcConstrant.COMMON_128_LENGTH,
                    "名称不能超过128个字符");
                AssertUtil.state(sessionCounselorVO.getCounselor().getId()
                    .equals(counselor.getId()), "非法修改，不是您当前的个人信息");
            }

            @Override
            public CcResult executeService() {
                CounselorVO localCounselorVO = counselorDAO.findById(counselor.getId());
                AssertUtil.notNull(localCounselorVO, "当前用户不存在或者页面过期，请刷新或重新登录");
                Counselor localCounselor = localCounselorVO.getCounselor();
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
                        localCounselor.setPhoto(fileName);
                    }
                    localCounselor.setGmtModified(new Date());
                    localCounselor.setDepartment(counselor.getDepartment());
                    localCounselor.setDescription(counselor.getDescription());
                    localCounselor.setMobile(counselor.getMobile());
                    counselorDAO.update(localCounselor);
                } catch (Exception e) {
                    LogUtil.error(logger, e, "文件上传失败");
                    throw new CcException("文件上传失败");
                }
                return new CcResult(localCounselor);
            }
        });
        if (result.isSuccess()) {
            request.getSession().setAttribute(CcConstrant.SESSION_COUNSELOR_OBJECT,
                counselorDAO.findById(counselor.getId()));//更新到session中
            return new ModelAndView("counselor/personalInfo");
        }
        modelMap.put("result", result);
        modelMap.put("counselor", counselor);
        return new ModelAndView("counselor/editPersonalInfo");
    }
}
