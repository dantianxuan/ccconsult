package com.ccconsult.dao;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ccconsult.pojo.Service;

/**
 	* A data access object (DAO) providing persistence and search support for Service entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.ccconsult.pojo.Service
  * @author MyEclipse Persistence Tools 
 */

public class ServiceDAO extends BaseHibernateDAO<Service> {
    private static final Logger log          = LoggerFactory.getLogger(ServiceDAO.class);
    //property constants
    public static final String  NAME         = "name";
    public static final String  DESCRIPTION  = "description";
    public static final String  PRICE_REGION = "priceRegion";
    public static final String  RULE         = "rule";
    public static final String  GMT_CREATE   = "gmtCreate";
    public static final String  GMT_MODIFIED = "gmtModified";

    public Service findById(java.lang.Integer id) {
        log.debug("getting Service instance with id: " + id);
        try {
            Service instance = (Service) getSession().get("com.ccconsult.pojo.Service", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding Service instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from Service as model where model." + propertyName + "= ?";
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

    public List findByPriceRegion(Object priceRegion) {
        return findByProperty(PRICE_REGION, priceRegion);
    }

    public List findByRule(Object rule) {
        return findByProperty(RULE, rule);
    }

    public List findByGmtCreate(Object gmtCreate) {
        return findByProperty(GMT_CREATE, gmtCreate);
    }

    public List findByGmtModified(Object gmtModified) {
        return findByProperty(GMT_MODIFIED, gmtModified);
    }

    public List<Service> findAll() {
        log.debug("finding all Service instances");
        try {
            String queryString = "from Service";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

}