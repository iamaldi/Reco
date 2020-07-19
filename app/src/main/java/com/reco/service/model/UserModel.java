package com.reco.service.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.lifecycle.LiveData;

public class UserModel extends UserBaseModel {
    @SerializedName("name")
    @Expose
    private String name;

    public UserModel(String name, String username) {
        super();
        this.name = name;
        super.setUsername(username);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}