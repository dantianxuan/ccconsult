package com.ccconsult.dao;

import com.ccconsult.pojo.InnerMail;

import java.util.Date;
import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 	* A data access object (DAO) providing persistence and search support for InnerMail entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.ccconsult.pojo.InnerMail
  * @author MyEclipse Persistence Tools 
 */

public class InnerMailDAO extends BaseHibernateDAO {
    private static final Logger log           = LoggerFactory.getLogger(InnerMailDAO.class);
    //property constants
    public static final String  CONTENT       = "content";
    public static final String  SENDER_ID     = "senderId";
    public static final String  SENDER_ROLE   = "senderRole";
    public static final String  CREATOR       = "creator";
    public static final String  RECEIVER_ID   = "receiverId";
    public static final String  RECEIVER_ROLE = "receiverRole";
    public static final String  STATE         = "state";
    public static final String  TYPE          = "type";

    public void save(InnerMail transientInstance) {
        log.debug("saving InnerMail instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(InnerMail persistentInstance) {
        log.debug("deleting InnerMail instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public InnerMail findById(java.lang.Integer id) {
        log.debug("getting InnerMail instance with id: " + id);
        try {
            InnerMail instance = (InnerMail) getSession().get("com.ccconsult.pojo.InnerMail", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(InnerMail instance) {
        log.debug("finding InnerMail instance by example");
        try {
            List results = getSession().createCriteria("com.ccconsult.pojo.InnerMail")
                .add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding InnerMail instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from InnerMail as model where model." + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByContent(Object content) {
        return findByProperty(CONTENT, content);
    }

    public List findBySenderId(Object senderId) {
        return findByProperty(SENDER_ID, senderId);
    }

    public List findBySenderRole(Object senderRole) {
        return findByProperty(SENDER_ROLE, senderRole);
    }

    public List findByCreator(Object creator) {
        return findByProperty(CREATOR, creator);
    }

    public List findByReceiverId(Object receiverId) {
        return findByProperty(RECEIVER_ID, receiverId);
    }

    public List findByReceiverRole(Object receiverRole) {
        return findByProperty(RECEIVER_ROLE, receiverRole);
    }

    public List findByState(Object state) {
        return findByProperty(STATE, state);
    }

    public List findByType(Object type) {
        return findByProperty(TYPE, type);
    }

    public List findAll() {
        log.debug("finding all InnerMail instances");
        try {
            String queryString = "from InnerMail";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public InnerMail merge(InnerMail detachedInstance) {
        log.debug("merging InnerMail instance");
        try {
            InnerMail result = (InnerMail) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(InnerMail instance) {
        log.debug("attaching dirty InnerMail instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(InnerMail instance) {
        log.debug("attaching clean InnerMail instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}