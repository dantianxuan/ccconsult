package com.ccconsult.pojo;

/**
 * Level entity. @author MyEclipse Persistence Tools
 */

public class Level extends com.ccconsult.base.ToString implements java.io.Serializable {

    // Fields    

    private Integer id;
    private String  name;
    private String  description;
    private String  level;

    // Constructors

    /** default constructor */
    public Level() {
    }

    /** full constructor */
    public Level(String name, String description, String level) {
        this.name = name;
        this.description = description;
        this.level = level;
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

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

}