package com.ccconsult.pojo;

/**
 * DiagMessage entity. @author MyEclipse Persistence Tools
 */

public class DiagMessage extends com.ccconsult.base.ToString implements java.io.Serializable {

    // Fields    

    private Integer id;
    private String  senderRole;
    private Integer senderId;
    private String  gmtCreate;
    private String  message;

    // Constructors

    /** default constructor */
    public DiagMessage() {
    }

    /** full constructor */
    public DiagMessage(String senderRole, Integer senderId, String gmtCreate, String message) {
        this.senderRole = senderRole;
        this.senderId = senderId;
        this.gmtCreate = gmtCreate;
        this.message = message;
    }

    // Property accessors

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSenderRole() {
        return this.senderRole;
    }

    public void setSenderRole(String senderRole) {
        this.senderRole = senderRole;
    }

    public Integer getSenderId() {
        return this.senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public String getGmtCreate() {
        return this.gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}