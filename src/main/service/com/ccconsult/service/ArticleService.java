/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.service;

import com.ccconsult.base.CcResult;
import com.ccconsult.pojo.Article;

/**
 * 文章操作服务
 * @author jingyu.dan
 * @version $Id: ArticleService.java, v 0.1 2014-6-2 下午4:24:29 jingyu.dan Exp $
 */
public interface ArticleService {

    /**
     * 保存文章
     * 
     * @param article
     * @return
     */
    public CcResult saveArticle(Article article);

    /**
     * 保存文章
     * 
     * @param article
     * @return
     */
    public CcResult updateArticle(Article article);

}
