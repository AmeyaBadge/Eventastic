package com.amykoder.eventastic;

public class UserClass {
    public String email;
    public String password;
    public UserClass(){
        //Default Constructor
    }
    public UserClass(String email, String password ){
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
