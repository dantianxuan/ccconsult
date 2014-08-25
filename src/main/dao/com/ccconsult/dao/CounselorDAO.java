package com.ccconsult.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.ccconsult.base.PageList;
import com.ccconsult.base.util.StringUtil;
import com.ccconsult.pojo.Company;
import com.ccconsult.pojo.Counselor;
import com.ccconsult.pojo.Level;
import com.ccconsult.pojo.ServiceConfig;
import com.ccconsult.web.view.CounselorVO;

/**
 	* A data access object (DAO) providing persistence and search support for Counselor entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.ccconsult.pojo.Counselor
  * @author MyEclipse Persistence Tools 
 */

public class CounselorDAO extends BaseHibernateDAO<Counselor> {
    @Autowired
    private CompanyDAO         companyDAO;
    @Autowired
    private ServiceConfigDAO   serviceConfigDAO;
    @Autowired
    private LevelDAO           levelDAO;
    //property constants
    public static final String NAME          = "name";
    public static final String EMAIL         = "email";
    public static final String MOBILE        = "mobile";
    public static final String DESCRIPTION   = "description";
    public static final String COMPANY_ID    = "companyId";
    public static final String PHOTO         = "photo";
    public static final String PASSWD        = "passwd";
    public static final String DEPARTMENT    = "department";
    public static final String APPRISE_RATE  = "appriseRate";
    public static final String CITY          = "city";
    public static final String CONSULT_COUNT = "consultCount";
    public static final String APPRISE_COUNT = "appriseCount";
    public static final String LEVEL_ID      = "levelId";

    public Counselor getById(java.lang.Integer id) {
        return (Counselor) getSession().get("com.ccconsult.pojo.Counselor", id);
    }

    public Map<Integer, Counselor> getByIds(List<Integer> ids) {
        Map<Integer, Counselor> counselorMap = new HashMap<Integer, Counselor>();
        if (CollectionUtils.isEmpty(ids)) {
            return counselorMap;
        }
        String hql = "  from Counselor where id  in  (:ids)";
        Query queryObject = getSession().createQuery(hql);
        queryObject.setParameterList("ids", ids);
        List<Counselor> counselors = queryObject.list();
        if (CollectionUtils.isEmpty(counselors)) {
            return counselorMap;
        }
        for (Counselor counselor : counselors) {
            counselorMap.put(counselor.getId(), counselor);
        }
        return counselorMap;
    }

    public CounselorVO findById(java.lang.Integer id) {
        Counselor instance = (Counselor) getSession().get("com.ccconsult.pojo.Counselor", id);
        if (instance == null) {
            return null;
        }
        return consVO(instance);
    }

    @SuppressWarnings("unchecked")
    public List<Counselor> findByProperty(String propertyName, Object value) {
        String queryString = "from Counselor as model where model." + propertyName + "= ?";
        Query queryObject = getSession().createQuery(queryString);
        queryObject.setParameter(0, value);
        return queryObject.list();
    }

    public PageList<Counselor> queryByName(int pageNo, int pageSize, String name) {
        Map map = new HashMap<String, Object>();
        String hql = "";
        if (StringUtil.isBlank(name)) {
            hql = "from Counselor  order by gmtCreate desc";
        } else {
            map.put(NAME, name);
            hql = "from Counselor where name like :name order by gmtCreate desc";
        }
        return queryPage(pageNo, pageSize, hql, map);
    }

    public int queryCount(int companyId) {
        Query queryObject = getSession().createQuery(
            "select count(*) from Counselor where companyId=" + companyId);
        return ((Long) queryObject.uniqueResult()).intValue();
    }

    public List<Counselor> findByCompanyId(int companyId) {
        return findByProperty(COMPANY_ID, companyId);

    }

    public CounselorVO findByEmail(Object email) {
        List<Counselor> counselors = findByProperty(EMAIL, email);
        Counselor counselor = getLimit(counselors);
        if (counselor == null) {
            return null;
        }
        return consVO(counselor);
    }

    private CounselorVO consVO(Counselor counselor) {
        if (counselor == null) {
            return null;
        }
        Company company = companyDAO.findById(counselor.getCompanyId());
        List<ServiceConfig> serviceConfigs = serviceConfigDAO.findByCounselorId(counselor.getId());
        Level level = levelDAO.findById(counselor.getLevelId());
        return new CounselorVO(counselor, company, serviceConfigs);

    }

}