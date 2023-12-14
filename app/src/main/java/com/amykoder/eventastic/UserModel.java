package com.amykoder.eventastic;

public class UserModel {
    public String email;
    public String password;
    public UserModel(){
        //Default Constructor
    }
    public UserModel(String email, String password ){
        this.email = email.trim();
        this.password = password.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String username) {
        this.email = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
