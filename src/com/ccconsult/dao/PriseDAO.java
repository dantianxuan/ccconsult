package com.ccconsult.dao;

import com.ccconsult.pojo.Prise;

import java.util.Date;
import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 	* A data access object (DAO) providing persistence and search support for Prise entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.ccconsult.pojo.Prise
  * @author MyEclipse Persistence Tools 
 */

public class PriseDAO extends BaseHibernateDAO {
    private static final Logger log     = LoggerFactory.getLogger(PriseDAO.class);
    //property constants
    public static final String  PRISE   = "prise";
    public static final String  CREATOR = "creator";
    public static final String  TYPE    = "type";

    public void save(Prise transientInstance) {
        log.debug("saving Prise instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(Prise persistentInstance) {
        log.debug("deleting Prise instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public Prise findById(java.lang.Integer id) {
        log.debug("getting Prise instance with id: " + id);
        try {
            Prise instance = (Prise) getSession().get("com.ccconsult.pojo.Prise", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(Prise instance) {
        log.debug("finding Prise instance by example");
        try {
            List results = getSession().createCriteria("com.ccconsult.pojo.Prise")
                .add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding Prise instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from Prise as model where model." + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByPrise(Object prise) {
        return findByProperty(PRISE, prise);
    }

    public List findByCreator(Object creator) {
        return findByProperty(CREATOR, creator);
    }

    public List findByType(Object type) {
        return findByProperty(TYPE, type);
    }

    public List findAll() {
        log.debug("finding all Prise instances");
        try {
            String queryString = "from Prise";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public Prise merge(Prise detachedInstance) {
        log.debug("merging Prise instance");
        try {
            Prise result = (Prise) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Prise instance) {
        log.debug("attaching dirty Prise instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(Prise instance) {
        log.debug("attaching clean Prise instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}