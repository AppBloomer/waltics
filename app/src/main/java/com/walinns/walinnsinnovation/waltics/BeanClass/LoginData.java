package com.walinns.walinnsinnovation.waltics.BeanClass;

/**
 * Created by walinnsinnovation on 12/01/18.
 */

public class LoginData {
    String username;
    String email;
    public LoginData(String username_, String email_){
        this.username = username_;
        this.email = email_;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
