package com.ccconsult.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ccconsult.pojo.RegMail;

/**
 	* A data access object (DAO) providing persistence and search support for RegMail entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.ccconsult.pojo.RegMail
  * @author MyEclipse Persistence Tools 
 */

public class RegMailDAO extends BaseHibernateDAO<RegMail> {
    private static final Logger log   = LoggerFactory.getLogger(RegMailDAO.class);
    //property constants
    public static final String  MAIL  = "mail";
    public static final String  TOKEN = "token";

    public RegMail findById(java.lang.Integer id) {
        log.debug("getting RegMail instance with id: " + id);
        try {
            RegMail instance = (RegMail) getSession().get("com.ccconsult.pojo.RegMail", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(RegMail instance) {
        log.debug("finding RegMail instance by example");
        try {
            List results = getSession().createCriteria("com.ccconsult.pojo.RegMail")
                .add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding RegMail instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from RegMail as model where model." + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public RegMail findByMail(Object mail) {
        Object object = getLimit(findByProperty(MAIL, mail));
        if (object == null) {
            return null;
        }
        return (RegMail) object;
    }

    public RegMail findByToken(Object token) {
        Object object = getLimit(findByProperty(TOKEN, token));
        if (object == null) {
            return null;
        }
        return (RegMail) object;

    }

}