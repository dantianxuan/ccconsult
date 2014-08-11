package com.ccconsult.dao;

import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ccconsult.pojo.BaseHibernateDAO;
import com.ccconsult.pojo.DiagMessage;

/**
 	* A data access object (DAO) providing persistence and search support for DiagMessage entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.ccconsult.pojo.DiagMessage
  * @author MyEclipse Persistence Tools 
 */

public class DiagMessageDAO extends BaseHibernateDAO {
    private static final Logger log         = LoggerFactory.getLogger(DiagMessageDAO.class);
    //property constants
    public static final String  SENDER_ROLE = "senderRole";
    public static final String  SENDER_ID   = "senderId";
    public static final String  GMT_CREATE  = "gmtCreate";
    public static final String  MESSAGE     = "message";

    public void save(DiagMessage transientInstance) {
        log.debug("saving DiagMessage instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(DiagMessage persistentInstance) {
        log.debug("deleting DiagMessage instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public DiagMessage findById(java.lang.Integer id) {
        log.debug("getting DiagMessage instance with id: " + id);
        try {
            DiagMessage instance = (DiagMessage) getSession().get("com.ccconsult.pojo.DiagMessage",
                id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(DiagMessage instance) {
        log.debug("finding DiagMessage instance by example");
        try {
            List results = getSession().createCriteria("com.ccconsult.pojo.DiagMessage")
                .add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding DiagMessage instance with property: " + propertyName + ", value: "
                  + value);
        try {
            String queryString = "from DiagMessage as model where model." + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findBySenderRole(Object senderRole) {
        return findByProperty(SENDER_ROLE, senderRole);
    }

    public List findBySenderId(Object senderId) {
        return findByProperty(SENDER_ID, senderId);
    }

    public List findByGmtCreate(Object gmtCreate) {
        return findByProperty(GMT_CREATE, gmtCreate);
    }

    public List findByMessage(Object message) {
        return findByProperty(MESSAGE, message);
    }

    public List findAll() {
        log.debug("finding all DiagMessage instances");
        try {
            String queryString = "from DiagMessage";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public DiagMessage merge(DiagMessage detachedInstance) {
        log.debug("merging DiagMessage instance");
        try {
            DiagMessage result = (DiagMessage) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(DiagMessage instance) {
        log.debug("attaching dirty DiagMessage instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(DiagMessage instance) {
        log.debug("attaching clean DiagMessage instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}