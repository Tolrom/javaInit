package com.tolrom.javaInit.model;

import java.util.ArrayList;
import java.util.Date;

public class Task {

    // Attributes

    private int id;
    private String title;
    private String content;
    private Date createdAt;
    private Date endDate;
    private boolean status;
    private User user;
    private ArrayList<Category> categories;

    // Getters & Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void addCategory(Category category) {
        this.categories.add(category);
    }

    public void removeCategory(Category category) {
        this.categories.remove(category);
    }

    // Constructors

    public Task(){
        this.categories = new ArrayList<>();
    }
    public Task(String title, String content, User user){
        this.title = title;
        this.content = content;
        this.user = user;
        this.categories = new ArrayList<>();
    }

}
