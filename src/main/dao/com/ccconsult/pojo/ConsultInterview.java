package com.ccconsult.pojo;

import java.util.Date;

/**
 * ConsultInterview entity. @author MyEclipse Persistence Tools
 */

public class ConsultInterview extends com.ccconsult.base.ToString implements java.io.Serializable {

    // Fields    

    private Integer id;
    private String  resumeFile;
    private Integer consultId;
    private Date    gmtScheduleBegin;
    private Date    gmtScheduleEnd;
    private String  interviewMemo;
    private String  schedueTime;

    // Constructors

    /** default constructor */
    public ConsultInterview() {
    }

    /** full constructor */
    public ConsultInterview(String resumeFile, Integer consultId, Date gmtScheduleBegin,
                            Date gmtScheduleEnd, String interviewMemo, String schedueTime) {
        this.resumeFile = resumeFile;
        this.consultId = consultId;
        this.gmtScheduleBegin = gmtScheduleBegin;
        this.gmtScheduleEnd = gmtScheduleEnd;
        this.interviewMemo = interviewMemo;
        this.schedueTime = schedueTime;
    }

    // Property accessors

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResumeFile() {
        return this.resumeFile;
    }

    public void setResumeFile(String resumeFile) {
        this.resumeFile = resumeFile;
    }

    public Integer getConsultId() {
        return this.consultId;
    }

    public void setConsultId(Integer consultId) {
        this.consultId = consultId;
    }

    public Date getGmtScheduleBegin() {
        return this.gmtScheduleBegin;
    }

    public void setGmtScheduleBegin(Date gmtScheduleBegin) {
        this.gmtScheduleBegin = gmtScheduleBegin;
    }

    public Date getGmtScheduleEnd() {
        return this.gmtScheduleEnd;
    }

    public void setGmtScheduleEnd(Date gmtScheduleEnd) {
        this.gmtScheduleEnd = gmtScheduleEnd;
    }

    public String getInterviewMemo() {
        return this.interviewMemo;
    }

    public void setInterviewMemo(String interviewMemo) {
        this.interviewMemo = interviewMemo;
    }

    public String getSchedueTime() {
        return this.schedueTime;
    }

    public void setSchedueTime(String schedueTime) {
        this.schedueTime = schedueTime;
    }

}