package com.ccconsult.dao;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ccconsult.pojo.Service;
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
    private static final Logger log               = LoggerFactory.getLogger(ServiceConfigDAO.class);
    @Autowired
    private ServiceDAO          serviceDAO;
    //property constants
    public static final String  COUNSELOR_ID      = "counselorId";
    public static final String  SERVICE_ID        = "serviceId";
    public static final String  STATE             = "state";
    public static final String  WORK_ON_TIME      = "workOnTime";

    public ServiceConfig findById(java.lang.Integer id) {
        ServiceConfig instance = (ServiceConfig) getSession().get(
            "com.ccconsult.pojo.ServiceConfig", id);
        if (instance == null) {
            return null;
        }
        return instance;
    }

    public List<ServiceConfig> findByCounselorIdAndServiceId(int counselorId, int serviceId) {
        String queryString = "from ServiceConfig where counselorId=" + counselorId
                             + "  and serviceId=" + serviceId + " and state=1 order by serviceId ";
        Query queryObject = getSession().createQuery(queryString);
        return queryObject.list();
    }

    public ServiceConfigVO findVoById(java.lang.Integer id) {
        ServiceConfig instance = (ServiceConfig) getSession().get(
            "com.ccconsult.pojo.ServiceConfig", id);
        if (instance == null) {
            return null;
        }
        ServiceConfigVO serviceConfigVO = new ServiceConfigVO();
        serviceConfigVO.setServiceConfig(instance);
        Service service = serviceDAO.findById(instance.getServiceId());
        serviceConfigVO.setService(service);
        return serviceConfigVO;
    }

    public List<ServiceConfig> findByCounselorId(java.lang.Integer counselorId) {
        String queryString = "from ServiceConfig where counselorId=" + counselorId
                             + " and state=1 order by serviceId ";
        Query queryObject = getSession().createQuery(queryString);
        return queryObject.list();
    }

}