package com.ccconsult.dao;

import com.ccconsult.pojo.Comment;

import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 	* A data access object (DAO) providing persistence and search support for Comment entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.ccconsult.pojo.Comment
  * @author MyEclipse Persistence Tools 
 */

public class CommentDAO extends BaseHibernateDAO {
    private static final Logger log        = LoggerFactory.getLogger(CommentDAO.class);
    //property constants
    public static final String  COMMENT    = "comment";
    public static final String  THEME_TYPE = "themeType";
    public static final String  THEME_ID   = "themeId";
    public static final String  CREATOR    = "creator";

    public void save(Comment transientInstance) {
        log.debug("saving Comment instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(Comment persistentInstance) {
        log.debug("deleting Comment instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public Comment findById(java.lang.Integer id) {
        log.debug("getting Comment instance with id: " + id);
        try {
            Comment instance = (Comment) getSession().get("com.ccconsult.pojo.Comment", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(Comment instance) {
        log.debug("finding Comment instance by example");
        try {
            List results = getSession().createCriteria("com.ccconsult.pojo.Comment")
                .add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding Comment instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from Comment as model where model." + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByComment(Object comment) {
        return findByProperty(COMMENT, comment);
    }

    public List findByThemeType(Object themeType) {
        return findByProperty(THEME_TYPE, themeType);
    }

    public List findByThemeId(Object themeId) {
        return findByProperty(THEME_ID, themeId);
    }

    public List findByCreator(Object creator) {
        return findByProperty(CREATOR, creator);
    }

    public List findAll() {
        log.debug("finding all Comment instances");
        try {
            String queryString = "from Comment";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public Comment merge(Comment detachedInstance) {
        log.debug("merging Comment instance");
        try {
            Comment result = (Comment) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Comment instance) {
        log.debug("attaching dirty Comment instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(Comment instance) {
        log.debug("attaching clean Comment instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}