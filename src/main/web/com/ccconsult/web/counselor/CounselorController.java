/**
 * 
 */
package com.ccconsult.web.counselor;

import java.util.Date;

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
import com.ccconsult.base.PageList;
import com.ccconsult.base.PageQuery;
import com.ccconsult.base.enums.ConsultStepEnum;
import com.ccconsult.base.enums.FileTypeEnum;
import com.ccconsult.base.enums.UserRoleEnum;
import com.ccconsult.base.util.StringUtil;
import com.ccconsult.base.util.ValidateUtil;
import com.ccconsult.core.ConsultComponent;
import com.ccconsult.core.FileComponent;
import com.ccconsult.dao.CounselorDAO;
import com.ccconsult.dao.InnerMailDAO;
import com.ccconsult.pojo.Consultant;
import com.ccconsult.pojo.Counselor;
import com.ccconsult.web.BaseController;
import com.ccconsult.web.view.ConsultBase;
import com.ccconsult.web.view.CounselorVO;

/**
 * @author jingyu.dan
 * 
 */
@Controller
public class CounselorController extends BaseController {
    @Autowired
    private CounselorDAO     counselorDAO;
    @Autowired
    private ConsultComponent consultComponent;
    @Autowired
    private InnerMailDAO     innerMailDAO;
    @Autowired
    private FileComponent    fileComponent;

    /**
     * 公共个人信息介绍页面
     * 
     * @param request
     * @param counselorId
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/counselorInfo.htm", method = RequestMethod.GET)
    public ModelAndView counselorSelf(HttpServletRequest request, final PageQuery query,
                                      final String counselorId, final ModelMap modelMap) {
        ModelAndView view = new ModelAndView("content/counselorInfo");
        CcResult result = serviceTemplate.execute(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                CounselorVO counselorVO = counselorDAO.findById(NumberUtils.toInt(counselorId));
                PageList<ConsultBase> consultBases = consultComponent.queryPaged(2,
                    ConsultStepEnum.FIHSHED.getValue(), 0, counselorVO.getCounselor().getId(), 0,
                    query.getPageSize(), query.getPageNo());
                modelMap.put("counselorVO", counselorVO);

                modelMap.put("consultBases", consultBases);
                return new CcResult(true);
            }
        });
        modelMap.put("result", result);
        return view;
    }

    @RequestMapping(value = "/counselor/counselorSelf.htm", method = RequestMethod.GET)
    public ModelAndView counselorSelf(HttpServletRequest request, final PageQuery query,
                                      ModelMap modelMap) {
        ModelAndView view = new ModelAndView("counselor/consult/searchConsult");
        final CounselorVO counselorVO = getCounselorInSession(request.getSession());
        CcResult result = serviceTemplate.execute(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                PageList<ConsultBase> consultBases = consultComponent.queryPaged(0,
                    ConsultStepEnum.CREATE.getValue(), 0, counselorVO.getCounselor().getId(), 0,
                    query.getPageSize(), query.getPageNo());
                return new CcResult(consultBases);
            }
        });
        modelMap.put("step", 1);
        modelMap.put("result", result);
        return view;
    }

    @RequestMapping(value = "counselor/consult/searchConsult.htm", method = RequestMethod.GET)
    public ModelAndView searchConsult(final HttpServletRequest request, final PageQuery query,
                                      final Integer step, final Integer serviceId, ModelMap modelMap) {
        ModelAndView view = new ModelAndView("counselor/consult/searchConsult");
        final CounselorVO counselorVO = getCounselorInSession(request.getSession());
        CcResult result = serviceTemplate.execute(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                PageList<ConsultBase> consultBases = consultComponent.queryPaged(0,
                    step == null ? 0 : step, serviceId == null ? 0 : serviceId, counselorVO
                        .getCounselor().getId(), 0, query.getPageSize(), query.getPageNo());
                return new CcResult(consultBases);
            }
        });
        modelMap.put("serviceId", serviceId);
        modelMap.put("step", step);
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
        CcResult result = serviceTemplate.executeWithTx(CcResult.class, new BlankServiceCallBack() {
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
                AssertUtil.state(counselor.getName().length() < CcConstrant.COMMON_32_LENGTH,
                    "名称不能超过32个字符");
                AssertUtil.state(sessionCounselorVO.getCounselor().getId()
                    .equals(counselor.getId()), "非法修改，不是您当前的个人信息");
            }

            @Override
            public CcResult executeService() {
                CounselorVO localCounselorVO = counselorDAO.findById(counselor.getId());
                AssertUtil.notNull(localCounselorVO, "当前用户不存在或者页面过期，请刷新或重新登录");
                Counselor localCounselor = localCounselorVO.getCounselor();
                String fileName = "";
                String contextPath = request.getSession().getServletContext().getRealPath("/");
                for (MultipartFile myfile : localPhoto) {
                    if (myfile == null || myfile.isEmpty()) {
                        continue;
                    }
                    fileName = fileComponent.uploadFile(myfile, FileTypeEnum.USER_PHOTO,
                        contextPath, CcConstrant.FILE_2M_SIZE, "jpg|jpeg|png|gif");
                }
                if (!StringUtil.isBlank(fileName)) {
                    localCounselor.setPhoto(fileName);
                }
                localCounselor.setGmtModified(new Date());
                localCounselor.setDepartment(counselor.getDepartment());
                localCounselor.setDescription(counselor.getDescription());
                localCounselor.setName(counselor.getName());
                localCounselor.setCity(counselor.getCity());
                localCounselor.setMobile(counselor.getMobile());
                counselorDAO.update(localCounselor);
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
