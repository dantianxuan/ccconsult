package com.ccconsult.dao;

import com.ccconsult.pojo.ConsultInterview;
import com.ccconsult.pojo.ConsultOnline;

import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 	* A data access object (DAO) providing persistence and search support for ConsultOnline entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.ccconsult.pojo.ConsultOnline
  * @author MyEclipse Persistence Tools 
 */

public class ConsultOnlineDAO extends BaseHibernateDAO<ConsultOnline> {
    private static final Logger log          = LoggerFactory.getLogger(ConsultOnlineDAO.class);
    //property constants
    public static final String  CONSULT_ID   = "consultId";
    public static final String  SCHEDUE_TIME = "schedueTime";

    public ConsultOnline findById(java.lang.Integer id) {
        log.debug("getting ConsultOnline instance with id: " + id);
        try {
            ConsultOnline instance = (ConsultOnline) getSession().get(
                "com.ccconsult.pojo.ConsultOnline", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding ConsultOnline instance with property: " + propertyName + ", value: "
                  + value);
        try {
            String queryString = "from ConsultOnline as model where model." + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public ConsultOnline findByConsultId(Object consultId) {
        return getLimit(findByProperty(CONSULT_ID, consultId));
    }

}