package com.ccconsult.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.ccconsult.pojo.ServiceConfig;
import com.ccconsult.view.ServiceConfigVO;

/**
 	* A data access object (DAO) providing persistence and search support for ServiceConfig entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.ccconsult.pojo.ServiceConfig
  * @author MyEclipse Persistence Tools 
 */

public class ServiceConfigDAO extends BaseHibernateDAO<ServiceConfig> {
    private static final Logger log          = LoggerFactory.getLogger(ServiceConfigDAO.class);

    @Autowired
    private ServiceDAO          serviceDAO;
    //property constants
    public static final String  COUNSELOR_ID = "counselorId";
    public static final String  SERVICE_ID   = "serviceId";
    public static final String  PRICE        = "price";
    public static final String  GMT_CREATE   = "gmtCreate";
    public static final String  STATE        = "state";

    public ServiceConfig findById(java.lang.Integer id) {
        log.debug("getting ServiceConfig instance with id: " + id);
        try {
            ServiceConfig instance = (ServiceConfig) getSession().get(
                "com.ccconsult.pojo.ServiceConfig", id);
            return instance;

        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public ServiceConfigVO findVoById(java.lang.Integer id) {
        log.debug("getting ServiceConfig instance with id: " + id);
        try {
            ServiceConfig instance = (ServiceConfig) getSession().get(
                "com.ccconsult.pojo.ServiceConfig", id);
            if (instance == null) {
                return null;
            }
            return consConfigVO(instance);

        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List<ServiceConfigVO> findByProperty(String propertyName, Object value) {
        log.debug("finding ServiceConfig instance with property: " + propertyName + ", value: "
                  + value);
        try {
            String queryString = "from ServiceConfig as model where model." + propertyName
                                 + "= ? and state!=3 order by serviceId";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            List result = queryObject.list();
            if (CollectionUtils.isEmpty(result)) {
                return result;
            }
            List<ServiceConfigVO> serviceConfigs = new ArrayList<ServiceConfigVO>();
            for (Object object : result) {
                serviceConfigs.add(consConfigVO((ServiceConfig) object));
            }
            return serviceConfigs;
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List<ServiceConfigVO> findByCounselorId(int counselorId) {
        return findByProperty(COUNSELOR_ID, counselorId);
    }

    public List<ServiceConfig> findByCounselorIdAndServiceId(int counselorId, int serviceId) {
        String hql = "from ServiceConfig as model where model.counselorId=" + counselorId
                     + "and  model.serviceId=" + serviceId + " and state!=3";
        return findByQuery(hql, null);
    }

    private ServiceConfigVO consConfigVO(ServiceConfig serviceConfig) {
        ServiceConfigVO serviceConfigVO = new ServiceConfigVO();
        serviceConfigVO.setServiceConfig(serviceConfig);
        serviceConfigVO.setService(serviceDAO.findById(serviceConfig.getServiceId()));
        return serviceConfigVO;
    }

}