package com.ccconsult.dao;

import com.ccconsult.pojo.Service;

import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 	* A data access object (DAO) providing persistence and search support for Service entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.ccconsult.pojo.Service
  * @author MyEclipse Persistence Tools 
 */

public class ServiceDAO extends BaseHibernateDAO {
    private static final Logger log              = LoggerFactory.getLogger(ServiceDAO.class);
    //property constants
    public static final String  NAME             = "name";
    public static final String  DESCRIPTION      = "description";
    public static final String  PRICE_REGION     = "priceRegion";
    public static final String  GMT_CREATE       = "gmtCreate";
    public static final String  GMT_MODIFIED     = "gmtModified";
    public static final String  STATE            = "state";
    public static final String  INTRO_ARTICLE_ID = "introArticleId";
    public static final String  CODE             = "code";
    public static final String  EFFECT_TIME      = "effectTime";
    public static final String  PHOTO            = "photo";
    public static final String  SCHEDULE_TYPE    = "scheduleType";

    public void save(Service transientInstance) {
        log.debug("saving Service instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(Service persistentInstance) {
        log.debug("deleting Service instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

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

    public List findByExample(Service instance) {
        log.debug("finding Service instance by example");
        try {
            List results = getSession().createCriteria("com.ccconsult.pojo.Service")
                .add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
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

    public List findByGmtCreate(Object gmtCreate) {
        return findByProperty(GMT_CREATE, gmtCreate);
    }

    public List findByGmtModified(Object gmtModified) {
        return findByProperty(GMT_MODIFIED, gmtModified);
    }

    public List findByState(Object state) {
        return findByProperty(STATE, state);
    }

    public List findByIntroArticleId(Object introArticleId) {
        return findByProperty(INTRO_ARTICLE_ID, introArticleId);
    }

    public List findByCode(Object code) {
        return findByProperty(CODE, code);
    }

    public List findByEffectTime(Object effectTime) {
        return findByProperty(EFFECT_TIME, effectTime);
    }

    public List findByPhoto(Object photo) {
        return findByProperty(PHOTO, photo);
    }

    public List findByScheduleType(Object scheduleType) {
        return findByProperty(SCHEDULE_TYPE, scheduleType);
    }

    public List findAll() {
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

    public Service merge(Service detachedInstance) {
        log.debug("merging Service instance");
        try {
            Service result = (Service) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Service instance) {
        log.debug("attaching dirty Service instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(Service instance) {
        log.debug("attaching clean Service instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}