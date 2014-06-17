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
    private Integer consultantId;
    private String  mobile;
    private String  email;
    private String  workExperience;

    // Constructors

    /** default constructor */
    public Resume() {
    }

    /** full constructor */
    public Resume(String realName, String sexy, Date birth, String education, String resume,
                  Date gmtCreate, Date gmtModified, Integer consultantId, String mobile,
                  String email, String workExperience) {
        this.realName = realName;
        this.sexy = sexy;
        this.birth = birth;
        this.education = education;
        this.resume = resume;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.consultantId = consultantId;
        this.mobile = mobile;
        this.email = email;
        this.workExperience = workExperience;
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

    public Integer getConsultantId() {
        return this.consultantId;
    }

    public void setConsultantId(Integer consultantId) {
        this.consultantId = consultantId;
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

    public String getWorkExperience() {
        return this.workExperience;
    }

    public void setWorkExperience(String workExperience) {
        this.workExperience = workExperience;
    }

}