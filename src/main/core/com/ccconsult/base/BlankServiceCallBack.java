/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.base;

/**
 * 
 * @author jingyu.dan
 * @version $Id: BlankServiceCallBack.java, v 0.1 2014-5-30 下午8:04:40 jingyu.dan Exp $
 */
public class BlankServiceCallBack implements ServiceCallBack {

    /** 
     * @see com.ccconsult.base.ServiceCallBack#doLock()
     */
    @Override
    public void doLock() {
    }

    /** 
     * @see com.ccconsult.base.ServiceCallBack#check()
     */
    @Override
    public void check() {
    }

    /** 
     * @see com.ccconsult.base.ServiceCallBack#executeService()
     */
    @Override
    public CcResult executeService() {
        return null;
    }

}
