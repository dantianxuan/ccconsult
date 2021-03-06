package com.ccconsult.pojo;

import java.util.Date;

/**
 * Consult entity. @author MyEclipse Persistence Tools
 */

public class Consult extends com.ccconsult.base.ToString implements java.io.Serializable {

    // Fields    

    /**  */
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer consultantId;
    private Integer counselorId;
    private Integer step;
    private Integer payTag;
    private Integer serviceId;
    private String  goal;
    private Date    gmtModified;
    private Date    gmtCreate;
    private String  rejectReason;
    private Integer serviceConfigId;
    private String  indetityCode;
    private Date    gmtEffectBegin;
    private Date    gmtEffectEnd;
    private double  price;

    // Constructors

    /** default constructor */
    public Consult() {
    }

    /** full constructor */
    public Consult(Integer consultantId, Integer counselorId, Integer step, Integer payTag,
                   Integer serviceId, String goal, Date gmtModified, Date gmtCreate,
                   String rejectReason, Integer serviceConfigId, String indetityCode,
                   Date gmtEffectBegin, Date gmtEffectEnd, double price) {
        this.consultantId = consultantId;
        this.counselorId = counselorId;
        this.step = step;
        this.payTag = payTag;
        this.serviceId = serviceId;
        this.goal = goal;
        this.gmtModified = gmtModified;
        this.gmtCreate = gmtCreate;
        this.rejectReason = rejectReason;
        this.serviceConfigId = serviceConfigId;
        this.indetityCode = indetityCode;
        this.gmtEffectBegin = gmtEffectBegin;
        this.gmtEffectEnd = gmtEffectEnd;
        this.price = price;
    }

    // Property accessors

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getConsultantId() {
        return this.consultantId;
    }

    public void setConsultantId(Integer consultantId) {
        this.consultantId = consultantId;
    }

    public Integer getCounselorId() {
        return this.counselorId;
    }

    public void setCounselorId(Integer counselorId) {
        this.counselorId = counselorId;
    }

    public Integer getStep() {
        return this.step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public Integer getPayTag() {
        return this.payTag;
    }

    public void setPayTag(Integer payTag) {
        this.payTag = payTag;
    }

    public Integer getServiceId() {
        return this.serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getGoal() {
        return this.goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public Date getGmtModified() {
        return this.gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Date getGmtCreate() {
        return this.gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getRejectReason() {
        return this.rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public Integer getServiceConfigId() {
        return this.serviceConfigId;
    }

    public void setServiceConfigId(Integer serviceConfigId) {
        this.serviceConfigId = serviceConfigId;
    }

    public String getIndetityCode() {
        return this.indetityCode;
    }

    public void setIndetityCode(String indetityCode) {
        this.indetityCode = indetityCode;
    }

    public Date getGmtEffectBegin() {
        return this.gmtEffectBegin;
    }

    public void setGmtEffectBegin(Date gmtEffectBegin) {
        this.gmtEffectBegin = gmtEffectBegin;
    }

    public Date getGmtEffectEnd() {
        return this.gmtEffectEnd;
    }

    public void setGmtEffectEnd(Date gmtEffectEnd) {
        this.gmtEffectEnd = gmtEffectEnd;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}