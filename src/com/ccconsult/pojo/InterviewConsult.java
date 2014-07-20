package com.ccconsult.pojo;

import java.util.Date;

/**
 * InterviewConsult entity. @author MyEclipse Persistence Tools
 */

public class InterviewConsult extends com.ccconsult.base.ToString implements java.io.Serializable {

    // Fields    

    private Integer id;
    private String  resumeFile;
    private Integer consultId;
    private Date    gmtScheduleBegin;
    private Date    gmtScheduleEnd;
    private String  targetJobInfo;
    private Integer consultantApprise;
    private String  consultMemo;

    // Constructors

    /** default constructor */
    public InterviewConsult() {
    }

    /** full constructor */
    public InterviewConsult(String resumeFile, Integer consultId, Date gmtScheduleBegin,
                            Date gmtScheduleEnd, String targetJobInfo, Integer consultantApprise,
                            String consultMemo) {
        this.resumeFile = resumeFile;
        this.consultId = consultId;
        this.gmtScheduleBegin = gmtScheduleBegin;
        this.gmtScheduleEnd = gmtScheduleEnd;
        this.targetJobInfo = targetJobInfo;
        this.consultantApprise = consultantApprise;
        this.consultMemo = consultMemo;
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

    public String getTargetJobInfo() {
        return this.targetJobInfo;
    }

    public void setTargetJobInfo(String targetJobInfo) {
        this.targetJobInfo = targetJobInfo;
    }

    public Integer getConsultantApprise() {
        return this.consultantApprise;
    }

    public void setConsultantApprise(Integer consultantApprise) {
        this.consultantApprise = consultantApprise;
    }

    public String getConsultMemo() {
        return this.consultMemo;
    }

    public void setConsultMemo(String consultMemo) {
        this.consultMemo = consultMemo;
    }

}