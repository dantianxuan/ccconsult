package com.ccconsult.pojo;

import java.util.Date;

/**
 * ConsultOnline entity. @author MyEclipse Persistence Tools
 */

public class ConsultOnline extends com.ccconsult.base.ToString implements java.io.Serializable {

    // Fields    

    private Integer id;
    private Integer consultId;
    private Date    gmtScheduleBegin;
    private Date    gmtScheduleEnd;
    private String  schedueTime;

    // Constructors

    /** default constructor */
    public ConsultOnline() {
    }

    /** full constructor */
    public ConsultOnline(Integer consultId, Date gmtScheduleBegin, Date gmtScheduleEnd,
                         String schedueTime) {
        this.consultId = consultId;
        this.gmtScheduleBegin = gmtScheduleBegin;
        this.gmtScheduleEnd = gmtScheduleEnd;
        this.schedueTime = schedueTime;
    }

    // Property accessors

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getSchedueTime() {
        return this.schedueTime;
    }

    public void setSchedueTime(String schedueTime) {
        this.schedueTime = schedueTime;
    }

}