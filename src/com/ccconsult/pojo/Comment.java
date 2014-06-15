package com.ccconsult.pojo;

import java.util.Date;

/**
 * Comment entity. @author MyEclipse Persistence Tools
 */

public class Comment extends com.ccconsult.base.ToString implements java.io.Serializable {

    // Fields    

    private Integer id;
    private String  comment;
    private String  themeType;
    private Integer themeId;
    private Date    gmtCreate;
    private String  creator;
    private Date    gmtModified;

    // Constructors

    /** default constructor */
    public Comment() {
    }

    /** full constructor */
    public Comment(String comment, String themeType, Integer themeId, Date gmtCreate,
                   String creator, Date gmtModified) {
        this.comment = comment;
        this.themeType = themeType;
        this.themeId = themeId;
        this.gmtCreate = gmtCreate;
        this.creator = creator;
        this.gmtModified = gmtModified;
    }

    // Property accessors

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getThemeType() {
        return this.themeType;
    }

    public void setThemeType(String themeType) {
        this.themeType = themeType;
    }

    public Integer getThemeId() {
        return this.themeId;
    }

    public void setThemeId(Integer themeId) {
        this.themeId = themeId;
    }

    public Date getGmtCreate() {
        return this.gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getGmtModified() {
        return this.gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

}