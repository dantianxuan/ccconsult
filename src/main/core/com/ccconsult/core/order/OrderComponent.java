/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.core.order;


/**
 * 
 * @author jingyudan
 * @version $Id: AccountComponent.java, v 0.1 2014-8-31 下午9:31:07 jingyudan Exp $
 */
public interface OrderComponent {

    /**
     * finishOrder
     * 
     * @param consultId
     */
    public void finishConsultOrder(int consultId);

    /**
     * 
     * 
     * @return
     */
    public void payConsultOrder(int consultId);

    /**
     * 
     * 
     * @return
     */
    public void rejectConsultOrder(int consultId);

}
