package com.reco.service.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserRegisterModel {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("display_name")
    @Expose
    private String displayName;
    @SerializedName("messenger_url")
    @Expose
    private String messengerUrl;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("password_repeat")
    @Expose
    private String passwordRepeat;

    /**
     * @param password
     * @param displayName
     * @param messengerUrl
     * @param passwordRepeat
     * @param username
     */
    public UserRegisterModel(String username, String displayName, String messengerUrl, String password, String passwordRepeat) {
        super();
        this.username = username;
        this.displayName = displayName;
        this.messengerUrl = messengerUrl;
        this.password = password;
        this.passwordRepeat = passwordRepeat;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getMessengerUrl() {
        return messengerUrl;
    }

    public void setMessengerUrl(String messengerUrl) {
        this.messengerUrl = messengerUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }

}