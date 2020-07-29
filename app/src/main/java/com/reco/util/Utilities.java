package com.reco.util;

import com.reco.service.model.RecommendedUserModel;
import com.reco.service.model.TrackModel;
import com.reco.service.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class Utilities {
    public static UserModel generateUser() {
        return new UserModel("TestUser1", "test_user_1");
    }

    public static List<TrackModel> generateUserLibrary() {
        List<TrackModel> userLibrary = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            userLibrary.add(new TrackModel("Socks" + i, "Arctic Monkeys" + i, "https://URL/" + i, "https://IMAGEURL/" + i));
        }

        return userLibrary;
    }

    public static List<RecommendedUserModel> generateRecommendedUsers() {
        List<RecommendedUserModel> recommendedUsers = new ArrayList<>();

        recommendedUsers.add(new RecommendedUserModel("John", "john_a_1", 15));
        recommendedUsers.add(new RecommendedUserModel("Bob", "bob_s_2", 75));
        recommendedUsers.add(new RecommendedUserModel("Regino", "reggy29", 87));
        recommendedUsers.add(new RecommendedUserModel("Aldi", "lol_who_me", 34));
        recommendedUsers.add(new RecommendedUserModel("Aldi", "lol_who_me", 34));

        return recommendedUsers;
    }

    public static List<TrackModel> generateTracks() {
        List<TrackModel> generatedTracks = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            generatedTracks.add(new TrackModel("Track" + i, "Artist" + i, "https://URL/" + i, "https://IMAGEURL/" + i));
        }
        return generatedTracks;
    }
}
