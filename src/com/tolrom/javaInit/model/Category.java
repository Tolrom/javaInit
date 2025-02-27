package com.tolrom.javaInit.model;

public class Category {


    // Attributes

    private int id;
    private String categoryName;

    // Getters & Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    // Constructors

    public Category(){}
    public Category(String categoryName){
        this.categoryName = categoryName;
    }


}
