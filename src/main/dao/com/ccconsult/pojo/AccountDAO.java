package com.ccconsult.pojo;

import com.ccconsult.dao.BaseHibernateDAO;
import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 	* A data access object (DAO) providing persistence and search support for Account entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.ccconsult.pojo.Account
  * @author MyEclipse Persistence Tools 
 */

public class AccountDAO extends BaseHibernateDAO {
    private static final Logger log             = LoggerFactory.getLogger(AccountDAO.class);
    //property constants
    public static final String  REL_ROLE_ID     = "relRoleId";
    public static final String  REL_ROLE_TYPE   = "relRoleType";
    public static final String  IN_ALL_MONEY    = "inAllMoney";
    public static final String  CURRENT_MONEY   = "currentMoney";
    public static final String  FREEZING_MONEY  = "freezingMoney";
    public static final String  TRANS_ALL_MONEY = "transAllMoney";

    public void save(Account transientInstance) {
        log.debug("saving Account instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(Account persistentInstance) {
        log.debug("deleting Account instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public Account findById(java.lang.Integer id) {
        log.debug("getting Account instance with id: " + id);
        try {
            Account instance = (Account) getSession().get("com.ccconsult.pojo.Account", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(Account instance) {
        log.debug("finding Account instance by example");
        try {
            List results = getSession().createCriteria("com.ccconsult.pojo.Account")
                .add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding Account instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from Account as model where model." + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByRelRoleId(Object relRoleId) {
        return findByProperty(REL_ROLE_ID, relRoleId);
    }

    public List findByRelRoleType(Object relRoleType) {
        return findByProperty(REL_ROLE_TYPE, relRoleType);
    }

    public List findByInAllMoney(Object inAllMoney) {
        return findByProperty(IN_ALL_MONEY, inAllMoney);
    }

    public List findByCurrentMoney(Object currentMoney) {
        return findByProperty(CURRENT_MONEY, currentMoney);
    }

    public List findByFreezingMoney(Object freezingMoney) {
        return findByProperty(FREEZING_MONEY, freezingMoney);
    }

    public List findByTransAllMoney(Object transAllMoney) {
        return findByProperty(TRANS_ALL_MONEY, transAllMoney);
    }

    public List findAll() {
        log.debug("finding all Account instances");
        try {
            String queryString = "from Account";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public Account merge(Account detachedInstance) {
        log.debug("merging Account instance");
        try {
            Account result = (Account) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Account instance) {
        log.debug("attaching dirty Account instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(Account instance) {
        log.debug("attaching clean Account instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}