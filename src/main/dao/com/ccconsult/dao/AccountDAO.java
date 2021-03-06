package com.ccconsult.dao;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ccconsult.pojo.Account;

/**
 	* A data access object (DAO) providing persistence and search support for Account entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.ccconsult.pojo.Account
  * @author MyEclipse Persistence Tools 
 */

public class AccountDAO extends BaseHibernateDAO<Account> {
    private static final Logger log             = LoggerFactory.getLogger(AccountDAO.class);
    //property constants
    public static final String  REL_ROLE_ID     = "relRoleId";
    public static final String  REL_ROLE_TYPE   = "relRoleType";
    public static final String  IN_ALL_MONEY    = "inAllMoney";
    public static final String  CURRENT_MONEY   = "currentMoney";
    public static final String  FREEZING_MONEY  = "freezingMoney";
    public static final String  TRANS_ALL_MONEY = "transAllMoney";

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

    public Account queryByRole(int roleId, int roleType) {
        String hqlStr = "from Account as account where account.relRoleId=" + roleId
                        + " and roleType=" + roleType;
        Query query = getSession().createQuery(hqlStr);
        return getLimit(query.list());
    }

    public Account queryByRoleForUpdate(int roleId, int roleType) {
        String hqlStr = "from Account as account where account.relRoleId=" + roleId
                        + " and relRoleType=" + roleType;
        Query query = getSession().createQuery(hqlStr);
        query.setLockMode("account", LockMode.PESSIMISTIC_WRITE);
        return getLimit(query.list());
    }

}