package com.ccconsult.dao;

import java.util.HashMap;
import java.util.Map;

import com.ccconsult.base.PageList;
import com.ccconsult.pojo.Comment;

/**
 	* A data access object (DAO) providing persistence and search support for Comment entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.ccconsult.pojo.Comment
  * @author MyEclipse Persistence Tools 
 */

public class CommentDAO extends BaseHibernateDAO<Comment> {
    public static final String  CONTENT      = "content";
    public static final String  CREATOR_ID   = "creatorId";
    public static final String  CREATOR_ROLE = "creatorRole";
    public static final String  REL_ID       = "relId";
    public static final String  REL_TYPE     = "relType";
    public static final String  REL_INFO     = "relInfo";

    public PageList<Comment> queryByThemeIdAndType(int relId, int relType, int pageNo, int pageSize) {
        Map map = new HashMap<String, Object>();
        map.put(REL_ID, relId);
        map.put(REL_TYPE, relType);
        String hql = "from Comment where relId=:relId  and relType=:relType order by gmtCreate desc";
        return queryPage(pageNo, pageSize, hql, map);
    }
}