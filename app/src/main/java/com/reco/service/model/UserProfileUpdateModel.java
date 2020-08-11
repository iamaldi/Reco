package com.reco.service.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserProfileUpdateModel {

    @SerializedName("display_name")
    @Expose
    private String displayName;
    @SerializedName("img_url")
    @Expose
    private String imgUrl;
    @SerializedName("messenger_url")
    @Expose
    private String messengerUrl;

    /**
     * @param imgUrl
     * @param displayName
     * @param messengerUrl
     */
    public UserProfileUpdateModel(String displayName, String imgUrl, String messengerUrl) {
        super();
        this.displayName = displayName;
        this.imgUrl = imgUrl;
        this.messengerUrl = messengerUrl;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getMessengerUrl() {
        return messengerUrl;
    }

    public void setMessengerUrl(String messengerUrl) {
        this.messengerUrl = messengerUrl;
    }

}