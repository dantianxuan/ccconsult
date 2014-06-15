package com.ccconsult.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ccconsult.pojo.Article;

/**
 	* A data access object (DAO) providing persistence and search support for Article entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.ccconsult.pojo.Article
  * @author MyEclipse Persistence Tools 
 */

public class ArticleDAO extends BaseHibernateDAO<Article> {
    private static final Logger log       = LoggerFactory.getLogger(ArticleDAO.class);
    //property constants
    public static final String  TITLE     = "title";
    public static final String  CONTENT   = "content";
    public static final String  TOP_PHOTO = "topPhoto";
    public static final String  SATE      = "sate";
    public static final String  TOP       = "top";

    public Article findById(java.lang.Integer id) {
        log.debug("getting Article instance with id: " + id);
        try {
            Article instance = (Article) getSession().get("com.ccconsult.pojo.Article", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List<Article> findRecentList(int topx) {
        String hql = "from Article order by gmtCreate desc";
        return findPageByQuery(0, topx, hql, null);
    }
}