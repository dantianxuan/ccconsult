/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.base;

/**
 * 
 * @author jingyu.dan
 * @version $Id: ServiceTemplate.java, v 0.1 2014-5-30 下午5:53:41 jingyu.dan Exp $
 */
public interface ServiceTemplate {

    /**
     * <pre> 无事务模板执行业务处理
     * 1. 异常捕获，及分类处理
     * 2. 业务日志记录
     * </pre>
     * 
     * @param <T>
     * @param clazz  返回对象
     * @param action 业务操作回调的接口
     * @return       服务返回对象
     */
    public <T> T executeWithTx(Class<? extends CcResult> clazz, ServiceCallBack action);

    /**
     * <pre> 支持本地事务模板执行业务处理
     * 1. 本地事务封装
     * 2. 异常捕获，及分类处理
     * 3. 业务日志记录
     * </pre>
     * 
     * @param <T>
     * @param clazz  返回对象
     * @param action 业务操作回调的接口
     * @return       服务返回对象
     */
    public <T> T execute(Class<? extends CcResult> clazz, ServiceCallBack action);

}
