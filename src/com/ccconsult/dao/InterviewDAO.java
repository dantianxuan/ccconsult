package com.ccconsult.dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.ccconsult.enums.DataStateEnum;
import com.ccconsult.enums.InterviewStepEnum;
import com.ccconsult.pojo.Interview;
import com.ccconsult.view.InterviewVO;

/**
 	* A data access object (DAO) providing persistence and search support for Interview entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.ccconsult.pojo.Interview
  * @author MyEclipse Persistence Tools 
 */

public class InterviewDAO extends BaseHibernateDAO<Interview> {
    private static final Logger log           = LoggerFactory.getLogger(InterviewDAO.class);

    @Autowired
    private CounselorDAO        counselorDAO;
    @Autowired
    private ConsultantDAO       consultantDAO;
    //property constants
    public static final String  CONSULTANT_ID = "consultantId";
    public static final String  COUNSELOR_ID  = "counselorId";
    public static final String  STEP          = "step";
    public static final String  MEMO          = "memo";
    public static final String  ORDER_ID      = "orderId";
    public static final String  STATE         = "state";

    public Interview findById(java.lang.Integer id) {
        log.debug("getting Interview instance with id: " + id);
        try {
            Interview instance = (Interview) getSession().get("com.ccconsult.pojo.Interview", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List<Interview> findByConsultantId(int id) {
        String hql = "from Interview  where consultantId=" + id + " order by gmtCreate desc";
        return findPageByQuery(0, Integer.MAX_VALUE, hql, null);
    }

    public List<InterviewVO> findInterviewsConsultant(int consultant, InterviewStepEnum stepEnum,
                                                      DataStateEnum state) {
        String hql = "from Interview  where consultantId=" + consultant + "and step="
                     + stepEnum.getValue() + "and state=" + state.getValue()
                     + " order by gmtCreate desc";
        List<Interview> interviews = findPageByQuery(0, Integer.MAX_VALUE, hql, null);

        List<InterviewVO> interviewVOs = new ArrayList<InterviewVO>();
        if (CollectionUtils.isEmpty(interviews)) {
            return interviewVOs;
        }
        for (Interview interview : interviews) {
            InterviewVO interviewVO = new InterviewVO();
            interviewVO.setInterview(interview);
            interviewVO.setConsultant(consultantDAO.findById(interview.getConsultantId()));
            interviewVO.setCounselorVO(counselorDAO.findById(interview.getCounselorId()));
            interviewVOs.add(interviewVO);
        }
        return interviewVOs;
    }

    public List<InterviewVO> findUnDelInterviewsByCounselor(int counselorId) {
        String hql = "from Interview  where counselorId=" + counselorId + "and step!=4 and state!=3 order by gmtCreate desc";
        List<Interview> interviews = findPageByQuery(0, Integer.MAX_VALUE, hql, null);
        List<InterviewVO> interviewVOs = new ArrayList<InterviewVO>();
        if (CollectionUtils.isEmpty(interviews)) {
            return interviewVOs;
        }
        for (Interview interview : interviews) {
            InterviewVO interviewVO = new InterviewVO();
            interviewVO.setInterview(interview);
            interviewVO.setConsultant(consultantDAO.findById(interview.getConsultantId()));
            interviewVO.setCounselorVO(counselorDAO.findById(interview.getCounselorId()));
            interviewVOs.add(interviewVO);
        }
        return interviewVOs;
    }

    public List<InterviewVO> findInterviewsByCounselor(int counselorId, InterviewStepEnum stepEnum,
                                                       DataStateEnum state) {
        String hql = "from Interview  where counselorId=" + counselorId + "and step="
                     + stepEnum.getValue() + "and state=" + state.getValue()
                     + " order by gmtCreate desc";
        List<Interview> interviews = findPageByQuery(0, Integer.MAX_VALUE, hql, null);

        List<InterviewVO> interviewVOs = new ArrayList<InterviewVO>();
        if (CollectionUtils.isEmpty(interviews)) {
            return interviewVOs;
        }
        for (Interview interview : interviews) {
            InterviewVO interviewVO = new InterviewVO();
            interviewVO.setInterview(interview);
            interviewVO.setConsultant(consultantDAO.findById(interview.getConsultantId()));
            interviewVO.setCounselorVO(counselorDAO.findById(interview.getCounselorId()));
            interviewVOs.add(interviewVO);
        }
        return interviewVOs;
    }

}