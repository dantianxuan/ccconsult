/**
 * 
 */
package com.ccconsult.web.consultant;

import java.io.IOException;
import java.util.Date;

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
import com.ccconsult.dao.ConsultantDAO;
import com.ccconsult.dao.MobileTokenDAO;
import com.ccconsult.enums.MobileTokenEnum;
import com.ccconsult.enums.UserRoleEnum;
import com.ccconsult.pojo.Account;
import com.ccconsult.pojo.Consultant;
import com.ccconsult.pojo.MobileToken;
import com.ccconsult.util.ValidateUtil;
import com.ccconsult.web.BaseController;

/**
 * @author jingyu.dan
 * 
 */
@Controller
public class RegistConsultantController extends BaseController {
    @Autowired
    private ConsultantDAO  consultantDAO;
    @Autowired
    private AccountDAO     accountDAO;
    @Autowired
    private MobileTokenDAO mobileTokenDAO;

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
                                            final String token, ModelMap modelMap) {

        CcResult result = serviceTemplate.executeWithTx(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                AssertUtil.notNull(consultant, "非法的注册请求");
                AssertUtil.notBlank(consultant.getEmail(), "用户邮箱不能为空");
                AssertUtil.state(consultant.getEmail().length() < CcConstrant.COMMON_256_LENGTH,
                    "注册邮箱不能超过256个字符！");
                AssertUtil.state(ValidateUtil.isEmail(consultant.getEmail()), "请输入合法的邮箱账户格式");
                AssertUtil.notBlank(token, "验证码信息不能为空");
                AssertUtil.notBlank(consultant.getName(), "用户名称不能为空");
                AssertUtil.state(consultant.getName().length() < CcConstrant.COMMON_128_LENGTH,
                    "注册用户名称不能超过128个字符！");

                MobileToken mobileToken = mobileTokenDAO.getByTypeAndMobile(
                    MobileTokenEnum.REG_CONSULTANT.getValue(), consultant.getMobile());
                AssertUtil.state(mobileToken != null && mobileToken.getToken().equals(token),
                    "验证码信息不正确，无法注册");

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
        modelMap.put("token", token);
        modelMap.put("result", result);
        modelMap.put("consultant", consultant);
        return new ModelAndView("regist/regConsultant");
    }

}
