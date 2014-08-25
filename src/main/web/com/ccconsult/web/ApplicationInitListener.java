/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ccconsult.core.cache.LoacalCacheLoader;
import com.ccconsult.core.cache.CachedComponent;
import com.ccconsult.enums.CacheEnum;
import com.ccconsult.pojo.Level;
import com.ccconsult.pojo.Service;
import com.ccconsult.util.LogUtil;

/**
 * 系统初始化监听器
 * 
 * @author jingyudan
 * @version $Id: ApplicationInitListener.java, v 0.1 2014-8-9 上午9:25:35 jingyudan Exp $
 */
public class ApplicationInitListener implements ServletContextListener {

    /**日志 */
    private static final Logger          logger = Logger.getLogger(ApplicationInitListener.class);

    private static WebApplicationContext springContext;

    /** 
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
    }

    /** 
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextInitialized(ServletContextEvent arg0) {

        springContext = WebApplicationContextUtils.getWebApplicationContext(arg0
            .getServletContext());
        CachedComponent cachedComponent = (CachedComponent) springContext
            .getBean("cachedComponent");
        LogUtil.info(logger, "初始化等级数据");
        //初始化基本的等级信息
        List<Level> levels = (List<Level>) cachedComponent.getCache(
            CacheEnum.LEVEL_CACHE.getCode(), LoacalCacheLoader.ALL);
        arg0.getServletContext().setAttribute("levelList", levels);
        //初始化基本的
        Map<String, Level> levelMap = new HashMap<String, Level>();
        for (Level level : levels) {
            levelMap.put(String.valueOf(level.getId()), level);
        }
        arg0.getServletContext().setAttribute("levelMap", levelMap);

        //初始化基本的等级信息
        LogUtil.info(logger, "初始化服务信息");
        List<Service> services = (List<Service>) cachedComponent.getCache(
            CacheEnum.SERVICE_CACHE.getCode(), LoacalCacheLoader.ALL);
        arg0.getServletContext().setAttribute("serviceList", services);
        Map<String, Service> serviceMap = new HashMap<String, Service>();
        for (Service service : services) {
            serviceMap.put(String.valueOf(service.getId()), service);
        }
        arg0.getServletContext().setAttribute("serviceMap", serviceMap);
    }

}
