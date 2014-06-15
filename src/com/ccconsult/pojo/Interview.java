package com.ccconsult.pojo;

import java.util.Date;

/**
 * Interview entity. @author MyEclipse Persistence Tools
 */

public class Interview extends com.ccconsult.base.ToString implements java.io.Serializable {

    // Fields    

    private Integer id;
    private Integer consultantId;
    private Integer counselorId;
    private Integer step;
    private String  memo;
    private String  orderId;
    private Date    gmtModified;
    private Date    gmtCreate;
    private Integer state;
    private Date    gmtShceduleBegin;
    private Date    gmtScheduleEnd;

    // Constructors

    /** default constructor */
    public Interview() {
    }

    /** full constructor */
    public Interview(Integer consultantId, Integer counselorId, Integer step, String memo,
                     String orderId, Date gmtModified, Date gmtCreate, Integer state,
                     Date gmtShceduleBegin, Date gmtScheduleEnd) {
        this.consultantId = consultantId;
        this.counselorId = counselorId;
        this.step = step;
        this.memo = memo;
        this.orderId = orderId;
        this.gmtModified = gmtModified;
        this.gmtCreate = gmtCreate;
        this.state = state;
        this.gmtShceduleBegin = gmtShceduleBegin;
        this.gmtScheduleEnd = gmtScheduleEnd;
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

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public Integer getState() {
        return this.state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getGmtShceduleBegin() {
        return this.gmtShceduleBegin;
    }

    public void setGmtShceduleBegin(Date gmtShceduleBegin) {
        this.gmtShceduleBegin = gmtShceduleBegin;
    }

    public Date getGmtScheduleEnd() {
        return this.gmtScheduleEnd;
    }

    public void setGmtScheduleEnd(Date gmtScheduleEnd) {
        this.gmtScheduleEnd = gmtScheduleEnd;
    }

}