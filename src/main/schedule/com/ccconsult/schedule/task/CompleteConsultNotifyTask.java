/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.schedule.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.ccconsult.dao.ConsultDAO;

/**
 * 每天执行一次，在有效期以外处于对收费的且处于进行中的进行通知，如果超过时间还不能完成，则执行退款。
 * 
 * @author jingyudan
 * @version $Id: ConfirmTaskNotify.java, v 0.1 2014-8-31 下午4:06:28 jingyudan Exp $
 */
public class CompleteConsultNotifyTask implements Job {

    /***咨询dao*/
    @Autowired
    private ConsultDAO consultDAO;

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        
        
        
        
    }

}
