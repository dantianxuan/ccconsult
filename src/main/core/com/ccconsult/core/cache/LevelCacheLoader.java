/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.core.cache;

import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.ccconsult.base.util.StringUtil;
import com.ccconsult.dao.LevelDAO;
import com.ccconsult.pojo.Level;

/**
 * 所有的等级数据
 * 
 * @author jingyudan
 * @version $Id: LevelCacheLoader.java, v 0.1 2014-8-8 下午10:23:41 jingyudan Exp $
 */
public class LevelCacheLoader implements LoacalCacheLoader {

    @Autowired
    private LevelDAO levelDAO;

    /** 
     * @see com.ccconsult.core.cache.LoacalCacheLoader#loadCache(java.lang.String)
     */
    @Override
    public Object loadCache(String key) {
        if (StringUtil.equals(LoacalCacheLoader.ALL, key)) {
            List<Level> levels = levelDAO.findAll();
            return levels;
        }
        return levelDAO.findById(NumberUtils.toInt(key));
    }

}
