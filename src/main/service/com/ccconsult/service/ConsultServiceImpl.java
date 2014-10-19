/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.ccconsult.base.AbstractService;
import com.ccconsult.base.AssertUtil;
import com.ccconsult.base.BlankServiceCallBack;
import com.ccconsult.base.CcResult;
import com.ccconsult.base.enums.ConsultStepEnum;
import com.ccconsult.base.enums.NotifySenderEnum;
import com.ccconsult.base.util.DateUtil;
import com.ccconsult.core.notify.NotifySender;
import com.ccconsult.core.order.OrderComponent;
import com.ccconsult.dao.ConsultDAO;
import com.ccconsult.pojo.Consult;

/**
 * 
 * @author jingyudan
 * @version $Id: ConsultServiceImpl.java, v 0.1 2014年10月19日 下午2:41:51 jingyudan Exp $
 */
public class ConsultServiceImpl extends AbstractService implements ConsultService {

    @Autowired
    private ConsultDAO     consultDAO;
    @Autowired
    private OrderComponent orderComponent;
    @Autowired
    private NotifySender   notifySender;

    @Override
    public CcResult finishConsultOrder(final int consultId) {

        CcResult result = serviceTemplate.executeWithTx(CcResult.class, new BlankServiceCallBack() {

            @Override
            public CcResult executeService() {
                Consult consult = consultDAO.findById(consultId);
                AssertUtil.notNull(consult, "咨询记录不存在");
                AssertUtil.state(consult.getStep() == ConsultStepEnum.ON_CONSULT.getValue(),
                    "当前状态无需支付！");
                //扣款。进行支付
                orderComponent.finishConsultOrder(consultId);
                return new CcResult(true);

            }
        });
        return result;

    }

    @Override
    public CcResult payForConsult(final int consultId) {

        CcResult result = serviceTemplate.executeWithTx(CcResult.class, new BlankServiceCallBack() {

            @Override
            public CcResult executeService() {
                Consult consult = consultDAO.findById(consultId);
                AssertUtil.notNull(consult, "咨询记录不存在");
                AssertUtil.state(consult.getStep() == ConsultStepEnum.CREATE.getValue(),
                    "当前状态无需支付！");
                //校验，有效期之前才能进行支付
                AssertUtil.state(
                    DateUtil.getDiffMinutes(consult.getGmtEffectBegin(), new Date()) < 10,
                    "当前咨询已经超过支付时间，请重新发起咨询");

                //扣款。进行支付
                orderComponent.payConsultOrder(consultId);

                //对咨询师发送消息，通知已经完成
                if (consult.getPrice() > 0) {
                    notifySender.notify(NotifySenderEnum.CONSULT_EFFIECT_NOTIFY.getCode(), consult);
                }
                return new CcResult(true);

            }
        });
        return result;
    }

    @Override
    public CcResult rejectConsult(final int consultId) {

        CcResult result = serviceTemplate.executeWithTx(CcResult.class, new BlankServiceCallBack() {

            @Override
            public CcResult executeService() {
                Consult consult = consultDAO.findById(consultId);
                AssertUtil.notNull(consult, "咨询记录不存在");
                AssertUtil.state(consult.getStep() == ConsultStepEnum.ON_CONSULT.getValue(),
                    "当前状态无法进行拒绝操作！");
                //扣款。进行支付
                orderComponent.rejectConsultOrder(consultId);

                //对咨询师发送消息，通知已经完成
                if (consult.getPrice() > 0) {
                    notifySender.notify(NotifySenderEnum.CONSULT_REJECT_NOTIFY.getCode(), consult);
                }
                return new CcResult(true);

            }
        });
        return result;
    }

}
