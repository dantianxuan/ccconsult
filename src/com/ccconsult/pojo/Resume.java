package com.ccconsult.pojo;

import java.util.Date;

/**
 * Resume entity. @author MyEclipse Persistence Tools
 */

public class Resume extends com.ccconsult.base.ToString implements java.io.Serializable {

    // Fields    

    private Integer id;
    private String  realName;
    private String  sexy;
    private Date    birth;
    private String  education;
    private String  resume;
    private Date    gmtCreate;
    private Date    gmtModified;
    private long    salary;
    private String  type;

    // Constructors

    /** default constructor */
    public Resume() {
    }

    /** full constructor */
    public Resume(String realName, String sexy, Date birth, String education, String resume,
                  Date gmtCreate, Date gmtModified, long salary, String type) {
        this.realName = realName;
        this.sexy = sexy;
        this.birth = birth;
        this.education = education;
        this.resume = resume;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.salary = salary;
        this.type = type;
    }

    // Property accessors

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRealName() {
        return this.realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSexy() {
        return this.sexy;
    }

    public void setSexy(String sexy) {
        this.sexy = sexy;
    }

    public Date getBirth() {
        return this.birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getEducation() {
        return this.education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getResume() {
        return this.resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
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

    public long getSalary() {
        return this.salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

}