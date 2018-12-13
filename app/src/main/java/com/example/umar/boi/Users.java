package com.example.umar.boi;

public class Users {


    String userId,name,email,password;

    public Users(){

    }

    public Users(String userId, String name, String email, String password) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }





}
