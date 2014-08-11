/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.core.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.ccconsult.base.CcConstrant;
import com.ccconsult.util.LogUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;

/**
 * 缓存组件
 * 
 * @author jingyudan
 * @version $Id: CachedComponentImpl.java, v 0.1 2014-8-8 下午9:45:25 jingyudan Exp $
 */
public class CachedComponentImpl implements CachedComponent {

    /**缓存加载器*/
    private Map<String, LoacalCacheLoader> cacheLoaders = new HashMap<String, LoacalCacheLoader>();

    /**缓存对象*/
    private Cache<String, Object>          cache        = CacheBuilder
                                                            .newBuilder()
                                                            .maximumSize(10000)
                                                            .refreshAfterWrite(60, TimeUnit.MINUTES)
                                                            .build(
                                                                new CacheLoader<String, Object>() {

                                                                    @Override
                                                                    public Object load(String key)
                                                                                                  throws Exception {
                                                                        return this.load(key);
                                                                    }
                                                                });
    /**日志 */
    private static final Logger            logger       = Logger
                                                            .getLogger(CachedComponentImpl.class);

    /** 
     * 
     * 
     * @see com.ccconsult.core.cache.CachedComponent#getCache(java.lang.String, java.lang.String)
     */
    @Override
    public Object getCache(final String cacheName, final String key) {
        try {
            return cache.get(cacheName + CcConstrant.ALT_SEPARATOR + key, new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    return cacheLoaders.get(cacheName).loadCache(key);
                }
            });
        } catch (Exception e) {
            LogUtil.warn(logger, "加载缓存失败", cacheName, cacheName);
        }
        return null;
    }

    public Map<String, LoacalCacheLoader> getCacheLoaders() {
        return cacheLoaders;
    }

    public void setCacheLoaders(Map<String, LoacalCacheLoader> cacheLoaders) {
        this.cacheLoaders = cacheLoaders;
    }

}
