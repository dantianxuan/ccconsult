package com.ccconsult.pojo;

import java.util.Date;

/**
 * MobileToken entity. @author MyEclipse Persistence Tools
 */

public class MobileToken extends com.ccconsult.base.ToString implements java.io.Serializable {

    // Fields    

    private Integer id;
    private String  token;
    private Date    gmtCreate;
    private Date    gmtModified;
    private Integer tokenType;
    private String  params;
    private Integer sendTimes;
    private String  mobile;

    // Constructors

    /** default constructor */
    public MobileToken() {
    }

    /** full constructor */
    public MobileToken(String token, Date gmtCreate, Date gmtModified, Integer tokenType,
                       String params, Integer sendTimes, String mobile) {
        this.token = token;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.tokenType = tokenType;
        this.params = params;
        this.sendTimes = sendTimes;
        this.mobile = mobile;
    }

    // Property accessors

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getGmtModified() {
        return this.gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Integer getTokenType() {
        return this.tokenType;
    }

    public void setTokenType(Integer tokenType) {
        this.tokenType = tokenType;
    }

    public String getParams() {
        return this.params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Integer getSendTimes() {
        return this.sendTimes;
    }

    public void setSendTimes(Integer sendTimes) {
        this.sendTimes = sendTimes;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}