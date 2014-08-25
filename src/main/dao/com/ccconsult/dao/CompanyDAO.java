package com.ccconsult.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ccconsult.base.PageList;
import com.ccconsult.base.util.StringUtil;
import com.ccconsult.pojo.Company;

/**
 	* A data access object (DAO) providing persistence and search support for Company entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.ccconsult.pojo.Company
  * @author MyEclipse Persistence Tools 
 */

public class CompanyDAO extends BaseHibernateDAO<Company> {

    private static final Logger log             = LoggerFactory.getLogger(CompanyDAO.class);

    //property constants
    public static final String  NAME            = "name";
    public static final String  DESCRIPTION     = "description";
    public static final String  LINK            = "link";
    public static final String  MAIL_SUFFIX     = "mailSuffix";
    public static final String  PHOTO           = "photo";
    public static final String  COUNSELOR_COUNT = "counselorCount";
    public static final String  REG_MOBILE      = "regMobile";
    public static final String  STATE           = "state";

    public Company findById(java.lang.Integer id) {
        log.debug("getting Company instance with id: " + id);
        try {
            Company instance = (Company) getSession().get("com.ccconsult.pojo.Company", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public PageList<Company> queryByName(int pageNo, int pageSize, String name) {
        Map map = new HashMap<String, Object>();
        String hql = "";
        if (StringUtil.isBlank(name)) {
            hql = "from Company  order by gmtCreate desc";
        } else {
            map.put(NAME, "%" + name + "%");
            hql = "from Company where state=1 and  name like :name order by gmtCreate desc";
        }
        return queryPage(pageNo, pageSize, hql, map);
    }

    public Company findByMailSuffix(Object mailSuffix) {
        return getLimit(findByProperty(MAIL_SUFFIX, mailSuffix));
    }

    public Company findByName(Object name) {
        return getLimit(findByProperty(NAME, name));
    }

    public List findAll() {
        log.debug("finding all Company instances");
        try {
            String queryString = "from Company where state=1  ";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding Company instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from Company as model where model." + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List<Company> queryTopList(int size) {
        String hql = "from Company where state=1   order by counselorCount desc limit 0," + size;
        Query query = this.getSession().createQuery(hql);
        List<Company> companys = query.list();
        return companys;
    }

}