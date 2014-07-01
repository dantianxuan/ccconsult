package com.ccconsult.pojo;

import java.util.Date;

/**
 * Counselor entity. @author MyEclipse Persistence Tools
 */

public class Counselor extends com.ccconsult.base.ToString implements java.io.Serializable {

    // Fields    

    private Integer id;
    private String  name;
    private String  email;
    private String  mobile;
    private Date    gmtCreate;
    private Date    gmtModified;
    private String  description;
    private Integer companyId;
    private String  photo;
    private String  passwd;
    private String  department;

    // Constructors

    /** default constructor */
    public Counselor() {
    }

    /** full constructor */
    public Counselor(String name, String email, String mobile, Date gmtCreate, Date gmtModified,
                     String description, Integer companyId, String photo, String passwd,
                     String department) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.description = description;
        this.companyId = companyId;
        this.photo = photo;
        this.passwd = passwd;
        this.department = department;
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

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getPhoto() {
        return this.photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPasswd() {
        return this.passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getDepartment() {
        return this.department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

}