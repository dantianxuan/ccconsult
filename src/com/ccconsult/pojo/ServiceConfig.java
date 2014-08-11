package com.ccconsult.pojo;

import java.util.Date;

/**
 * ServiceConfig entity. @author MyEclipse Persistence Tools
 */

public class ServiceConfig extends com.ccconsult.base.ToString implements java.io.Serializable {

    // Fields    

    private Integer id;
    private Integer counselorId;
    private Integer serviceId;
    private Date    gmtCreate;
    private Integer state;
    private String  workOnTime;

    // Constructors

    /** default constructor */
    public ServiceConfig() {
    }

    /** full constructor */
    public ServiceConfig(Integer counselorId, Integer serviceId, Date gmtCreate, Integer state,
                         String workOnTime) {
        this.counselorId = counselorId;
        this.serviceId = serviceId;
        this.gmtCreate = gmtCreate;
        this.state = state;
        this.workOnTime = workOnTime;
    }

    // Property accessors

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCounselorId() {
        return this.counselorId;
    }

    public void setCounselorId(Integer counselorId) {
        this.counselorId = counselorId;
    }

    public Integer getServiceId() {
        return this.serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Date getGmtCreate() {
        return this.gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Integer getState() {
        return this.state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getWorkOnTime() {
        return this.workOnTime;
    }

    public void setWorkOnTime(String workOnTime) {
        this.workOnTime = workOnTime;
    }

}