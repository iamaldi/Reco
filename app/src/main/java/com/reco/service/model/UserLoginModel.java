package com.reco.service.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserLoginModel extends UserBaseModel {

    @SerializedName("password")
    @Expose
    private String password;

    public UserLoginModel(String username, String password) {
        super(username);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}