/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.base;

/**
 * 
 * @author jingyu.dan
 * @version $Id: ServiceCallBack.java, v 0.1 2014-5-30 下午5:53:10 jingyu.dan Exp $
 */
public interface ServiceCallBack {

    /**
     *  锁
     */
    void doLock();

    /**
     * <pre> 校验 
     * 对于校验不通过，异常驱动返回
     * </pre>
     */
    void check();

    /**
     * <pre> 执行业务逻辑
     * </pre>
     * @return 
     */
    CcResult executeService();

}
