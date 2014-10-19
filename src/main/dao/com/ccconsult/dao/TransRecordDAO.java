package com.ccconsult.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ccconsult.base.PageList;
import com.ccconsult.pojo.TransRecord;

/**
 	* A data access object (DAO) providing persistence and search support for TransRecord entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.ccconsult.pojo.TransRecord
  * @author MyEclipse Persistence Tools 
 */

public class TransRecordDAO extends BaseHibernateDAO<TransRecord> {
    private static final Logger log           = LoggerFactory.getLogger(TransRecordDAO.class);
    //property constants
    public static final String  MONEY         = "money";
    public static final String  TRANS_TYPE    = "transType";
    public static final String  REL_ROLE_ID   = "relRoleId";
    public static final String  REL_ROLE_TYPE = "relRoleType";
    public static final String  DETAIL        = "detail";
    public static final String  TRANS_TOKEN   = "transToken";
    public static final String  CONSULT_ID    = "consultId";
    public static final String  CHARGE_TYPE   = "chargeType";

    public TransRecord findById(java.lang.Integer id) {
        log.debug("getting TransRecord instance with id: " + id);
        try {
            TransRecord instance = (TransRecord) getSession().get("com.ccconsult.pojo.TransRecord",
                id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List<TransRecord> queryByConsultIdAndType(int consultId, int transType) {
        String hql = "from TransRecord where consultId=" + consultId + " and transType="
                     + transType;
        Query query = getSession().createQuery(hql);
        return query.list();
    }

    public PageList<TransRecord> queryPaged(int relRoleId, int relRoleType, int transType,
                                            int chargeType, int pageSize, int pageNo) {
        Map<String, Object> params = new HashMap<String, Object>();
        String hql = "from TransRecord where relRoleId=" + relRoleId + " ";
        params.put(REL_ROLE_TYPE, relRoleType);
        hql += " and relRoleType=:relRoleType ";
        if (transType > 0) {
            params.put(TRANS_TYPE, transType);
            hql += " and transType=:transType ";
        }
        if (chargeType > 0) {
            params.put(CHARGE_TYPE, chargeType);
            hql += " and chargeType=:chargeType ";
        }
        hql += " order by gmtCreate DESC ";
        return queryPage(pageNo, pageSize, hql, params);
    }

}