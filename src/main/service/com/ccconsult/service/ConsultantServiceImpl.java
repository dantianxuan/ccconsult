/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.service;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.ccconsult.base.AbstractService;
import com.ccconsult.base.AssertUtil;
import com.ccconsult.base.BlankServiceCallBack;
import com.ccconsult.base.CcConstrant;
import com.ccconsult.base.CcException;
import com.ccconsult.base.CcResult;
import com.ccconsult.base.enums.MobileTokenEnum;
import com.ccconsult.base.enums.UserRoleEnum;
import com.ccconsult.base.util.ValidateUtil;
import com.ccconsult.dao.AccountDAO;
import com.ccconsult.dao.ConsultantDAO;
import com.ccconsult.dao.MobileTokenDAO;
import com.ccconsult.pojo.Account;
import com.ccconsult.pojo.Consultant;
import com.ccconsult.pojo.MobileToken;

/**
 * 
 * @author jingyudan
 * @version $Id: CounselorServiceImpl.java, v 0.1 2014-10-6 下午3:53:48 jingyudan Exp $
 */
public class ConsultantServiceImpl extends AbstractService implements ConsultantService {

    @Autowired
    private ConsultantDAO  consultantDAO;
    @Autowired
    private AccountDAO     accountDAO;
    @Autowired
    private MobileTokenDAO mobileTokenDAO;

    @Override
    public CcResult registConsultant(final Consultant consultant, final String repasswd,
                                     final String token) {

        CcResult result = serviceTemplate.executeWithTx(CcResult.class, new BlankServiceCallBack() {

            public void check() {
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
                    MobileTokenEnum.REG_CONSULTANT.getValue(), consultant.getMobile(), -1);
                AssertUtil.state(mobileToken != null && mobileToken.getToken().equals(token),
                    "验证码信息不正确，无法注册");

                AssertUtil.state(ValidateUtil.isMobile(consultant.getMobile()), "请输入合法的手机号码");
                AssertUtil.state(consultant.getPasswd() != null
                                 && consultant.getPasswd().length() >= 6
                                 && consultant.getPasswd().length() <= 20, "密码长度必须在6到20个字符之间");
                Consultant localJobseeker = consultantDAO.findByEmail(consultant.getEmail());
                if (localJobseeker != null) {
                    throw new CcException("该邮箱已经被注册！");
                }
                AssertUtil.state(StringUtils.equals(consultant.getPasswd(), repasswd), "重复密码输入不一致");
            }

            @Override
            public CcResult executeService() {

                //保存用户对象
                consultant.setGmtCreate(new Date());
                consultant.setGmtModified(new Date());
                consultantDAO.save(consultant);

                //初始化用户账户数据
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
        return result;
    }

}
