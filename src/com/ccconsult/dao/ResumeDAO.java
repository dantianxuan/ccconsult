package com.ccconsult.dao;

import com.ccconsult.pojo.Resume;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 	* A data access object (DAO) providing persistence and search support for Resume entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.ccconsult.pojo.Resume
  * @author MyEclipse Persistence Tools 
 */

public class ResumeDAO extends BaseHibernateDAO<Resume> {
    private static final Logger log             = LoggerFactory.getLogger(ResumeDAO.class);
    //property constants
    public static final String  REAL_NAME       = "realName";
    public static final String  SEXY            = "sexy";
    public static final String  EDUCATION       = "education";
    public static final String  RESUME          = "resume";
    public static final String  CONSULTANT_ID   = "consultantId";
    public static final String  MOBILE          = "mobile";
    public static final String  EMAIL           = "email";
    public static final String  WORK_EXPERIENCE = "workExperience";

    @Override
    public void save(Resume transientInstance) {
        log.debug("saving Resume instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    @Override
    public void delete(Resume persistentInstance) {
        log.debug("deleting Resume instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public Resume findById(java.lang.Integer id) {
        log.debug("getting Resume instance with id: " + id);
        try {
            Resume instance = (Resume) getSession().get("com.ccconsult.pojo.Resume", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(Resume instance) {
        log.debug("finding Resume instance by example");
        try {
            List results = getSession().createCriteria("com.ccconsult.pojo.Resume")
                .add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding Resume instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from Resume as model where model." + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByRealName(Object realName) {
        return findByProperty(REAL_NAME, realName);
    }

    public List findBySexy(Object sexy) {
        return findByProperty(SEXY, sexy);
    }

    public List findByEducation(Object education) {
        return findByProperty(EDUCATION, education);
    }

    public List findByResume(Object resume) {
        return findByProperty(RESUME, resume);
    }

    public Resume findByConsultantId(Object consultantId) {
        return getLimit(findByProperty(CONSULTANT_ID, consultantId));
    }

    public List findByMobile(Object mobile) {
        return findByProperty(MOBILE, mobile);
    }

    public List findByEmail(Object email) {
        return findByProperty(EMAIL, email);
    }

    public List findByWorkExperience(Object workExperience) {
        return findByProperty(WORK_EXPERIENCE, workExperience);
    }

}