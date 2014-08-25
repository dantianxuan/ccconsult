package com.ccconsult.pojo;

/**
 * Service entity. @author MyEclipse Persistence Tools
 */

public class Service extends com.ccconsult.base.ToString implements java.io.Serializable {

    // Fields    

    private Integer id;
    private String  name;
    private String  description;
    private String  priceRegion;
    private String  gmtCreate;
    private String  gmtModified;
    private String  state;
    private Integer introArticleId;
    private String  code;
    private Integer effectTime;
    private String  photo;
    private Integer scheduleType;

    // Constructors

    /** default constructor */
    public Service() {
    }

    /** full constructor */
    public Service(String name, String description, String priceRegion, String gmtCreate,
                   String gmtModified, String state, Integer introArticleId, String code,
                   Integer effectTime, String photo, Integer scheduleType) {
        this.name = name;
        this.description = description;
        this.priceRegion = priceRegion;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.state = state;
        this.introArticleId = introArticleId;
        this.code = code;
        this.effectTime = effectTime;
        this.photo = photo;
        this.scheduleType = scheduleType;
    }

    // Property accessors

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriceRegion() {
        return this.priceRegion;
    }

    public void setPriceRegion(String priceRegion) {
        this.priceRegion = priceRegion;
    }

    public String getGmtCreate() {
        return this.gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getGmtModified() {
        return this.gmtModified;
    }

    public void setGmtModified(String gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getIntroArticleId() {
        return this.introArticleId;
    }

    public void setIntroArticleId(Integer introArticleId) {
        this.introArticleId = introArticleId;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getEffectTime() {
        return this.effectTime;
    }

    public void setEffectTime(Integer effectTime) {
        this.effectTime = effectTime;
    }

    public String getPhoto() {
        return this.photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getScheduleType() {
        return this.scheduleType;
    }

    public void setScheduleType(Integer scheduleType) {
        this.scheduleType = scheduleType;
    }

}