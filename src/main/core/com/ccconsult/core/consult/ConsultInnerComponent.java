/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.core.consult;

import com.ccconsult.pojo.Consult;

/**
 *   站内咨询服务
 * 
 * @author jingyudan
 * @version $Id: ConsultInnerComponent.java, v 0.1 2014-8-30 下午9:24:12 jingyudan Exp $
 */
public interface ConsultInnerComponent {

    /**
     * 创建内部咨询
     * 
     * @param consult
     * @return
     */
    public void createConsult(Consult consult);

}
