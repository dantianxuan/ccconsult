/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.base;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.ccconsult.dao.HibernateSessionFactory;
import com.ccconsult.util.LogUtil;

/**
 * 
 * @author jingyu.dan
 * @version $Id: ServiceTemplateImpl.java, v 0.1 2014-5-30 下午5:54:22 jingyu.dan Exp $
 */
@SuppressWarnings("unchecked")
public class ServiceTemplateImpl implements ServiceTemplate {

    /**日志 */
    private static final Logger logger = Logger.getLogger(ServiceTemplateImpl.class);

    private Session getSession() {
        return HibernateSessionFactory.getSession();
    }

    @Override
    public <T> T execute(Class<? extends CcResult> clazz, ServiceCallBack action) {
        CcResult result = null;
        Session session = getSession();
        try {
            session = getSession();
            session.beginTransaction();
            result = clazz.newInstance();
            // 执行校验
            action.check();
            // 执行处理逻辑
            result = action.executeService();
            if (result == null || !(result instanceof CcResult)) {
                throw new RuntimeException("逻辑错误");
            }
            session.getTransaction().commit();
        } catch (CcException e) {
            // 业务异常捕获
            LogUtil.error(logger, e, "【业务异常】");
            result.setCode(e.getCode());
            result.setSuccess(false);
            result.setMessage(e.getLocalizedMessage());
            session.flush();
            session.getTransaction().rollback();
            return (T) result;
        } catch (Throwable e2) {
            result.setSuccess(false);
            LogUtil.error(logger, e2, "【系统异常】");
            session.getTransaction().rollback();
            return (T) result;
        } finally {
            session.close();
        }
        LogUtil.info(logger, "【退出事务服务模板】", result);
        return (T) result;
    }

    @Override
    public <T> T executeWithTx(Class<? extends CcResult> clazz, ServiceCallBack action) {
        CcResult result = null;
        Session session = getSession();
        try {
            session = getSession();
            session.beginTransaction();
            result = clazz.newInstance();
            // 执行校验
            action.check();
            // 执行处理逻辑
            result = action.executeService();
            if (result == null || !(result instanceof CcResult)) {
                throw new RuntimeException("逻辑错误");
            }
            session.flush();
            session.getTransaction().commit();
        } catch (CcException e) {
            // 业务异常捕获
            LogUtil.error(logger, e, "【业务异常】");
            result.setCode(e.getCode());
            result.setSuccess(false);
            result.setMessage(e.getLocalizedMessage());
            session.getTransaction().rollback();
            return (T) result;
        } catch (Throwable e2) {
            result.setSuccess(false);
            LogUtil.error(logger, e2, "【系统异常】");
            session.getTransaction().rollback();
            return (T) result;
        } finally {
            session.close();
        }
        LogUtil.info(logger, "【退出无事务服务模板】", result);
        return (T) result;
    }

}
