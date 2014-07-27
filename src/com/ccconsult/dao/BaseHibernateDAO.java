package com.ccconsult.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.util.CollectionUtils;
import com.ccconsult.base.PageList;

/**
 * Data access object (DAO) for domain model
 * 
 * @author MyEclipse Persistence Tools
 */
public class BaseHibernateDAO<T> implements IBaseHibernateDAO {

    @Override
    public Session getSession() {
        return HibernateSessionFactory.getSession();
    }

    /**
     * 获取
     * 
     * @param list
     * @return
     */
    public T getLimit(List<T> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    public PageList<T> queryPage(int pageNo, int pageSize, String hql, Map map) {
        int count = getTotalCount(hql, map);
        List<T> list = findByQuery(pageNo, pageSize, hql, map);
        return new PageList<T>(pageSize * (pageNo - 1), count, pageSize, list);
    }

    /**
     * 查询服务   
     * 
     * @param pageNo
     * @param pageSize
     * @param hql
     * @param map
     * @return
     */
    public List<T> findByHql(String hql) {
        List<T> result = null;
        try {
            Query query = this.getSession().createQuery(hql);
            result = query.list();
        } catch (RuntimeException re) {
            throw re;
        }
        return result;
    }

    /**
     * 查询服务   
     * 
     * @param pageNo
     * @param pageSize
     * @param hql
     * @param map
     * @return
     */
    public List<T> findByQuery(int pageNo, int pageSize, String hql, Map map) {
        List<T> result = null;
        try {
            Query query = this.getSession().createQuery(hql);
            if (!CollectionUtils.isEmpty(map)) {
                Iterator it = map.keySet().iterator();
                while (it.hasNext()) {
                    Object key = it.next();
                    query.setParameter(key.toString(), map.get(key));
                }
            }
            query.setFirstResult((pageNo - 1) * pageSize);
            query.setMaxResults(pageSize);
            result = query.list();
        } catch (RuntimeException re) {
            throw re;
        }
        return result;
    }

    /**
     * 查询服务   
     * 
     * @param pageNo
     * @param pageSize
     * @param hql
     * @param map
     * @return
     */
    public List<T> findByQuery(String hql, Map map) {
        List<T> result = null;
        try {
            Query query = this.getSession().createQuery(hql);
            if (!CollectionUtils.isEmpty(map)) {
                Iterator it = map.keySet().iterator();
                while (it.hasNext()) {
                    Object key = it.next();
                    query.setParameter(key.toString(), map.get(key));
                }
            }
            result = query.list();
        } catch (RuntimeException re) {
            throw re;
        }
        return result;
    }

    /**
     * @function 根据查询条件查询记录数的个数
     * @param hql
     *            hql查询语句
     * @param map
     *            用map封装查询条件
     * @return 数据库中满足查询条件的数据的条数
     */
    public int getTotalCount(String hql, Map map) {
        try {
            hql = "SELECT count(*) " + hql;
            Query query = this.getSession().createQuery(hql);

            Iterator it = map.keySet().iterator();
            while (it.hasNext()) {
                Object key = it.next();
                query.setParameter(key.toString(), map.get(key));
            }

            Integer i = Integer.parseInt(query.list().get(0).toString());
            return i;
        } catch (RuntimeException re) {
            throw re;
        }

    }

    public void save(T transientInstance) {
        getSession().save(transientInstance);
    }

    public void update(T transientInstance) {
        getSession().update(transientInstance);
    }

    public void delete(T persistentInstance) {
        getSession().delete(persistentInstance);
    }

}