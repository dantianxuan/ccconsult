package com.ccconsult.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.ccconsult.base.PageList;
import com.ccconsult.pojo.Company;
import com.ccconsult.util.StringUtil;
import com.ccconsult.view.CompanyBriefVO;

/**
 	* A data access object (DAO) providing persistence and search support for Company entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.ccconsult.pojo.Company
  * @author MyEclipse Persistence Tools 
 */

public class CompanyDAO extends BaseHibernateDAO<Company> {

    private static final Logger log         = LoggerFactory.getLogger(CompanyDAO.class);

    @Autowired
    private CounselorDAO        counselorDAO;

    //property constants
    public static final String  NAME        = "name";
    public static final String  DESCRIPTION = "description";
    public static final String  LINK        = "link";
    public static final String  MAIL_SUFFIX = "mailSuffix";
    public static final String  PHOTO       = "photo";
    public static final String  TOP_TAG     = "topTag";

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

    public PageList<CompanyBriefVO> queryByName(int pageNo, int pageSize, String name) {
        Map map = new HashMap<String, Object>();
        String hql = "";
        if (StringUtil.isBlank(name)) {
            hql = "from Company  order by gmtCreate desc";
        } else {
            map.put(NAME, "%" + name + "%");
            hql = "from Company where name like :name order by gmtCreate desc";
        }
        PageList result = queryPage(pageNo, pageSize, hql, map);
        if (CollectionUtils.isEmpty(result.getResult())) {
            return result;
        }
        List<CompanyBriefVO> briefs = new ArrayList<CompanyBriefVO>();
        for (Object object : result.getResult()) {
            briefs.add(consBrief((Company) object));
        }
        result.setData(briefs);
        return result;
    }

    public List findAll() {
        log.debug("finding all Company instances");
        try {
            String queryString = "from Company";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public List<CompanyBriefVO> queryTopList(int size) {
        String hql = "from Company  order by topTag desc limit 0," + "size";
        Query query = this.getSession().createQuery(hql);
        List<Company> companys = query.list();
        List<CompanyBriefVO> companyBriefs = new ArrayList<CompanyBriefVO>();
        if (CollectionUtils.isEmpty(companys)) {
            return companyBriefs;
        }
        for (Company company : companys) {
            companyBriefs.add(consBrief(company));
        }
        return companyBriefs;
    }

    private CompanyBriefVO consBrief(Company company) {
        CompanyBriefVO brif = new CompanyBriefVO();
        brif.setCompany(company);
        brif.setCounselorCount(counselorDAO.queryCount(company.getId()));
        return brif;
    }
}