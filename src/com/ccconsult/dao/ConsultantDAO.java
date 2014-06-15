package com.ccconsult.dao;

import java.util.List;

import org.hibernate.Query;

import com.ccconsult.pojo.Consultant;

/**
 	* A data access object (DAO) providing persistence and search support for Consultant entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.ccconsult.pojo.Consultant
  * @author MyEclipse Persistence Tools 
 */

public class ConsultantDAO extends BaseHibernateDAO<Consultant> {
    //property constants
    public static final String NAME   = "name";
    public static final String PASSWD = "passwd";
    public static final String MOBILE = "mobile";
    public static final String EMAIL  = "email";
    public static final String PHOTO  = "photo";

    public Consultant findById(java.lang.Integer id) {
        Consultant instance = (Consultant) getSession().get("com.ccconsult.pojo.Consultant", id);
        return instance;
    }

    public List findByProperty(String propertyName, Object value) {
        String queryString = "from Consultant as model where model." + propertyName + "= ?";
        Query queryObject = getSession().createQuery(queryString);
        queryObject.setParameter(0, value);
        return queryObject.list();
    }

    public Consultant findByEmail(Object mail) {
        List list = findByProperty(EMAIL, mail);
        return getLimit(list);
    }

}