/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.core.consult;

import com.ccconsult.base.CcResult;

/**
 *   站内咨询服务
 * 
 * @author jingyudan
 * @version $Id: ConsultComponent.java, v 0.1 2014-8-30 下午9:24:12 jingyudan Exp $
 */
public interface ConsultComponent {

    /***
     * 拒绝咨询，在拒绝咨询的时候会将咨询费退换
     * 
     * @param consultId     
     * @param reason           
     * @return
     */
    public CcResult rejectConsult(int consultId, String reason);

    /***
     * 删除咨询
     * 
     * @param consultId     
     * @return
     */
    public CcResult deleteConsult(int consultId);

}
