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
import com.ccconsult.base.enums.DataStateEnum;
import com.ccconsult.base.enums.MobileTokenEnum;
import com.ccconsult.base.enums.UserRoleEnum;
import com.ccconsult.dao.AccountDAO;
import com.ccconsult.dao.CompanyDAO;
import com.ccconsult.dao.CounselorDAO;
import com.ccconsult.dao.MobileTokenDAO;
import com.ccconsult.dao.RegMailDAO;
import com.ccconsult.dao.ServiceConfigDAO;
import com.ccconsult.pojo.Account;
import com.ccconsult.pojo.Company;
import com.ccconsult.pojo.Counselor;
import com.ccconsult.pojo.MobileToken;
import com.ccconsult.pojo.RegMail;
import com.ccconsult.pojo.ServiceConfig;
import com.ccconsult.web.view.CounselorVO;

/**
 * 
 * @author jingyudan
 * @version $Id: CounselorServiceImpl.java, v 0.1 2014-10-6 下午3:53:48 jingyudan Exp $
 */
public class CounselorServiceImpl extends AbstractService implements CounselorService {

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

    public CcResult registCounselor(final Counselor counselor, final int regMailId,
                                    final String token) {

        CcResult result = serviceTemplate.executeWithTx(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                RegMail regMail = regMailDAO.findById(regMailId);
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
                    MobileTokenEnum.REG_COUNSELOR.getValue(), counselor.getMobile(), -1);
                AssertUtil.state(mobileToken != null && mobileToken.getToken().equals(token),
                    "验证码信息不正确，无法注册");
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
        return result;
    }

    @Override
    public CcResult updateCounselor(Counselor counselor) {
        return null;
    }

    @Override
    public CcResult deleteCounselor(int id) {
        return null;
    }

    CounselorServiceImpl() {
        System.out.println("xxx");
    }

}
