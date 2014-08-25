/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.core.cache;

import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.ccconsult.dao.ServiceDAO;
import com.ccconsult.pojo.Service;
import com.ccconsult.util.StringUtil;

/**
 * 所有的等级数据
 * 
 * @author jingyudan
 * @version $Id: LevelCacheLoader.java, v 0.1 2014-8-8 下午10:23:41 jingyudan Exp $
 */
public class ServiceCacheLoader implements LoacalCacheLoader {

    @Autowired
    private ServiceDAO serviceDAO;

    /** 
     * @see com.ccconsult.core.cache.LoacalCacheLoader#loadCache(java.lang.String)
     */
    @Override
    public Object loadCache(String key) {
        if (StringUtil.equals(LoacalCacheLoader.ALL, key)) {
            List<Service> services = serviceDAO.findAll();
            return services;
        }
        return serviceDAO.findById(NumberUtils.toInt(key));
    }

}
