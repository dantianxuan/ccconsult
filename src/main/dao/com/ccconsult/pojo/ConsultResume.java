package com.ccconsult.pojo;

/**
 * ConsultResume entity. @author MyEclipse Persistence Tools
 */

public class ConsultResume extends com.ccconsult.base.ToString implements java.io.Serializable {

    // Fields    

    private Integer id;
    private String  resumeFiles;
    private String  review;
    private String  reviewFiles;
    private Integer consultId;

    // Constructors

    /** default constructor */
    public ConsultResume() {
    }

    /** full constructor */
    public ConsultResume(String resumeFiles, String review, String reviewFiles, Integer consultId) {
        this.resumeFiles = resumeFiles;
        this.review = review;
        this.reviewFiles = reviewFiles;
        this.consultId = consultId;
    }

    // Property accessors

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResumeFiles() {
        return this.resumeFiles;
    }

    public void setResumeFiles(String resumeFiles) {
        this.resumeFiles = resumeFiles;
    }

    public String getReview() {
        return this.review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getReviewFiles() {
        return this.reviewFiles;
    }

    public void setReviewFiles(String reviewFiles) {
        this.reviewFiles = reviewFiles;
    }

    public Integer getConsultId() {
        return this.consultId;
    }

    public void setConsultId(Integer consultId) {
        this.consultId = consultId;
    }

}