/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.ccconsult.base.AbstractService;
import com.ccconsult.base.AssertUtil;
import com.ccconsult.base.BlankServiceCallBack;
import com.ccconsult.base.CcException;
import com.ccconsult.base.CcResult;
import com.ccconsult.core.MailSender;
import com.ccconsult.dao.ConsultantDAO;
import com.ccconsult.dao.CounselorDAO;
import com.ccconsult.dao.RegMailDAO;
import com.ccconsult.pojo.Consultant;
import com.ccconsult.pojo.Counselor;
import com.ccconsult.pojo.RegMail;
import com.ccconsult.view.CounselorVO;

/**
 * 
 * @author jingyu.dan
 * @version $Id: RegistServiceImpl.java, v 0.1 2014-5-29 下午9:28:25 jingyu.dan
 *          Exp $
 */
public class RegistServiceImpl extends AbstractService implements RegistService {

    @Autowired
    private RegMailDAO    regMailDAO;
    @Autowired
    private CounselorDAO  counselorDAO;
    @Autowired
    private MailSender    mailSender;
    @Autowired
    private ConsultantDAO consultantDAO;

    @Override
    public CcResult regMail(final RegMail regMail) {

        return serviceTemplate.execute(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                RegMail existRegMail = regMailDAO.findByMail(regMail.getMail());
                if (existRegMail == null) {
                    regMailDAO.save(regMail);
                    mailSender.sendMail(regMail);
                } else {
                    CounselorVO counselorVO = counselorDAO.findByEmail(regMail.getMail());
                    if (counselorVO != null) {
                        throw new CcException("您已经注册过该用户，请直接登录，如果忘记密码请点击忘记密码找回");
                    }
                    mailSender.sendMail(existRegMail);
                }
                return new CcResult(regMail);
            }
        });

    }

    /**
     * 注册成为一个面试官
     * 
     */

    @Override
    public CcResult regConsultant(final Consultant consultant) {
        return serviceTemplate.execute(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                AssertUtil.notNull(consultant, "非法的注册请求");
                AssertUtil.notBlank(consultant.getEmail(), "用户邮箱不能为空");
                AssertUtil.notBlank(consultant.getName(), "用户名称不能为空");
                AssertUtil.notBlank(consultant.getMobile(), "请输入手机号码");
                Consultant localJobseeker = consultantDAO.findByEmail(consultant.getEmail());
                if (localJobseeker != null) {
                    throw new CcException("该用户名称已经被注册！");
                }
                consultantDAO.save(consultant);
                return new CcResult(consultant);
            }
        });
    }

    /**
     * 注册成为一个面试官
     * 
     */
    @Override
    public CcResult regCounselor(final Counselor counselor, final int regMailId) {
        return serviceTemplate.execute(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                RegMail regMail = regMailDAO.findById(regMailId);
                AssertUtil.notNull(regMail, "非法的注册请求");
                AssertUtil.state(StringUtils.equals(regMail.getMail(), counselor.getEmail()),
                    "非法的账号，账号被篡改");
                CounselorVO innerInterviewerVO = counselorDAO.findByEmail(counselor.getEmail());
                if (innerInterviewerVO != null) {
                    throw new CcException("您已经注册过该用户，请直接登录，如果忘记密码请点击忘记密码找回");
                }
                counselorDAO.save(counselor);
                return new CcResult(counselor);
            }
        });
    }

    @Override
    public CcResult getRegMainInfo(final String token) {
        return serviceTemplate.execute(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                RegMail regMail = regMailDAO.findByToken(token);
                if (regMail == null) {
                    throw new CcException("无注册记录，请注册");
                }
                return new CcResult(regMail);
            }
        });
    }

}
