package com.ccconsult.dao;

import java.util.List;

import com.ccconsult.pojo.InnerMail;

/**
 	* A data access object (DAO) providing persistence and search support for InnerMail entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.ccconsult.pojo.InnerMail
  * @author MyEclipse Persistence Tools 
 */

public class InnerMailDAO extends BaseHibernateDAO<InnerMail> {
    //property constants
    public static final String CONTENT       = "content";
    public static final String SENDER_ID     = "senderId";
    public static final String SENDER_ROLE   = "senderRole";
    public static final String RECEIVER_ID   = "receiverId";
    public static final String RECEIVER_ROLE = "receiverRole";
    public static final String STATE         = "state";
    public static final String TYPE          = "type";

    public InnerMail findById(java.lang.Integer id) {
        InnerMail instance = (InnerMail) getSession().get("com.ccconsult.pojo.InnerMail", id);
        return instance;
    }

    public List<InnerMail> findByByReceiver(int receiverId, int receiverRole) {
        String hql = "from InnerMail  where receiverId=" + receiverId + " and receiverRole="
                     + receiverRole + " and state!=3 order by gmtCreate desc";
        return findByQuery(0, Integer.MAX_VALUE, hql, null);
    }

}