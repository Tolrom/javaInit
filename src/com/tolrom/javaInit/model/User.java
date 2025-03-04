package com.tolrom.javaInit.model;

public class User {

    // Attributes

    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Role role = new Role("USER");

    // Constructors

    public User(){}
    public User(String firstname, String lastname, String email, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    // Getters & Setters

    public int getId(){
        return this.id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    // Methods

    @Override
    public String toString() {
        return "User{" +
                "id='" + this.id + '\'' +
                ", firstname='" + this.firstname + '\'' +
                ", lastname='" + this.lastname + '\'' +
                ", email='" + this.email + '\'' +
                ", role="+ this.role.getRoleName()+ '\''+
                '}';
    }
}
