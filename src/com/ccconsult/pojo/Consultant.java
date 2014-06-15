package com.ccconsult.pojo;

import java.util.Date;

/**
 * Consultant entity. @author MyEclipse Persistence Tools
 */

public class Consultant extends com.ccconsult.base.ToString implements java.io.Serializable {

    // Fields    

    private Integer id;
    private String  name;
    private String  passwd;
    private String  mobile;
    private String  email;
    private String  photo;
    private Date    gmtCreate;
    private Date    gmtModified;

    // Constructors

    /** default constructor */
    public Consultant() {
    }

    /** full constructor */
    public Consultant(String name, String passwd, String mobile, String email, String photo,
                      Date gmtCreate, Date gmtModified) {
        this.name = name;
        this.passwd = passwd;
        this.mobile = mobile;
        this.email = email;
        this.photo = photo;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
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

    public String getPasswd() {
        return this.passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return this.photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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

}