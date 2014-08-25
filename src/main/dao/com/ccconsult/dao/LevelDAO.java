package com.ccconsult.dao;

import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ccconsult.pojo.BaseHibernateDAO;
import com.ccconsult.pojo.Level;

/**
 	* A data access object (DAO) providing persistence and search support for Level entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.ccconsult.pojo.Level
  * @author MyEclipse Persistence Tools 
 */

public class LevelDAO extends BaseHibernateDAO {
    private static final Logger log         = LoggerFactory.getLogger(LevelDAO.class);
    //property constants
    public static final String  NAME        = "name";
    public static final String  DESCRIPTION = "description";
    public static final String  LEVEL       = "level";

    public void save(Level transientInstance) {
        log.debug("saving Level instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(Level persistentInstance) {
        log.debug("deleting Level instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public Level findById(java.lang.Integer id) {
        log.debug("getting Level instance with id: " + id);
        try {
            Level instance = (Level) getSession().get("com.ccconsult.pojo.Level", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(Level instance) {
        log.debug("finding Level instance by example");
        try {
            List results = getSession().createCriteria("com.ccconsult.pojo.Level")
                .add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding Level instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from Level as model where model." + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByName(Object name) {
        return findByProperty(NAME, name);
    }

    public List findByDescription(Object description) {
        return findByProperty(DESCRIPTION, description);
    }

    public List findByLevel(Object level) {
        return findByProperty(LEVEL, level);
    }

    public List findAll() {
        log.debug("finding all Level instances");
        try {
            String queryString = "from Level";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public Level merge(Level detachedInstance) {
        log.debug("merging Level instance");
        try {
            Level result = (Level) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Level instance) {
        log.debug("attaching dirty Level instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(Level instance) {
        log.debug("attaching clean Level instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}