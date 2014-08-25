package com.ccconsult.dao;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ccconsult.pojo.ConsultInterview;

/**
 	* A data access object (DAO) providing persistence and search support for InterviewConsult entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.ccconsult.pojo.InterviewConsult
  * @author MyEclipse Persistence Tools 
 */

public class ConsultInterviewDAO extends BaseHibernateDAO<ConsultInterview> {
    private static final Logger log            = LoggerFactory.getLogger(ConsultInterviewDAO.class);
    //property constants
    public static final String  RESUME_FILE    = "resumeFile";
    public static final String  CONSULT_ID     = "consultId";
    public static final String  INTERVIEW_MEMO = "interviewMemo";
    public static final String  SCHEDUE_TIME   = "schedueTime";

    public ConsultInterview findById(java.lang.Integer id) {
        log.debug("getting ConsultInterview instance with id: " + id);
        try {
            ConsultInterview instance = (ConsultInterview) getSession().get(
                "com.ccconsult.pojo.ConsultInterview", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding ConsultInterview instance with property: " + propertyName + ", value: "
                  + value);
        try {
            String queryString = "from ConsultInterview as model where model." + propertyName
                                 + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public ConsultInterview findByConsultId(Object consultId) {
        return getLimit(findByProperty(CONSULT_ID, consultId));
    }

}