package com.ccconsult.pojo;

import java.util.Date;

/**
 * Prise entity. @author MyEclipse Persistence Tools
 */

public class Prise extends com.ccconsult.base.ToString implements java.io.Serializable {

    // Fields    

    private Integer id;
    private String  prise;
    private Date    gmtCreate;
    private Integer creator;
    private Integer type;

    // Constructors

    /** default constructor */
    public Prise() {
    }

    /** full constructor */
    public Prise(String prise, Date gmtCreate, Integer creator, Integer type) {
        this.prise = prise;
        this.gmtCreate = gmtCreate;
        this.creator = creator;
        this.type = type;
    }

    // Property accessors

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrise() {
        return this.prise;
    }

    public void setPrise(String prise) {
        this.prise = prise;
    }

    public Date getGmtCreate() {
        return this.gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Integer getCreator() {
        return this.creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}