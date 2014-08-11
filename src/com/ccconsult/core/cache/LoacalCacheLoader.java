/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.core.cache;

/**
 * 
 * @author jingyudan
 * @version $Id: CacheLoader.java, v 0.1 2014-8-8 下午10:10:24 jingyudan Exp $
 */
public interface LoacalCacheLoader {

    /**缓存数据*/
    public final static String ALL = "all_cache";

    /**
     * 加载cacache
     * 
     * @param key
     * @return
     */
    public Object loadCache(String key);
}
