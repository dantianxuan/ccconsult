/**
 * 
 */
package com.ccconsult.web;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
import com.ccconsult.base.CcException;
import com.ccconsult.base.CcResult;
import com.ccconsult.core.FileComponent;
import com.ccconsult.dao.AccountDAO;
import com.ccconsult.dao.CompanyDAO;
import com.ccconsult.dao.ConsultantDAO;
import com.ccconsult.dao.CounselorDAO;
import com.ccconsult.dao.RegMailDAO;
import com.ccconsult.dao.ServiceConfigDAO;
import com.ccconsult.enums.DataStateEnum;
import com.ccconsult.enums.NotifySenderEnum;
import com.ccconsult.enums.UserRoleEnum;
import com.ccconsult.notify.NotifySender;
import com.ccconsult.pojo.Account;
import com.ccconsult.pojo.Company;
import com.ccconsult.pojo.Consultant;
import com.ccconsult.pojo.Counselor;
import com.ccconsult.pojo.RegMail;
import com.ccconsult.pojo.ServiceConfig;
import com.ccconsult.service.RegistService;
import com.ccconsult.util.StringUtil;
import com.ccconsult.util.ValidateUtil;
import com.ccconsult.view.CounselorVO;

/**
 * @author jingyu.dan
 * 
 */
@Controller
public class RegistController extends BaseController {
    @Autowired
    private RegistService    registService;
    @Autowired
    private NotifySender     notifySender;
    @Autowired
    private RegMailDAO       regMailDAO;
    @Autowired
    private ServiceConfigDAO serviceConfigDAO;
    @Autowired
    private CounselorDAO     counselorDAO;
    @Autowired
    private ConsultantDAO    consultantDAO;
    @Autowired
    private CompanyDAO       companyDAO;
    @Autowired
    private AccountDAO       accountDAO;
    @Autowired
    private FileComponent    fileComponent;

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
                                           final Integer regMailId, final ModelMap modelMap) {

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
                AssertUtil.state(StringUtils.equals(counselor.getPasswd(), repasswd), "重复密码输入不一致");
                AssertUtil.state(counselor.getName() != null, "用户昵称不能为空");
                AssertUtil.state(counselor.getName().length() <= CcConstrant.COMMON_32_LENGTH,
                    "用户名称长度不能超过32个字符");
                counselor.setGmtCreate(new Date());
                counselor.setGmtModified(new Date());
                counselor.setCompanyId(company.getId());
                counselorDAO.save(counselor);//注册一个用户
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
                serviceConfig.setPrice(0);
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

    /**
     * @return
     */
    @RequestMapping(value = "regist/regConsultant.htm", method = RequestMethod.GET)
    public ModelAndView submitRegJobseeker(HttpServletRequest request, ModelMap modelMap) {
        return new ModelAndView("regist/regConsultant");
    }

    /**
     * 公司邮箱链接注册
     * 
     * @return
     * @throws IOException 
     * @throws Exception
     */
    @RequestMapping(value = "regist/regConsultant.htm", params = "action=regist")
    public ModelAndView submitRegConsultant(HttpServletRequest request,
                                            final Consultant consultant, final String repasswd,
                                            ModelMap modelMap) {

        CcResult result = serviceTemplate.executeWithTx(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                AssertUtil.notNull(consultant, "非法的注册请求");
                AssertUtil.notBlank(consultant.getEmail(), "用户邮箱不能为空");
                AssertUtil.state(consultant.getEmail().length() < CcConstrant.COMMON_256_LENGTH,
                    "注册邮箱不能超过256个字符！");
                AssertUtil.state(ValidateUtil.isEmail(consultant.getEmail()), "请输入合法的邮箱账户格式");
                AssertUtil.notBlank(consultant.getName(), "用户名称不能为空");
                AssertUtil.state(consultant.getName().length() < CcConstrant.COMMON_128_LENGTH,
                    "注册用户名称不能超过128个字符！");
                AssertUtil.state(ValidateUtil.isMobile(consultant.getMobile()), "请输入合法的手机号码");
                AssertUtil.state(consultant.getPasswd() != null
                                 && consultant.getPasswd().length() > 6
                                 && consultant.getPasswd().length() < 20, "密码长度必须在6到20个字符之间");
                Consultant localJobseeker = consultantDAO.findByEmail(consultant.getEmail());
                if (localJobseeker != null) {
                    throw new CcException("该用户名称已经被注册！");
                }
                AssertUtil.state(StringUtils.equals(consultant.getPasswd(), repasswd), "重复密码输入不一致");
                consultant.setGmtCreate(new Date());
                consultant.setGmtModified(new Date());
                consultantDAO.save(consultant);
                Account account = new Account();
                account.setCurrentMoney(new Double(0));
                account.setFreezingMoney(0);
                account.setInAllMoney(0);
                account.setRelRoleId(consultant.getId());
                account.setRelRoleType(UserRoleEnum.CONSULTANT.getValue());
                account.setTransAllMoney(0);
                accountDAO.save(account);//初始化一个账户                
                return new CcResult(consultant);
            }
        });
        if (result.isSuccess()) {
            request.getSession().setAttribute(CcConstrant.SESSION_CONSULTANT_OBJECT,
                result.getObject());
            return new ModelAndView("redirect:/consultant/consultantSelf.htm?consultantId="
                                    + consultant.getId());
        }

        modelMap.put("result", result);
        modelMap.put("consultant", consultant);
        return new ModelAndView("regist/regConsultant");
    }

    @RequestMapping(value = "/findPasswdInit.htm", method = RequestMethod.GET)
    public ModelAndView findPasswdPage(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("content/findPasswd");
        return view;
    }

    @RequestMapping(value = "/findPasswd.htm", method = RequestMethod.GET)
    public ModelAndView findPasswd(HttpServletRequest request, final String accountMail,
                                   ModelMap modelMap) {
        ModelAndView view = new ModelAndView("content/findPasswd");
        CcResult result = serviceTemplate.execute(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                AssertUtil.state(ValidateUtil.isEmail(accountMail), "账号格式不合法，请输入合法的邮箱账号");
                CounselorVO counselorVO = counselorDAO.findByEmail(accountMail);
                Consultant consultant = consultantDAO.findByEmail(accountMail);
                AssertUtil.state(counselorVO != null || consultant != null, "不存在的合法账号");
                if (counselorVO != null) {
                    notifySender.notify(NotifySenderEnum.FINDPASSWD_NOTIFY.getCode(), counselorVO);
                }
                if (consultant != null) {
                    notifySender.notify(NotifySenderEnum.FINDPASSWD_NOTIFY.getCode(), consultant);
                }
                return new CcResult(true);
            }
        });
        modelMap.put("accountMail", accountMail);
        modelMap.put("result", result);
        return view;
    }

    /**
     * 注册一个公司信息
     * 
     * @return
     */
    @RequestMapping(value = "regist/regCompany.htm", method = RequestMethod.GET)
    public ModelAndView regCompany(HttpServletRequest httpservletrequest, ModelMap modelMap) {
        ModelAndView view = new ModelAndView("regist/regCompany");
        return view;
    }

    /**
     * 注册公司信息
     * 
     * @param request
     * @param company
     * @param localPhoto
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/regist/regCompanyInfo.htm", method = RequestMethod.POST)
    public @ResponseBody
    ModelMap regCompanyJson(final HttpServletRequest request, final Company company,
                            ModelMap modelMap) {
        modelMap.clear();
        CcResult result = serviceTemplate.executeWithTx(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                AssertUtil.state(ValidateUtil.isMailSubfix(company.getMailSuffix()), "请填写合法的公司邮箱后缀");
                AssertUtil.state(company.getName() != null
                                 && company.getName().length() < CcConstrant.COMMON_128_LENGTH,
                    "请输入合法的公司名称，名称长度不能超过128个字符");
                AssertUtil.state(
                    company.getDescription() != null
                            && company.getDescription().length() < CcConstrant.COMMON_512_LENGTH,
                    "请输入合法的公司描述，描述长度不能超过512个字符");
                AssertUtil.state(ValidateUtil.isMobile(company.getRegMobile()),
                    "请留下您的手机号码，我们会联系您进行审核");
                company.setState(DataStateEnum.DISABLE.getValue());
                company.setGmtCreate(new Date());
                company.setGmtModified(new Date());
                companyDAO.save(company);
                return new CcResult(company);
            }
        });
        modelMap.put("result", result);
        return modelMap;
    }
}
