package com.ccconsult.pojo;

/**
 * Diag entity. @author MyEclipse Persistence Tools
 */

public class Diag extends com.ccconsult.base.ToString implements java.io.Serializable {

    // Fields    

    private Integer id;
    private String  gmtCreate;
    private String  token;
    private String  gmtEnd;
    private String  creatorRole;
    private Integer creatorId;
    private Integer relId;
    private String  relTheme;

    // Constructors

    /** default constructor */
    public Diag() {
    }

    /** full constructor */
    public Diag(String gmtCreate, String token, String gmtEnd, String creatorRole,
                Integer creatorId, Integer relId, String relTheme) {
        this.gmtCreate = gmtCreate;
        this.token = token;
        this.gmtEnd = gmtEnd;
        this.creatorRole = creatorRole;
        this.creatorId = creatorId;
        this.relId = relId;
        this.relTheme = relTheme;
    }

    // Property accessors

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGmtCreate() {
        return this.gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getGmtEnd() {
        return this.gmtEnd;
    }

    public void setGmtEnd(String gmtEnd) {
        this.gmtEnd = gmtEnd;
    }

    public String getCreatorRole() {
        return this.creatorRole;
    }

    public void setCreatorRole(String creatorRole) {
        this.creatorRole = creatorRole;
    }

    public Integer getCreatorId() {
        return this.creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getRelId() {
        return this.relId;
    }

    public void setRelId(Integer relId) {
        this.relId = relId;
    }

    public String getRelTheme() {
        return this.relTheme;
    }

    public void setRelTheme(String relTheme) {
        this.relTheme = relTheme;
    }

}