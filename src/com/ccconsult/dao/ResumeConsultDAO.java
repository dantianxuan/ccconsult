package com.ccconsult.dao;

import com.ccconsult.pojo.ResumeConsult;

import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 	* A data access object (DAO) providing persistence and search support for ResumeConsult entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.ccconsult.pojo.ResumeConsult
  * @author MyEclipse Persistence Tools 
 */

public class ResumeConsultDAO extends BaseHibernateDAO {
    private static final Logger log          = LoggerFactory.getLogger(ResumeConsultDAO.class);
    //property constants
    public static final String  RESUME_FILES = "resumeFiles";
    public static final String  REVIEW       = "review";
    public static final String  REVIEW_FILES = "reviewFiles";
    public static final String  CONSULT_ID   = "consultId";

    public void save(ResumeConsult transientInstance) {
        log.debug("saving ResumeConsult instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(ResumeConsult persistentInstance) {
        log.debug("deleting ResumeConsult instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public ResumeConsult findById(java.lang.Integer id) {
        log.debug("getting ResumeConsult instance with id: " + id);
        try {
            ResumeConsult instance = (ResumeConsult) getSession().get(
                "com.ccconsult.pojo.ResumeConsult", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(ResumeConsult instance) {
        log.debug("finding ResumeConsult instance by example");
        try {
            List results = getSession().createCriteria("com.ccconsult.pojo.ResumeConsult")
                .add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding ResumeConsult instance with property: " + propertyName + ", value: "
                  + value);
        try {
            String queryString = "from ResumeConsult as model where model." + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByResumeFiles(Object resumeFiles) {
        return findByProperty(RESUME_FILES, resumeFiles);
    }

    public List findByReview(Object review) {
        return findByProperty(REVIEW, review);
    }

    public List findByReviewFiles(Object reviewFiles) {
        return findByProperty(REVIEW_FILES, reviewFiles);
    }

    public List findByConsultId(Object consultId) {
        return findByProperty(CONSULT_ID, consultId);
    }

    public List findAll() {
        log.debug("finding all ResumeConsult instances");
        try {
            String queryString = "from ResumeConsult";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public ResumeConsult merge(ResumeConsult detachedInstance) {
        log.debug("merging ResumeConsult instance");
        try {
            ResumeConsult result = (ResumeConsult) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(ResumeConsult instance) {
        log.debug("attaching dirty ResumeConsult instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(ResumeConsult instance) {
        log.debug("attaching clean ResumeConsult instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}