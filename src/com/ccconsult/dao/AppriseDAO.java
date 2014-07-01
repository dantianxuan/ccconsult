package com.ccconsult.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ccconsult.pojo.Apprise;

/**
 	* A data access object (DAO) providing persistence and search support for Apprise entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.ccconsult.pojo.Apprise
  * @author MyEclipse Persistence Tools 
 */

public class AppriseDAO extends BaseHibernateDAO<Apprise> {
    private static final Logger log          = LoggerFactory.getLogger(AppriseDAO.class);
    //property constants
    public static final String  APPRISE      = "apprise";
    public static final String  CREATOR      = "creator";
    public static final String  MEMO         = "memo";
    public static final String  REL_TYPE     = "relType";
    public static final String  REL_ID       = "relId";
    public static final String  CREATOR_ROLE = "creatorRole";

    public Apprise findById(java.lang.Integer id) {
        log.debug("getting Apprise instance with id: " + id);
        try {
            Apprise instance = (Apprise) getSession().get("com.ccconsult.pojo.Apprise", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List<Apprise> findByRelId(int relId, int relType) {
        String hql = "from Apprise  where relId=" + relId + " and relType=" + relType
                     + "order by gmtCreate desc";
        return findPageByQuery(0, Integer.MAX_VALUE, hql, null);
    }

    public Apprise findByRelId(int relId, int relType, int creatorId, int creatorRole) {
        String hql = "from Apprise  where relId=" + relId + " and relType=" + relType
                     + " and creator=" + creatorId + " and creatorRole=" + creatorRole
                     + "order by gmtCreate desc";
        return getLimit(findPageByQuery(0, Integer.MAX_VALUE, hql, null));
    }

}