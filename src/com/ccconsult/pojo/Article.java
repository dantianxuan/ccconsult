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
    private String  sate;
    private String  top;

    // Constructors

    /** default constructor */
    public Article() {
    }

    /** full constructor */
    public Article(String title, String content, Date gmtCreate, Date gmtModified, String topPhoto,
                   String sate, String top) {
        this.title = title;
        this.content = content;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.topPhoto = topPhoto;
        this.sate = sate;
        this.top = top;
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

    public String getSate() {
        return this.sate;
    }

    public void setSate(String sate) {
        this.sate = sate;
    }

    public String getTop() {
        return this.top;
    }

    public void setTop(String top) {
        this.top = top;
    }

}