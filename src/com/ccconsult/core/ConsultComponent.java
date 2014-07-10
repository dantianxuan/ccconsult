/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.core;

import com.ccconsult.base.PageList;
import com.ccconsult.view.ConsultBase;

/**
 * 
 * @author jingyudan
 * @version $Id: CounsultComponent.java, v 0.1 2014-7-7 下午9:56:30 jingyudan Exp $
 */
public interface ConsultComponent {

    /**
     * 分页查询在step以下的记录
     * 
     * @return
     */
    public PageList<ConsultBase> queryUnderStepPaged(int step, int serviceId, int counselorId,
                                                      int consultantId, int pageSize, int pageNo);

    /**
     * 分页查询记录
     * 
     * @return
     */
    public PageList<ConsultBase> queryPaged(int step, int serviceId, int counselorId,
                                             int consultantId, int pageSize, int pageNo);

    /**
     * 通过id查询咨询信息
     * 
     * @return
     */
    public ConsultBase queryById(int consultId);

    /**
     * 创建咨询记录
     * 
     * @return
     */
    public int payForConsult();

}
