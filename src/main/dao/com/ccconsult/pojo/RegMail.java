package com.ccconsult.pojo;

import java.util.Date;

/**
 * RegMail entity. @author MyEclipse Persistence Tools
 */

public class RegMail extends com.ccconsult.base.ToString implements java.io.Serializable {

    // Fields    

    private Integer id;
    private String  mail;
    private String  token;
    private Date    gmtCreate;

    // Constructors

    /** default constructor */
    public RegMail() {
    }

    /** full constructor */
    public RegMail(String mail, String token, Date gmtCreate) {
        this.mail = mail;
        this.token = token;
        this.gmtCreate = gmtCreate;
    }

    // Property accessors

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMail() {
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getGmtCreate() {
        return this.gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

}