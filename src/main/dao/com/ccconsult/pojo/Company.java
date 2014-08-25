package com.ccconsult.pojo;

import java.util.Date;

/**
 * Company entity. @author MyEclipse Persistence Tools
 */

public class Company extends com.ccconsult.base.ToString implements java.io.Serializable {

    // Fields    

    private Integer id;
    private String  name;
    private String  description;
    private String  link;
    private Date    gmtCreate;
    private String  mailSuffix;
    private String  photo;
    private Date    gmtModified;
    private Integer counselorCount;
    private String  regMobile;
    private Integer state;

    // Constructors

    /** default constructor */
    public Company() {
    }

    /** full constructor */
    public Company(String name, String description, String link, Date gmtCreate, String mailSuffix,
                   String photo, Date gmtModified, Integer counselorCount, String regMobile,
                   Integer state) {
        this.name = name;
        this.description = description;
        this.link = link;
        this.gmtCreate = gmtCreate;
        this.mailSuffix = mailSuffix;
        this.photo = photo;
        this.gmtModified = gmtModified;
        this.counselorCount = counselorCount;
        this.regMobile = regMobile;
        this.state = state;
    }

    // Property accessors

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getGmtCreate() {
        return this.gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getMailSuffix() {
        return this.mailSuffix;
    }

    public void setMailSuffix(String mailSuffix) {
        this.mailSuffix = mailSuffix;
    }

    public String getPhoto() {
        return this.photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Date getGmtModified() {
        return this.gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Integer getCounselorCount() {
        return this.counselorCount;
    }

    public void setCounselorCount(Integer counselorCount) {
        this.counselorCount = counselorCount;
    }

    public String getRegMobile() {
        return this.regMobile;
    }

    public void setRegMobile(String regMobile) {
        this.regMobile = regMobile;
    }

    public Integer getState() {
        return this.state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

}