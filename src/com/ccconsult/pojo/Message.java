package com.ccconsult.pojo;

import java.util.Date;

/**
 * Message entity. @author MyEclipse Persistence Tools
 */

public class Message extends com.ccconsult.base.ToString implements java.io.Serializable {

    // Fields    

    private Integer id;
    private String  message;
    private Integer creatorRole;
    private Integer creator;
    private Integer relType;
    private Integer relId;
    private Date    gmtCreate;

    // Constructors

    /** default constructor */
    public Message() {
    }

    /** full constructor */
    public Message(String message, Integer creatorRole, Integer creator, Integer relType,
                   Integer relId, Date gmtCreate) {
        this.message = message;
        this.creatorRole = creatorRole;
        this.creator = creator;
        this.relType = relType;
        this.relId = relId;
        this.gmtCreate = gmtCreate;
    }

    // Property accessors

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCreatorRole() {
        return this.creatorRole;
    }

    public void setCreatorRole(Integer creatorRole) {
        this.creatorRole = creatorRole;
    }

    public Integer getCreator() {
        return this.creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Integer getRelType() {
        return this.relType;
    }

    public void setRelType(Integer relType) {
        this.relType = relType;
    }

    public Integer getRelId() {
        return this.relId;
    }

    public void setRelId(Integer relId) {
        this.relId = relId;
    }

    public Date getGmtCreate() {
        return this.gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

}