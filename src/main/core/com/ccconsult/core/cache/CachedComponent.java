/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.core.cache;

/**
 * 缓存组件
 * 
 * @author jingyudan
 * @version $Id: CachedComponent.java, v 0.1 2014-8-8 下午9:41:27 jingyudan Exp $
 */
public interface CachedComponent {

    /**
     * 缓存读取
     * 
     * @param cacheName
     * @param key
     * @return
     */
    public Object getCache(String cacheName, String key);

}
