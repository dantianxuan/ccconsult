package com.ccconsult.pojo;

/**
 * Account entity. @author MyEclipse Persistence Tools
 */

public class Account extends com.ccconsult.base.ToString implements java.io.Serializable {

    // Fields    

    private Integer id;
    private Integer relRoleId;
    private Integer relRoleType;
    private double  inAllMoney;
    private double  currentMoney;
    private double  freezingMoney;
    private double  transAllMoney;

    // Constructors

    /** default constructor */
    public Account() {
    }

    /** full constructor */
    public Account(Integer relRoleId, Integer relRoleType, double inAllMoney, double currentMoney,
                   double freezingMoney, double transAllMoney) {
        this.relRoleId = relRoleId;
        this.relRoleType = relRoleType;
        this.inAllMoney = inAllMoney;
        this.currentMoney = currentMoney;
        this.freezingMoney = freezingMoney;
        this.transAllMoney = transAllMoney;
    }

    // Property accessors

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public double getInAllMoney() {
        return this.inAllMoney;
    }

    public void setInAllMoney(double inAllMoney) {
        this.inAllMoney = inAllMoney;
    }

    public double getCurrentMoney() {
        return this.currentMoney;
    }

    public void setCurrentMoney(double currentMoney) {
        this.currentMoney = currentMoney;
    }

    public double getFreezingMoney() {
        return this.freezingMoney;
    }

    public void setFreezingMoney(double freezingMoney) {
        this.freezingMoney = freezingMoney;
    }

    public double getTransAllMoney() {
        return this.transAllMoney;
    }

    public void setTransAllMoney(double transAllMoney) {
        this.transAllMoney = transAllMoney;
    }

}