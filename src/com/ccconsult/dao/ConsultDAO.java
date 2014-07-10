package com.ccconsult.dao;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ccconsult.base.PageList;
import com.ccconsult.pojo.Consult;

/**
 	* A data access object (DAO) providing persistence and search support for Consult entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.ccconsult.pojo.Consult
  * @author MyEclipse Persistence Tools 
 */

public class ConsultDAO extends BaseHibernateDAO<Consult> {
    private static final Logger log           = LoggerFactory.getLogger(ConsultDAO.class);
    //property constants
    public static final String  CONSULTANT_ID = "consultantId";
    public static final String  COUNSELOR_ID  = "counselorId";
    public static final String  STEP          = "step";
    public static final String  ORDER_ID      = "orderId";
    public static final String  STATE         = "state";
    public static final String  SERVICE_ID    = "serviceId";

    public Consult findById(java.lang.Integer id) {
        log.debug("getting Consult instance with id: " + id);
        try {
            Consult instance = (Consult) getSession().get("com.ccconsult.pojo.Consult", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public PageList<Consult> queryUnderStepPaged(int step, int serviceId, int counselorId,
                                                 int consultantId, int pageSize, int pageNo) {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put(STEP, step);
        String hql = "from Consult where step<:step ";
        if (serviceId > 0) {
            params.put(SERVICE_ID, serviceId);
            hql += " and serviceId=:serviceId ";
        }
        if (counselorId > 0) {
            params.put(COUNSELOR_ID, counselorId);
            hql += " and counselorId=:counselorId ";
        }
        if (consultantId > 0) {
            params.put(CONSULTANT_ID, consultantId);
            hql += " and consultantId=:consultantId ";
        }
        hql += " and payTag=2 order by gmtModified ";
        return queryPage(pageNo, pageSize, hql, params);
    }

    public PageList<Consult> queryPaged(int step, int serviceId, int counselorId, int consultantId,
                                        int pageSize, int pageNo) {
        Map<String, Object> params = new HashMap<String, Object>();

        params.put(STEP, step);
        String hql = "from Consult where step=:step  ";
        if (serviceId > 0) {
            params.put(SERVICE_ID, serviceId);
            hql += " and serviceId=:serviceId ";
        }
        if (counselorId > 0) {
            params.put(COUNSELOR_ID, counselorId);
            hql += " and counselorId=:counselorId ";
        }
        if (consultantId > 0) {
            params.put(CONSULTANT_ID, consultantId);
            hql += " and consultantId=:consultantId ";
        }

        hql += " and payTag=2 order by gmtModified ";
        return queryPage(pageNo, pageSize, hql, params);
    }
}