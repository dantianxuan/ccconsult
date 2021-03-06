package com.ccconsult.pojo;

import java.util.Date;

/**
 * Article entity. @author MyEclipse Persistence Tools
 */

public class Article extends com.ccconsult.base.ToString implements java.io.Serializable {

    // Fields    

    private Integer id;
    private String  title;
    private String  content;
    private Date    gmtCreate;
    private Date    gmtModified;
    private String  topPhoto;
    private Integer state;
    private String  topTag;
    private Integer type;
    private Integer hitCount;
    private String  replyCount;

    // Constructors

    /** default constructor */
    public Article() {
    }

    /** full constructor */
    public Article(String title, String content, Date gmtCreate, Date gmtModified, String topPhoto,
                   Integer state, String topTag, Integer type, Integer hitCount, String replyCount) {
        this.title = title;
        this.content = content;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.topPhoto = topPhoto;
        this.state = state;
        this.topTag = topTag;
        this.type = type;
        this.hitCount = hitCount;
        this.replyCount = replyCount;
    }

    // Property accessors

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Date getGmtModified() {
        return this.gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getTopPhoto() {
        return this.topPhoto;
    }

    public void setTopPhoto(String topPhoto) {
        this.topPhoto = topPhoto;
    }

    public Integer getState() {
        return this.state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getTopTag() {
        return this.topTag;
    }

    public void setTopTag(String topTag) {
        this.topTag = topTag;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getHitCount() {
        return this.hitCount;
    }

    public void setHitCount(Integer hitCount) {
        this.hitCount = hitCount;
    }

    public String getReplyCount() {
        return this.replyCount;
    }

    public void setReplyCount(String replyCount) {
        this.replyCount = replyCount;
    }

}