package com.ccconsult.pojo;

import java.util.Date;

/**
 * AccountTrans entity. @author MyEclipse Persistence Tools
 */

public class AccountTrans extends com.ccconsult.base.ToString implements java.io.Serializable {

    // Fields    

    private Integer id;
    private double  money;
    private Integer transType;
    private Integer relRoleId;
    private Integer relRoleType;
    private String  detail;
    private Date    gmtCreate;
    private String  transToken;
    private Integer consultId;

    // Constructors

    /** default constructor */
    public AccountTrans() {
    }

    /** full constructor */
    public AccountTrans(double money, Integer transType, Integer relRoleId, Integer relRoleType,
                        String detail, Date gmtCreate, String transToken, Integer consultId) {
        this.money = money;
        this.transType = transType;
        this.relRoleId = relRoleId;
        this.relRoleType = relRoleType;
        this.detail = detail;
        this.gmtCreate = gmtCreate;
        this.transToken = transToken;
        this.consultId = consultId;
    }

    // Property accessors

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getMoney() {
        return this.money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public Integer getTransType() {
        return this.transType;
    }

    public void setTransType(Integer transType) {
        this.transType = transType;
    }

    public Integer getRelRoleId() {
        return this.relRoleId;
    }

    public void setRelRoleId(Integer relRoleId) {
        this.relRoleId = relRoleId;
    }

    public Integer getRelRoleType() {
        return this.relRoleType;
    }

    public void setRelRoleType(Integer relRoleType) {
        this.relRoleType = relRoleType;
    }

    public String getDetail() {
        return this.detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Date getGmtCreate() {
        return this.gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getTransToken() {
        return this.transToken;
    }

    public void setTransToken(String transToken) {
        this.transToken = transToken;
    }

    public Integer getConsultId() {
        return this.consultId;
    }

    public void setConsultId(Integer consultId) {
        this.consultId = consultId;
    }

}