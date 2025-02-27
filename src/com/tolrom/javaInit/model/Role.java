package com.tolrom.javaInit.model;

public class Role {

    // Attributes

    private int id;
    private String roleName;

    // Getters & Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    // Constructors

    public Role(){}
    public Role(String roleName){
        this.roleName = roleName;
    }


}
