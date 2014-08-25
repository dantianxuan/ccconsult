package com.ccconsult.dao;

import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ccconsult.pojo.BaseHibernateDAO;
import com.ccconsult.pojo.Diag;

/**
 	* A data access object (DAO) providing persistence and search support for Diag entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.ccconsult.pojo.Diag
  * @author MyEclipse Persistence Tools 
 */

public class DiagDAO extends BaseHibernateDAO {
    private static final Logger log          = LoggerFactory.getLogger(DiagDAO.class);
    //property constants
    public static final String  GMT_CREATE   = "gmtCreate";
    public static final String  TOKEN        = "token";
    public static final String  GMT_END      = "gmtEnd";
    public static final String  CREATOR_ROLE = "creatorRole";
    public static final String  CREATOR_ID   = "creatorId";
    public static final String  REL_ID       = "relId";
    public static final String  REL_THEME    = "relTheme";

    public void save(Diag transientInstance) {
        log.debug("saving Diag instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(Diag persistentInstance) {
        log.debug("deleting Diag instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public Diag findById(java.lang.Integer id) {
        log.debug("getting Diag instance with id: " + id);
        try {
            Diag instance = (Diag) getSession().get("com.ccconsult.pojo.Diag", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(Diag instance) {
        log.debug("finding Diag instance by example");
        try {
            List results = getSession().createCriteria("com.ccconsult.pojo.Diag")
                .add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding Diag instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from Diag as model where model." + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByGmtCreate(Object gmtCreate) {
        return findByProperty(GMT_CREATE, gmtCreate);
    }

    public List findByToken(Object token) {
        return findByProperty(TOKEN, token);
    }

    public List findByGmtEnd(Object gmtEnd) {
        return findByProperty(GMT_END, gmtEnd);
    }

    public List findByCreatorRole(Object creatorRole) {
        return findByProperty(CREATOR_ROLE, creatorRole);
    }

    public List findByCreatorId(Object creatorId) {
        return findByProperty(CREATOR_ID, creatorId);
    }

    public List findByRelId(Object relId) {
        return findByProperty(REL_ID, relId);
    }

    public List findByRelTheme(Object relTheme) {
        return findByProperty(REL_THEME, relTheme);
    }

    public List findAll() {
        log.debug("finding all Diag instances");
        try {
            String queryString = "from Diag";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public Diag merge(Diag detachedInstance) {
        log.debug("merging Diag instance");
        try {
            Diag result = (Diag) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Diag instance) {
        log.debug("attaching dirty Diag instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(Diag instance) {
        log.debug("attaching clean Diag instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}