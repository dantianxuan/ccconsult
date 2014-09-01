/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.core.consult;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.ccconsult.base.AssertUtil;
import com.ccconsult.base.CcConstrant;
import com.ccconsult.base.enums.ConsultStepEnum;
import com.ccconsult.base.enums.PayStateEnum;
import com.ccconsult.base.util.CodeGenUtil;
import com.ccconsult.base.util.DateUtil;
import com.ccconsult.dao.ConsultDAO;
import com.ccconsult.dao.CounselorDAO;
import com.ccconsult.dao.ServiceDAO;
import com.ccconsult.pojo.Consult;
import com.ccconsult.pojo.Service;
import com.ccconsult.web.view.CounselorVO;

/**
 * 
 * @author jingyudan
 * @version $Id: ConsultInnerComponentImpl.java, v 0.1 2014-8-31 下午8:47:13 jingyudan Exp $
 */
public class ConsultInnerComponentImpl implements ConsultInnerComponent {

    @Autowired
    private CounselorDAO counselorDAO;
    @Autowired
    private ServiceDAO   serviceDAO;
    @Autowired
    private ConsultDAO   consultDAO;

    /** 
     * @see com.ccconsult.core.consult.ConsultInnerComponent#createConsultInner(com.ccconsult.pojo.Consult)
     */
    @Override
    public void createConsult(Consult consult) {
        CounselorVO counselorVO = counselorDAO.findById(consult.getCounselorId());
        AssertUtil.notNull(counselorVO, "咨询对象不存在，请检查");
        AssertUtil.notBlank(consult.getGoal(), "必须输入您需要咨询的问题");
        AssertUtil.state(consult.getGoal().length() <= CcConstrant.COMMON_512_LENGTH,
            "描述问题太长，请简洁描述");
        consult.setGmtCreate(new Date());
        consult.setStep(ConsultStepEnum.ON_CONSULT.getValue());
        consult.setPayTag(PayStateEnum.PAY_SUCCESS.getValue());//无须支付
        Service service = serviceDAO.findById(consult.getServiceId());
        AssertUtil.notNull(service, "服务不存在，请检查");
        consult.setGmtEffectBegin(new Date());
        consult.setGmtEffectEnd(DateUtil.addHours(new Date(), service.getEffectTime()));
        consult.setGmtModified(new Date());
        consult.setIndetityCode(CodeGenUtil.getFixLenthString(6));
        consultDAO.save(consult);
    }

}
