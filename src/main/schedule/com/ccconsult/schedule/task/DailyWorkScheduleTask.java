/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.schedule.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 每天用户的消息通知，在通知的时候，合并通知信息
 * 每天早上8点执行
 * 
 * @author jingyudan
 * @version $Id: DailyWorkScheduleTask.java, v 0.1 2014-8-31 下午4:04:11 jingyudan Exp $
 */
public class DailyWorkScheduleTask implements Job {

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
    }

}
