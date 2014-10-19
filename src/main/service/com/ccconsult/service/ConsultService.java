/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.service;

import com.ccconsult.base.CcResult;

/**
 * 咨询服务
 * 
 * @author jingyudan
 * @version $Id: ConsultService.java, v 0.1 2014年10月19日 上午8:00:18 jingyudan Exp $
 */
public interface ConsultService {

    /**
     * 支付咨询费用
     * 
     * @param consultId
     */
    public CcResult payForConsult(int consultId);

    /**
     * 拒绝咨询咨询费用
     * 
     * @param consultId
     */
    public CcResult rejectConsult(int consultId);

    /**
     * 咨询费用
     * 
     * @param consultId
     */
    public CcResult finishConsultOrder(int consultId);

}
