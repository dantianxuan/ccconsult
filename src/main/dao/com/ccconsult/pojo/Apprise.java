package com.ccconsult.pojo;

import java.util.Date;

/**
 * Apprise entity. @author MyEclipse Persistence Tools
 */

public class Apprise extends com.ccconsult.base.ToString implements java.io.Serializable {

    // Fields    

    private Integer id;
    private Integer score;
    private Integer creator;
    private String  memo;
    private Integer relType;
    private Integer relId;
    private Date    gmtCreate;
    private Integer creatorRole;

    // Constructors

    /** default constructor */
    public Apprise() {
    }

    /** full constructor */
    public Apprise(Integer score, Integer creator, String memo, Integer relType, Integer relId,
                   Date gmtCreate, Integer creatorRole) {
        this.score = score;
        this.creator = creator;
        this.memo = memo;
        this.relType = relType;
        this.relId = relId;
        this.gmtCreate = gmtCreate;
        this.creatorRole = creatorRole;
    }

    // Property accessors

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getScore() {
        return this.score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getCreator() {
        return this.creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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

    public Integer getCreatorRole() {
        return this.creatorRole;
    }

    public void setCreatorRole(Integer creatorRole) {
        this.creatorRole = creatorRole;
    }

}