package com.reco.service.model;

import java.util.List;


public class RecommendationsModel {
    private List<UserModel> recommendedUsers;

    public RecommendationsModel(List<UserModel> recommendedUsers) {
        this.recommendedUsers = recommendedUsers;
    }

    public List<UserModel> getRecommendedUsers() {
        return recommendedUsers;
    }

    public void setRecommendedUsers(List<UserModel> recommendedUsers) {
        this.recommendedUsers = recommendedUsers;
    }

    public void addRecommendedUser(UserModel recommendedUser) {
        this.recommendedUsers.add(recommendedUser);
    }
}
