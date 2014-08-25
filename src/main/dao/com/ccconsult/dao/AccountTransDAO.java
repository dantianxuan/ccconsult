package com.ccconsult.dao;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ccconsult.base.PageList;
import com.ccconsult.pojo.AccountTrans;

/**
 	* A data access object (DAO) providing persistence and search support for AccountTrans entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.ccconsult.pojo.AccountTrans
  * @author MyEclipse Persistence Tools 
 */

public class AccountTransDAO extends BaseHibernateDAO<AccountTrans> {
    private static final Logger log           = LoggerFactory.getLogger(AccountTransDAO.class);
    //property constants
    public static final String  MONEY         = "money";
    public static final String  TRANS_TYPE    = "transType";
    public static final String  REL_ROLE_ID   = "relRoleId";
    public static final String  REL_ROLE_TYPE = "relRoleType";
    public static final String  DETAIL        = "detail";
    public static final String  TRANS_TOKEN   = "transToken";
    public static final String  CONSULT_ID    = "consultId";

    @Override
    public void save(AccountTrans transientInstance) {
        log.debug("saving AccountTrans instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public PageList<AccountTrans> queryPaged(int relRoleId, int relRoleType, int transType,
                                             int counsultId, int pageSize, int pageNo) {
        String hql = "from AccountTrans where ";
        Map<String, Object> params = new HashMap<String, Object>();
        if (relRoleId > 0) {
            params.put(REL_ROLE_ID, relRoleId);
            hql += " relRoleId=:relRoleId";
        }
        if (relRoleType > 0) {
            params.put(REL_ROLE_TYPE, relRoleType);
            hql += " and relRoleType=:relRoleType";
        }
        if (transType > 0) {
            params.put(TRANS_TYPE, transType);
            hql += " and transType=:transType";
        }
        if (counsultId > 0) {
            params.put(CONSULT_ID, counsultId);
            hql += " and counsultId=:counsultId";
        }
        hql += " order by gmtCreate desc";
        return queryPage(pageNo, pageSize, hql, params);
    }

    public AccountTrans findById(java.lang.Integer id) {
        log.debug("getting AccountTrans instance with id: " + id);
        try {
            AccountTrans instance = (AccountTrans) getSession().get(
                "com.ccconsult.pojo.AccountTrans", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

}