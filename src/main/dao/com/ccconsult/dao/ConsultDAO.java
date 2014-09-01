package com.ccconsult.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ccconsult.base.PageList;
import com.ccconsult.base.enums.ConsultStepEnum;
import com.ccconsult.base.util.DateUtil;
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
    private static final Logger log               = LoggerFactory.getLogger(ConsultDAO.class);
    //property constants
    public static final String  CONSULTANT_ID     = "consultantId";
    public static final String  COUNSELOR_ID      = "counselorId";
    public static final String  STEP              = "step";
    public static final String  PAY_TAG           = "payTag";
    public static final String  SERVICE_ID        = "serviceId";
    public static final String  GOAL              = "goal";
    public static final String  REJECT_REASON     = "rejectReason";
    public static final String  SERVICE_CONFIG_ID = "serviceConfigId";
    public static final String  PRICE             = "price";

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

    public PageList<Consult> queryInnerConsultByGoal(String keyWord, int pageSize, int pageNo) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(GOAL, "%" + keyWord + "%");
        String hql = "from Consult where step=" + ConsultStepEnum.FIHSHED.getValue()
                     + " and goal like :goal";
        hql += " order by gmtModified ";
        return queryPage(pageNo, pageSize, hql, params);
    }

    public PageList<Consult> queryUnderStepPaged(int payTag, int step, int serviceId,
                                                 int counselorId, int consultantId, int pageSize,
                                                 int pageNo) {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put(STEP, step);
        String hql = "from Consult where step<:step ";
        if (serviceId > 0) {
            params.put(SERVICE_ID, serviceId);
            hql += " and serviceId=:serviceId ";
        }
        if (payTag > 0) {
            params.put(PAY_TAG, payTag);
            hql += " and payTag=:payTag ";
        }
        if (counselorId > 0) {
            params.put(COUNSELOR_ID, counselorId);
            hql += " and counselorId=:counselorId ";
        }
        if (consultantId > 0) {
            params.put(CONSULTANT_ID, consultantId);
            hql += " and consultantId=:consultantId ";
        }
        hql += " order by gmtModified ";
        return queryPage(pageNo, pageSize, hql, params);
    }

    public List<Consult> queryToday() {
        String today = DateUtil.format(new Date(), DateUtil.webFormat);
        String end = DateUtil.format(DateUtils.addDays(new Date(), 1), DateUtil.webFormat);
        String hql = "from Consult where step=2 and  gmtEffectBegin>='" + today
                     + "' order by gmtEffectBegin";
        Query query = this.getSession().createQuery(hql);
        return query.list();
    }

    public PageList<Consult> queryPaged(int payTag, int step, int serviceId, int counselorId,
                                        int consultantId, int pageSize, int pageNo) {
        Map<String, Object> params = new HashMap<String, Object>();

        params.put(STEP, step);
        String hql = "from Consult where step=:step  ";
        if (serviceId > 0) {
            params.put(SERVICE_ID, serviceId);
            hql += " and serviceId=:serviceId ";
        }
        if (payTag > 0) {
            params.put(PAY_TAG, payTag);
            hql += " and payTag=:payTag ";
        }
        if (counselorId > 0) {
            params.put(COUNSELOR_ID, counselorId);
            hql += " and counselorId=:counselorId ";
        }
        if (consultantId > 0) {
            params.put(CONSULTANT_ID, consultantId);
            hql += " and consultantId=:consultantId ";
        }

        hql += " order by gmtModified ";
        return queryPage(pageNo, pageSize, hql, params);
    }
}