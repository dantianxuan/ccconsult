package com.ccconsult.dao;

import com.ccconsult.pojo.InterviewConsult;
import java.util.List;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 	* A data access object (DAO) providing persistence and search support for InterviewConsult entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.ccconsult.pojo.InterviewConsult
  * @author MyEclipse Persistence Tools 
 */

public class InterviewConsultDAO extends BaseHibernateDAO<InterviewConsult> {
    private static final Logger log                = LoggerFactory
                                                       .getLogger(InterviewConsultDAO.class);
    //property constants
    public static final String  RESUME_FILE        = "resumeFile";
    public static final String  CONSULT_ID         = "consultId";
    public static final String  TARGET_JOB_INFO    = "targetJobInfo";
    public static final String  CONSULTANT_APPRISE = "consultantApprise";
    public static final String  CONSULT_MEMO       = "consultMemo";
    public static final String  SCHEDUE_TIME       = "schedueTime";

    public InterviewConsult findById(java.lang.Integer id) {
        log.debug("getting InterviewConsult instance with id: " + id);
        try {
            InterviewConsult instance = (InterviewConsult) getSession().get(
                "com.ccconsult.pojo.InterviewConsult", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding InterviewConsult instance with property: " + propertyName + ", value: "
                  + value);
        try {
            String queryString = "from InterviewConsult as model where model." + propertyName
                                 + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public InterviewConsult findByConsultId(Object consultId) {
        return getLimit(findByProperty(CONSULT_ID, consultId));
    }

}