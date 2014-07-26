/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.ccconsult.base.AbstractService;
import com.ccconsult.base.AssertUtil;
import com.ccconsult.base.BlankServiceCallBack;
import com.ccconsult.base.CcException;
import com.ccconsult.base.CcResult;
import com.ccconsult.dao.CounselorDAO;
import com.ccconsult.dao.RegMailDAO;
import com.ccconsult.notify.NotifySender;
import com.ccconsult.pojo.RegMail;
import com.ccconsult.util.ValidateUtil;
import com.ccconsult.view.CounselorVO;

/**
 * 
 * @author jingyu.dan
 * @version $Id: RegistServiceImpl.java, v 0.1 2014-5-29 下午9:28:25 jingyu.dan
 *          Exp $
 */
public class RegistServiceImpl extends AbstractService implements RegistService {

    @Autowired
    private RegMailDAO   regMailDAO;
    @Autowired
    private CounselorDAO counselorDAO;
    @Autowired
    private NotifySender notifySender;

    @Override
    public CcResult regMail(final RegMail regMail) {

        return serviceTemplate.executeWithTx(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                AssertUtil.state(ValidateUtil.isEmail(regMail.getMail()), "请输入合法的邮箱账户格式");
                RegMail existRegMail = regMailDAO.findByMail(regMail.getMail());
                if (existRegMail == null) {
                    regMailDAO.save(regMail);
                    notifySender.notify(NotifySender.REG_MAIL, regMail);
                } else {
                    CounselorVO counselorVO = counselorDAO.findByEmail(regMail.getMail());
                    if (counselorVO != null) {
                        throw new CcException("您已经注册过该用户，请直接登录，如果忘记密码请点击忘记密码找回");
                    }
                    notifySender.notify(NotifySender.REG_MAIL, existRegMail);
                }
                return new CcResult(regMail);
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
