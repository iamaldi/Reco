package com.reco.service.repository;

import com.reco.service.model.RecommendedUserModel;
import com.reco.service.model.TrackModel;
import com.reco.service.model.UserLoginModel;
import com.reco.service.model.UserModel;
import com.reco.service.model.UserRegisterModel;
import com.reco.util.Utilities;

import java.util.List;

public class APIRepository {
    public void userRegister(UserRegisterModel mUser) {
    }

    public void userLogin(UserLoginModel mUser) {
    }

    public UserModel getUserProfile() {
        return Utilities.generateUser();
    }

    public void updateUserProfile(UserModel mUser) {
    }

    public void deleterUserProfile() {
    }

    public void addTrackToLibrary(TrackModel mTrack) {
    }

    public void removeTrackFromLibrary(int mTrackId) {
    }

    public List<TrackModel> getUserLibrary() {
        return Utilities.generateUserLibrary();
    }

    public List<RecommendedUserModel> getRecommendedUsers() {
        return Utilities.generateRecommendedUsers();
    }

    public List<RecommendedUserModel> getLatestRecommendedUsers() {
        return Utilities.generateRecommendedUsers().subList(0, 0);
    }
}
