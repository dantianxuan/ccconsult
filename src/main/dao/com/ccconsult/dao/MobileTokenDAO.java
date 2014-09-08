package com.ccconsult.dao;

import java.util.Date;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ccconsult.base.util.DateUtil;
import com.ccconsult.pojo.MobileToken;

/**
 	* A data access object (DAO) providing persistence and search support for MobileToken entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.ccconsult.pojo.MobileToken
  * @author MyEclipse Persistence Tools 
 */

public class MobileTokenDAO extends BaseHibernateDAO<MobileToken> {
    private static final Logger log        = LoggerFactory.getLogger(MobileTokenDAO.class);
    //property constants
    public static final String  TOKEN      = "token";
    public static final String  TOKEN_TYPE = "tokenType";
    public static final String  PARAMS     = "params";
    public static final String  SEND_TIMES = "sendTimes";
    public static final String  MOBILE     = "mobile";

    public MobileToken findById(java.lang.Integer id) {
        log.debug("getting MobileToken instance with id: " + id);
        try {
            MobileToken instance = (MobileToken) getSession().get("com.ccconsult.pojo.MobileToken",
                id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public MobileToken getByTypeAndMobile(int type, String mobile, int recentHoure) {

        String beginTime = DateUtil.format(DateUtil.addHours(new Date(), recentHoure),
            DateUtil.newFormat);
        String currentTime = DateUtil.format(new Date(), DateUtil.newFormat);

        String hql = "from MobileToken where tokenType=? and mobile=? and  gmtCreate<='"
                     + currentTime + "' and  gmtCreate>='" + beginTime
                     + "' order by gmtModified desc";
        Query queryObject = getSession().createQuery(hql);
        queryObject.setParameter(0, type);
        queryObject.setParameter(1, mobile);
        return getLimit(queryObject.list());
    }
}