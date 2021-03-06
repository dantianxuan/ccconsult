package com.ccconsult.dao;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ccconsult.pojo.ConsultResume;

/**
 	* A data access object (DAO) providing persistence and search support for ResumeConsult entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.ccconsult.pojo.ResumeConsult
  * @author MyEclipse Persistence Tools 
 */

public class ConsultResumeDAO extends BaseHibernateDAO<ConsultResume> {
    private static final Logger log          = LoggerFactory.getLogger(ConsultResumeDAO.class);
    //property constants
    public static final String  RESUME_FILES = "resumeFiles";
    public static final String  REVIEW       = "review";
    public static final String  REVIEW_FILES = "reviewFiles";
    public static final String  CONSULT_ID   = "consultId";

    public ConsultResume findById(java.lang.Integer id) {
        log.debug("getting ConsultResume instance with id: " + id);
        try {
            ConsultResume instance = (ConsultResume) getSession().get(
                "com.ccconsult.pojo.ConsultResume", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding ResumeConsult instance with property: " + propertyName + ", value: "
                  + value);
        try {
            String queryString = "from ConsultResume as model where model." + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public ConsultResume findByConsultId(Object consultId) {
        return getLimit(findByProperty(CONSULT_ID, consultId));
    }
}