/**
 * 
 */
package com.ccconsult.web.counselor;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ccconsult.base.AssertUtil;
import com.ccconsult.base.BlankServiceCallBack;
import com.ccconsult.base.CcConstrant;
import com.ccconsult.base.CcException;
import com.ccconsult.base.CcResult;
import com.ccconsult.dao.AccountDAO;
import com.ccconsult.dao.CompanyDAO;
import com.ccconsult.dao.CounselorDAO;
import com.ccconsult.dao.MobileTokenDAO;
import com.ccconsult.dao.RegMailDAO;
import com.ccconsult.dao.ServiceConfigDAO;
import com.ccconsult.enums.DataStateEnum;
import com.ccconsult.enums.MobileTokenEnum;
import com.ccconsult.enums.UserRoleEnum;
import com.ccconsult.pojo.Account;
import com.ccconsult.pojo.Company;
import com.ccconsult.pojo.Counselor;
import com.ccconsult.pojo.MobileToken;
import com.ccconsult.pojo.RegMail;
import com.ccconsult.pojo.ServiceConfig;
import com.ccconsult.service.RegistService;
import com.ccconsult.view.CounselorVO;
import com.ccconsult.web.BaseController;

/**
 * @author jingyu.dan
 * 
 */
@Controller
public class RegistCounselorController extends BaseController {
    @Autowired
    private RegistService    registService;
    @Autowired
    private RegMailDAO       regMailDAO;
    @Autowired
    private ServiceConfigDAO serviceConfigDAO;
    @Autowired
    private CounselorDAO     counselorDAO;
    @Autowired
    private CompanyDAO       companyDAO;
    @Autowired
    private AccountDAO       accountDAO;
    @Autowired
    private MobileTokenDAO   mobileTokenDAO;

    /**
     * 注册面试官init页面
     * 
     * @return
     */
    @RequestMapping(value = "regist/regCounselorInit.htm", method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest httpservletrequest, ModelMap modelMap) {
        modelMap.put("companys", companyDAO.findAll());
        ModelAndView view = new ModelAndView("regist/regCounselorInit");
        return view;
    }

    /**
     * 使用公司邮箱进行注册
     * 
     * @return
     */
    @RequestMapping(value = "regist/regCounselorMail.htm", method = RequestMethod.GET)
    public ModelAndView registMail(HttpServletRequest httpservletrequest, String email,
                                   String subPrefix, ModelMap modelMap) {
        ModelAndView view = new ModelAndView("regist/regCounselorInit");
        RegMail regMail = new RegMail();
        regMail.setMail(email + subPrefix);
        regMail.setGmtCreate(new Date());
        regMail.setToken(UUID.randomUUID().toString());
        CcResult result = registService.regMail(regMail);
        modelMap.put("companys", companyDAO.findAll());
        modelMap.put("result", result);
        return view;
    }

    /**
     * 公司邮箱链接注册
     * 
     * @return
     */
    @RequestMapping(value = "regist/regCounselor.htm", method = RequestMethod.GET)
    public ModelAndView initRegInterviewer(String token, ModelMap modelMap) {
        RegMail regMail = regMailDAO.findByToken(token);
        if (regMail == null) {
            modelMap.put("message", "记录不存在，请重新注册");
            return new ModelAndView("error");
        }
        modelMap.put("regMail", regMail);
        return new ModelAndView("regist/regCounselor");
    }

    /**
     * 公司邮箱链接注册
     * @return
     */
    @RequestMapping(value = "regist/regCounselor.htm", params = "action=regist")
    public ModelAndView submitRegCounselor(final HttpServletRequest request,
                                           final Counselor counselor, final String repasswd,
                                           final String token, final Integer regMailId,
                                           final ModelMap modelMap) {

        CcResult result = serviceTemplate.executeWithTx(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                RegMail regMail = regMailDAO.findById(regMailId);
                modelMap.put("regMail", regMail);
                Company company = companyDAO.findByMailSuffix(CcConstrant.ALT_SEPARATOR
                                                              + regMail.getMail().split(
                                                                  CcConstrant.ALT_SEPARATOR)[1]);
                AssertUtil.notNull(company, "公司信息不存在，不能注册");
                AssertUtil.notNull(regMail, "非法的注册请求");
                AssertUtil.state(StringUtils.equals(regMail.getMail(), counselor.getEmail()),
                    "非法的账号，账号被篡改");
                CounselorVO innerInterviewerVO = counselorDAO.findByEmail(counselor.getEmail());
                if (innerInterviewerVO != null) {
                    throw new CcException("您已经注册过该用户，请直接登录，如果忘记密码请点击忘记密码找回");
                }
                MobileToken mobileToken = mobileTokenDAO.getByTypeAndMobile(
                    MobileTokenEnum.REG_COUNSELOR.getValue(), counselor.getMobile());
                AssertUtil.state(mobileToken != null && mobileToken.getToken().equals(token),
                    "验证码信息不正确，无法注册");
                AssertUtil.state(StringUtils.equals(counselor.getPasswd(), repasswd), "重复密码输入不一致");
                AssertUtil.state(counselor.getName() != null, "用户昵称不能为空");
                AssertUtil.state(counselor.getName().length() <= CcConstrant.COMMON_32_LENGTH,
                    "用户名称长度不能超过32个字符");
                counselor.setGmtCreate(new Date());
                counselor.setGmtModified(new Date());
                counselor.setCompanyId(company.getId());
                counselor.setLastLogin(new Date());
                counselor.setLevelId(1);
                counselorDAO.save(counselor);//注册一个用户
                company.setCounselorCount(company.getCounselorCount() + 1);
                companyDAO.update(company);
                Account account = new Account();
                account.setCurrentMoney(new Double(0));
                account.setFreezingMoney(0);
                account.setInAllMoney(0);
                account.setRelRoleId(counselor.getId());
                account.setRelRoleType(UserRoleEnum.COUNSELOR.getValue());
                account.setTransAllMoney(0);
                accountDAO.save(account);//初始化一个账户
                ServiceConfig serviceConfig = new ServiceConfig();
                serviceConfig.setCounselorId(counselor.getId());
                serviceConfig.setGmtCreate(new Date());
                serviceConfig.setState(DataStateEnum.NORMAL.getValue());
                serviceConfig.setServiceId(1);
                serviceConfigDAO.save(serviceConfig);//初始化一个最基本的站内咨询服务
                return new CcResult(counselor);
            }
        });
        if (result.isSuccess()) {
            CounselorVO counselorVO = counselorDAO.findById(((Counselor) result.getObject())
                .getId());
            request.getSession().setAttribute(CcConstrant.SESSION_COUNSELOR_OBJECT, counselorVO);
            return new ModelAndView("redirect:/counselor/counselorSelf.htm");
        }
        modelMap.put("result", result);
        return new ModelAndView("regist/regCounselor");
    }

}
