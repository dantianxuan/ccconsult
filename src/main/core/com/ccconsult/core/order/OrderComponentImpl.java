/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.core.order;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ccconsult.base.AssertUtil;
import com.ccconsult.base.CcConstrant;
import com.ccconsult.base.enums.ChargeTypeEnum;
import com.ccconsult.base.enums.ConsultStepEnum;
import com.ccconsult.base.enums.PayStateEnum;
import com.ccconsult.base.enums.TransTypeEnum;
import com.ccconsult.base.enums.UserRoleEnum;
import com.ccconsult.dao.AccountDAO;
import com.ccconsult.dao.ConsultDAO;
import com.ccconsult.dao.TransRecordDAO;
import com.ccconsult.pojo.Account;
import com.ccconsult.pojo.Consult;
import com.ccconsult.pojo.TransRecord;

/**
 * 订单操作组件
 * 
 * @author jingyudan
 * @version $Id: OrderComponentImpl.java, v 0.1 2014-9-1 上午8:02:28 jingyudan Exp $
 */
public class OrderComponentImpl implements OrderComponent {

    @Autowired
    private ConsultDAO     consultDAO;
    @Autowired
    private AccountDAO     accountDAO;
    @Autowired
    private TransRecordDAO transRecordDAO;

    /**
     * 支付订单 
     * 
     * @see com.ccconsult.core.order.OrderComponent#payConsunt(int)
     */
    public void finishConsultOrder(int consultId) {
        Consult consult = consultDAO.findByIdForUpdate(consultId);//加锁读取记录
        AssertUtil.notNull(consult, "咨询记录不存在");
        AssertUtil.state(consult.getStep().equals(ConsultStepEnum.ON_CONSULT.getValue()),
            "当前状态无需确认，请刷新页面");
        //加载当前的支付记录
        consult.setStep(ConsultStepEnum.FIHSHED.getValue());
        consultDAO.update(consult);
        if (consult.getPrice() == 0) {
            return;
        }//如果没有金额，无需转账即可。

        Account consultantAccount = accountDAO.queryByRoleForUpdate(consult.getConsultantId(),
            UserRoleEnum.CONSULTANT.getValue());
        AssertUtil.notNull(consultantAccount, "咨询者账户不存在");
        List<TransRecord> transRecords = transRecordDAO.queryByConsultIdAndType(consultId,
            TransTypeEnum.PAY_CONSULT_DEPOSIT.getValue());
        AssertUtil.state(transRecords != null && transRecords.size() == 1
                         && transRecords.get(0).getMoney() == consult.getPrice(), "异常记录，请联系客服处理");

        //添加转账记录
        double paltFormPrice = PaltformServiceMoneyCalculator.calculator(consult.getPrice());
        TransRecord counselorMoneyRecord = new TransRecord();
        counselorMoneyRecord.setConsultId(consultId);
        counselorMoneyRecord.setDetail("咨询被确认，获得咨询费用，咨询ID:" + String.valueOf(consultId));
        counselorMoneyRecord.setGmtCreate(new Date());
        counselorMoneyRecord.setMoney(consult.getPrice() - paltFormPrice);
        counselorMoneyRecord.setRelRoleId(consult.getCounselorId());
        counselorMoneyRecord.setChargeType(ChargeTypeEnum.CHARGE_IN.getValue());
        counselorMoneyRecord.setRelRoleType(UserRoleEnum.COUNSELOR.getValue());
        counselorMoneyRecord.setTransType(TransTypeEnum.PAY_COUNSELOR_CONSULT_MONEY.getValue());
        transRecordDAO.save(counselorMoneyRecord);
        Account counselorAccount = accountDAO.queryByRoleForUpdate(consult.getCounselorId(),
            UserRoleEnum.COUNSELOR.getValue());
        counselorAccount.setCurrentMoney(counselorAccount.getCurrentMoney()
                                         + counselorMoneyRecord.getMoney());
        counselorAccount.setInAllMoney(counselorAccount.getInAllMoney()
                                       + counselorMoneyRecord.getMoney());
        accountDAO.update(counselorAccount);

        //平台收费
        if (paltFormPrice != 0) {
            TransRecord paltformRecord = new TransRecord();
            paltformRecord.setConsultId(consultId);
            paltformRecord.setGmtCreate(new Date());
            paltformRecord.setMoney(paltFormPrice);
            counselorMoneyRecord.setDetail("平台服务费，咨询ID:" + consultId);
            counselorMoneyRecord.setChargeType(ChargeTypeEnum.CHARGE_IN.getValue());
            paltformRecord.setRelRoleId(CcConstrant.ADMINISTOR_ROLE_ID);
            paltformRecord.setRelRoleType(UserRoleEnum.ADMINISTRATOR.getValue());
            paltformRecord.setTransType(TransTypeEnum.PAY_PALTFORM_SERVICE_MONEY.getValue());
            Account adminAccount = accountDAO.queryByRoleForUpdate(consult.getCounselorId(),
                UserRoleEnum.COUNSELOR.getValue());
            adminAccount.setCurrentMoney(adminAccount.getCurrentMoney()
                                         + counselorMoneyRecord.getMoney());
            adminAccount.setInAllMoney(adminAccount.getInAllMoney()
                                       + counselorMoneyRecord.getMoney());
            accountDAO.update(adminAccount);
        }

        //咨询者，账户冻结解冻
        consultantAccount.setCurrentMoney(consultantAccount.getFreezingMoney()
                                          - counselorMoneyRecord.getMoney());
        accountDAO.update(consultantAccount);

    }

    /**
     * 支付账单，支付后进入担保账户，扣款
     * 
     * @param consultId
     */
    public void payConsultOrder(int consultId) {
        Consult consult = consultDAO.findByIdForUpdate(consultId);//加锁读取记录
        AssertUtil.notNull(consult, "咨询记录不存在");
        //加载当前的支付记录
        consult.setPayTag(PayStateEnum.PAY_SUCCESS.getValue());
        consult.setStep(ConsultStepEnum.ON_CONSULT.getValue());
        consultDAO.update(consult);
        if (consult.getPrice() == 0) {
            return;
        }//如果没有金额，无需转账即可。

        //咨询师账户更新
        Account consultantAccount = accountDAO.queryByRoleForUpdate(consult.getConsultantId(),
            UserRoleEnum.CONSULTANT.getValue());
        AssertUtil.notNull(consultantAccount, "咨询者账户不存在");
        AssertUtil
            .state(consultantAccount.getCurrentMoney() > consult.getPrice(), "对不起，当前金额不足，请充值");
        //添加转账记录（转出一条+平台收到转账一条）
        double current = consultantAccount.getCurrentMoney() - consult.getPrice();
        consultantAccount.setCurrentMoney(current);
        consultantAccount.setFreezingMoney(consultantAccount.getFreezingMoney()
                                           + consult.getPrice());
        accountDAO.update(consultantAccount);

        TransRecord guaranteeRecord = new TransRecord();
        guaranteeRecord.setConsultId(consult.getId());
        guaranteeRecord.setDetail("担保支付咨询费用，咨询ID:" + String.valueOf(consult.getId()));
        guaranteeRecord.setGmtCreate(new Date());
        guaranteeRecord.setMoney(consult.getPrice());
        guaranteeRecord.setRelRoleId(CcConstrant.ADMINISTOR_ROLE_ID);
        guaranteeRecord.setRelRoleType(UserRoleEnum.ADMINISTRATOR.getValue());
        guaranteeRecord.setChargeType(ChargeTypeEnum.CHARGE_IN.getValue());
        guaranteeRecord.setTransType(TransTypeEnum.FREEZE_CONSULT_DEPOSIT.getValue());
        transRecordDAO.save(guaranteeRecord);

        TransRecord transRecord = new TransRecord();
        transRecord.setConsultId(consult.getId());
        transRecord.setDetail("担保支付咨询费用，咨询ID:" + String.valueOf(consult.getId()));
        transRecord.setGmtCreate(new Date());
        transRecord.setMoney(consult.getPrice());
        transRecord.setRelRoleId(consult.getConsultantId());
        transRecord.setChargeType(ChargeTypeEnum.CHARGE_OUT.getValue());
        transRecord.setRelRoleType(UserRoleEnum.CONSULTANT.getValue());
        transRecord.setTransType(TransTypeEnum.PAY_CONSULT_DEPOSIT.getValue());
        transRecordDAO.save(transRecord);

    }

    public void rejectConsultOrder(int consultId) {
        Consult consult = consultDAO.findByIdForUpdate(consultId);//加锁读取记录
        AssertUtil.notNull(consult, "咨询记录不存在");
        consult.setStep(ConsultStepEnum.REJECT.getValue());
        //加载当前的支付记录
        if (consult.getPrice() == 0) {
            return;
        }//如果没有金额，无需转账即可。
         //咨询者账户，回款
        Account consultantAccount = accountDAO.queryByRoleForUpdate(consult.getConsultantId(),
            UserRoleEnum.CONSULTANT.getValue());
        AssertUtil.notNull(consultantAccount, "咨询者账户不存在");
        List<TransRecord> transRecords = transRecordDAO.queryByConsultIdAndType(consultId,
            TransTypeEnum.FREEZE_CONSULT_DEPOSIT.getValue());
        AssertUtil.state(transRecords != null && transRecords.size() == 1
                         && transRecords.get(0).getMoney() == consult.getPrice(), "异常记录，请联系客服处理");

        TransRecord guaranteeRecord = new TransRecord();
        guaranteeRecord.setConsultId(consult.getId());
        guaranteeRecord.setDetail("咨询被拒绝，退换支付费用，咨询ID:" + String.valueOf(consult.getId()));
        guaranteeRecord.setGmtCreate(new Date());
        guaranteeRecord.setMoney(consult.getPrice());
        guaranteeRecord.setRelRoleId(CcConstrant.ADMINISTOR_ROLE_ID);
        guaranteeRecord.setRelRoleType(UserRoleEnum.ADMINISTRATOR.getValue());
        guaranteeRecord.setChargeType(ChargeTypeEnum.CHARGE_OUT.getValue());
        guaranteeRecord.setTransType(TransTypeEnum.UN_FREEZE_DEABCK_MONEY.getValue());
        transRecordDAO.save(guaranteeRecord);//退换担保费用

        TransRecord transRecord = new TransRecord();
        transRecord.setConsultId(consult.getId());
        transRecord.setDetail("咨询被拒绝，退换支付费用，咨询ID:" + String.valueOf(consult.getId()));
        transRecord.setGmtCreate(new Date());
        transRecord.setMoney(consult.getPrice());
        transRecord.setRelRoleId(consult.getConsultantId());
        transRecord.setChargeType(ChargeTypeEnum.CHARGE_IN.getValue());
        transRecord.setRelRoleType(UserRoleEnum.CONSULTANT.getValue());
        transRecord.setTransType(TransTypeEnum.DEBACK_CONSULT_DEPOSIT.getValue());
        transRecordDAO.save(transRecord);

        //添加转账记录（转出一条+平台收到转账一条）
        double current = consultantAccount.getCurrentMoney() + consult.getPrice();
        consultantAccount.setCurrentMoney(current);
        consultantAccount.setFreezingMoney(consultantAccount.getFreezingMoney()
                                           - consult.getPrice());
        accountDAO.update(consultantAccount);
    }

}
