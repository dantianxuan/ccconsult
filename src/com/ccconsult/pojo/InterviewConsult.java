package com.ccconsult.pojo;

import java.util.Date;


/**
 * InterviewConsult entity. @author MyEclipse Persistence Tools
 */

public class InterviewConsult extends com.ccconsult.base.ToString implements java.io.Serializable {


    // Fields    

     private Integer id;
     private String goal;
     private Date gmtBegin;
     private Date gmtEnd;


    // Constructors

    /** default constructor */
    public InterviewConsult() {
    }

    
    /** full constructor */
    public InterviewConsult(String goal, Date gmtBegin, Date gmtEnd) {
        this.goal = goal;
        this.gmtBegin = gmtBegin;
        this.gmtEnd = gmtEnd;
    }

   
    // Property accessors

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoal() {
        return this.goal;
    }
    
    public void setGoal(String goal) {
        this.goal = goal;
    }

    public Date getGmtBegin() {
        return this.gmtBegin;
    }
    
    public void setGmtBegin(Date gmtBegin) {
        this.gmtBegin = gmtBegin;
    }

    public Date getGmtEnd() {
        return this.gmtEnd;
    }
    
    public void setGmtEnd(Date gmtEnd) {
        this.gmtEnd = gmtEnd;
    }
   








}