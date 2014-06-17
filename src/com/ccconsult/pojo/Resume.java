package com.ccconsult.pojo;

import java.util.Date;

/**
 * Resume entity. @author MyEclipse Persistence Tools
 */

public class Resume extends com.ccconsult.base.ToString implements java.io.Serializable {

    // Fields    

    private Integer id;
    private Integer jobSeekerId;
    private String  realName;
    private String  sexy;
    private Date    birth;
    private String  education;
    private String  resume;
    private Date    gmtCreate;
    private Date    gmtModified;
    private Long    salary;
    private String  type;
    private Date    graduation;
    private String  mobile;
    private String  email;
    private String  workExperience;

    // Constructors

    /** default constructor */
    public Resume() {
    }

    /** full constructor */
    public Resume(Integer jobSeekerId, String realName, String sexy, Date birth, String education,
                  String resume, Date gmtCreate, Date gmtModified, Long salary, String type,
                  Date graduation, String mobile, String email, String workExperience) {
        this.jobSeekerId = jobSeekerId;
        this.realName = realName;
        this.sexy = sexy;
        this.birth = birth;
        this.education = education;
        this.resume = resume;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.salary = salary;
        this.type = type;
        this.graduation = graduation;
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

    public Integer getJobSeekerId() {
        return this.jobSeekerId;
    }

    public void setJobSeekerId(Integer jobSeekerId) {
        this.jobSeekerId = jobSeekerId;
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

    public Long getSalary() {
        return this.salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getGraduation() {
        return this.graduation;
    }

    public void setGraduation(Date graduation) {
        this.graduation = graduation;
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