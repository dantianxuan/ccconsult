package com.ccconsult.pojo;

import java.util.Date;

/**
 * Comment entity. @author MyEclipse Persistence Tools
 */

public class Comment extends com.ccconsult.base.ToString implements java.io.Serializable {

    // Fields    

    private Integer id;
    private String  content;
    private Date    gmtCreate;
    private Integer creatorId;
    private Integer creatorRole;
    private Integer relId;
    private Integer relType;
    private String  relInfo;

    // Constructors

    /** default constructor */
    public Comment() {
    }

    /** full constructor */
    public Comment(String content, Date gmtCreate, Integer creatorId, Integer creatorRole,
                   Integer relId, Integer relType, String relInfo) {
        this.content = content;
        this.gmtCreate = gmtCreate;
        this.creatorId = creatorId;
        this.creatorRole = creatorRole;
        this.relId = relId;
        this.relType = relType;
        this.relInfo = relInfo;
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

    public Date getGmtCreate() {
        return this.gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Integer getCreatorId() {
        return this.creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getCreatorRole() {
        return this.creatorRole;
    }

    public void setCreatorRole(Integer creatorRole) {
        this.creatorRole = creatorRole;
    }

    public Integer getRelId() {
        return this.relId;
    }

    public void setRelId(Integer relId) {
        this.relId = relId;
    }

    public Integer getRelType() {
        return this.relType;
    }

    public void setRelType(Integer relType) {
        this.relType = relType;
    }

    public String getRelInfo() {
        return this.relInfo;
    }

    public void setRelInfo(String relInfo) {
        this.relInfo = relInfo;
    }

}