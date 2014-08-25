package com.ccconsult.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.ccconsult.base.enums.UserRoleEnum;
import com.ccconsult.pojo.Consultant;
import com.ccconsult.pojo.Message;
import com.ccconsult.web.view.CounselorVO;
import com.ccconsult.web.view.MessageVO;

/**
 	* A data access object (DAO) providing persistence and search support for Message entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.ccconsult.pojo.Message
  * @author MyEclipse Persistence Tools 
 */

public class MessageDAO extends BaseHibernateDAO<Message> {

    @Autowired
    private CounselorDAO       counselorDAO;
    @Autowired
    private ConsultantDAO      consultantDAO;
    //property constants
    public static final String MESSAGE      = "message";
    public static final String CREATOR_ROLE = "creatorRole";
    public static final String CREATOR      = "creator";
    public static final String REL_TYPE     = "relType";
    public static final String REL_ID       = "relId";

    public List<MessageVO> queryByRelInfo(int relId, int relType) {
        List<MessageVO> messagevos = new ArrayList<MessageVO>();
        String queryString = "from Message as model where model.relId=" + relId
                             + " and model.relType=" + relType + " order by gmtCreate desc";
        Query queryObject = getSession().createQuery(queryString);
        List<Message> messages = queryObject.list();
        if (CollectionUtils.isEmpty(messages)) {
            return messagevos;
        }
        for (Message message : messages) {
            MessageVO messageVO = new MessageVO();
            messageVO.setMessage(message);
            if (message.getCreatorRole() == UserRoleEnum.COUNSELOR.getValue()) {
                CounselorVO counselorVO = counselorDAO.findById(message.getCreator());
                messageVO.setCounselorVO(counselorVO);
            }
            if (message.getCreatorRole() == UserRoleEnum.CONSULTANT.getValue()) {
                Consultant consultant = consultantDAO.findById(message.getCreator());
                messageVO.setConsultant(consultant);
            }
            messagevos.add(messageVO);
        }
        return messagevos;
    }

    public List<Message> queryByRelInfoAndCreator(int relId, int relType, int creator,
                                                  int creatorRole) {
        String queryString = "from Message as model where model.relId=" + relId
                             + " and model.relType=" + relType + " and model.creator="
                             + creator + " and model.creatorRole=" + creatorRole
                             + " order by gmtCreate desc";
        Query queryObject = getSession().createQuery(queryString);
        return queryObject.list();
    }
}