package com.ccconsult.pojo;

import com.ccconsult.dao.BaseHibernateDAO;
import java.util.Date;
import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 	* A data access object (DAO) providing persistence and search support for Resume entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.ccconsult.pojo.Resume
  * @author MyEclipse Persistence Tools 
 */

public class ResumeDAO extends BaseHibernateDAO {
    private static final Logger log             = LoggerFactory.getLogger(ResumeDAO.class);
    //property constants
    public static final String  REAL_NAME       = "realName";
    public static final String  SEXY            = "sexy";
    public static final String  EDUCATION       = "education";
    public static final String  RESUME          = "resume";
    public static final String  CONSULTANT_ID   = "consultantId";
    public static final String  MOBILE          = "mobile";
    public static final String  EMAIL           = "email";
    public static final String  WORK_EXPERIENCE = "workExperience";
    public static final String  SCHOOL          = "school";
    public static final String  PROFESSION      = "profession";

    public Resume findById(java.lang.Integer id) {
        log.debug("getting Resume instance with id: " + id);
        try {
            Resume instance = (Resume) getSession().get("com.ccconsult.pojo.Resume", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

}