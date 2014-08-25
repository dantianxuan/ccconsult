package com.ccconsult.pojo;

import org.hibernate.Session;


/**
 * Data access object (DAO) for domain model
 * @author MyEclipse Persistence Tools
 */
public class BaseHibernateDAO implements IBaseHibernateDAO {
	
	@Override
    public Session getSession() {
		return HibernateSessionFactory.getSession();
	}
	
}