/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.core.order;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.ccconsult.base.AssertUtil;
import com.ccconsult.base.enums.ConsultStepEnum;
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
    @Override
    public void confirmConsunt(int consultId) {
        Consult consult = consultDAO.findByIdForUpdate(consultId);//加锁读取记录
        AssertUtil.notNull(consult, "咨询记录不存在");
        AssertUtil.state(consult.getStep().equals(ConsultStepEnum.ON_CONSULT.getValue()),
            "当前状态无需确认，请刷新页面");
        //加载当前的支付记录
        if (consult.getPrice() == 0) {
            consult.setStep(ConsultStepEnum.FIHSHED.getValue());
            consultDAO.update(consult);
            return;
        }//如果没有金额，无需转账即可。

        List<TransRecord> transRecords = transRecordDAO.queryByConsultIdAndType(consultId,
            TransTypeEnum.PAY_CONSULT_DEPOSIT.getValue());
        //扣款
        AssertUtil.state((!CollectionUtils.isEmpty(transRecords) && transRecords.size() == 1),
            "不合法的数据，该咨询没有对应的费用记录");
        TransRecord depositRecord = transRecords.get(0);
        AssertUtil.state(depositRecord.getMoney() != consult.getPrice(),
            "不合法的数据，支付的金额异常，请联系管理员进行处理");
        //添加转账记录
        TransRecord counselorMoneyRecord = new TransRecord();
        counselorMoneyRecord.setConsultId(consultId);
        counselorMoneyRecord.setDetail(String.valueOf(consultId));
        counselorMoneyRecord.setGmtCreate(new Date());
        counselorMoneyRecord.setMoney(depositRecord.getMoney()
                                      - PaltformServiceMoneyCalculator.calculator(depositRecord
                                          .getMoney()));
        counselorMoneyRecord.setRelRoleId(consult.getCounselorId());
        counselorMoneyRecord.setRelRoleType(UserRoleEnum.COUNSELOR.getValue());
        counselorMoneyRecord.setTransType(TransTypeEnum.PAY_COUNSELOR_CONSULT_MONEY.getValue());
        transRecordDAO.save(counselorMoneyRecord);

        //平台收费
        TransRecord paltformRecord = new TransRecord();
        paltformRecord.setConsultId(consultId);
        paltformRecord.setDetail(String.valueOf(consultId));
        paltformRecord.setGmtCreate(new Date());
        paltformRecord
            .setMoney(PaltformServiceMoneyCalculator.calculator(depositRecord.getMoney()));
        paltformRecord.setRelRoleId(consult.getCounselorId());
        paltformRecord.setRelRoleType(UserRoleEnum.ADMINISTRATOR.getValue());
        paltformRecord.setTransType(TransTypeEnum.PAY_COUNSELOR_CONSULT_MONEY.getValue());
        //咨询师账户更新
        Account account = accountDAO.queryByRoleForUpdate(consult.getCounselorId(),
            UserRoleEnum.COUNSELOR.getValue());
        account.setCurrentMoney(account.getCurrentMoney() + counselorMoneyRecord.getMoney());
        account.setInAllMoney(account.getInAllMoney() + counselorMoneyRecord.getMoney());
        accountDAO.update(account);
    }
}
