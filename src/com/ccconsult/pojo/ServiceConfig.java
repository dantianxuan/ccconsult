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
    private Integer price;
    private Date    gmtCreate;
    private Integer state;

    // Constructors

    /** default constructor */
    public ServiceConfig() {
    }

    /** minimal constructor */
    public ServiceConfig(Integer price) {
        this.price = price;
    }

    /** full constructor */
    public ServiceConfig(Integer counselorId, Integer serviceId, Integer price, Date gmtCreate,
                         Integer state) {
        this.counselorId = counselorId;
        this.serviceId = serviceId;
        this.price = price;
        this.gmtCreate = gmtCreate;
        this.state = state;
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

    public Integer getPrice() {
        return this.price;
    }

    public void setPrice(Integer price) {
        this.price = price;
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

}