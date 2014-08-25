package com.ccconsult.pojo;

import java.util.Date;

/**
 * InnerMail entity. @author MyEclipse Persistence Tools
 */

public class InnerMail extends com.ccconsult.base.ToString implements java.io.Serializable {

    // Fields    

    private Integer id;
    private String  content;
    private Integer senderId;
    private Integer senderRole;
    private Date    gmtCreate;
    private Date    gmtModified;
    private Integer receiverId;
    private Integer receiverRole;
    private Integer state;
    private Integer type;

    // Constructors

    /** default constructor */
    public InnerMail() {
    }

    /** full constructor */
    public InnerMail(String content, Integer senderId, Integer senderRole, Date gmtCreate,
                     Date gmtModified, Integer receiverId, Integer receiverRole, Integer state,
                     Integer type) {
        this.content = content;
        this.senderId = senderId;
        this.senderRole = senderRole;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.receiverId = receiverId;
        this.receiverRole = receiverRole;
        this.state = state;
        this.type = type;
    }

    // Property accessors

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getSenderId() {
        return this.senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public Integer getSenderRole() {
        return this.senderRole;
    }

    public void setSenderRole(Integer senderRole) {
        this.senderRole = senderRole;
    }

    public Date getGmtCreate() {
        return this.gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return this.gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Integer getReceiverId() {
        return this.receiverId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }

    public Integer getReceiverRole() {
        return this.receiverRole;
    }

    public void setReceiverRole(Integer receiverRole) {
        this.receiverRole = receiverRole;
    }

    public Integer getState() {
        return this.state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}