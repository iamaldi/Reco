package com.reco.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.reco.service.model.RecommendedUserModel;
import com.reco.service.model.TrackModel;
import com.reco.service.model.UserProfileModel;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class Utilities {
    public static UserProfileModel generateUser() {
        return new UserProfileModel("TestUser1", "test_user_1", "http://m.me/MYPROFILE", "m.me/JhnDoe");
    }

    public static List<TrackModel> generateUserLibrary() {
        List<TrackModel> userLibrary = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            userLibrary.add(new TrackModel("Socks" + i, "Arctic Monkeys" + i));
        }

        return userLibrary;
    }

    public static List<RecommendedUserModel> generateRecommendedUsers() {
        List<RecommendedUserModel> recommendedUsers = new ArrayList<>();

        recommendedUsers.add(new RecommendedUserModel("John", "john_a_1", "", "http://m.me/john", 75));
        recommendedUsers.add(new RecommendedUserModel("Bob", "bob_s_2", "", "http://m.me/bob", 36));
        recommendedUsers.add(new RecommendedUserModel("Regino", "reggy29", "", "http://m.me/reggy", 22));
        recommendedUsers.add(new RecommendedUserModel("Aldi", "lol_who_me", "", "http://m.me/aa", 16));
        return recommendedUsers;
    }

    public static List<TrackModel> generateTracks() {
        List<TrackModel> generatedTracks = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            generatedTracks.add(new TrackModel("Track" + i, "Artist" + i));
        }
        return generatedTracks;
    }

    public static boolean isUserLoggedIn(AppCompatActivity mActivity){
        if(getUser(mActivity) != null){
            // we have a user object locally
            return true;
        } else {
            return false;
        }
    }

    public static void saveUser(AppCompatActivity mActivity, UserProfileModel user) {
        SharedPreferences mPrefs;
        SharedPreferences.Editor mPrefsEditor;

        mPrefs = mActivity.getPreferences(Context.MODE_PRIVATE);

        mPrefsEditor = mPrefs.edit();
        mPrefsEditor.putString("user", new Gson().toJson(user));

        // debug
        Log.i("RECO-SaveUser", user.getDisplayName());
        mPrefsEditor.commit();
    }

    public static UserProfileModel getUser(AppCompatActivity mActivity) {
        SharedPreferences mPrefs;
        mPrefs = mActivity.getPreferences(Context.MODE_PRIVATE);
        UserProfileModel user;

        user = new Gson().fromJson(mPrefs.getString("user", ""), UserProfileModel.class);
        Log.i("RECO-SaveUser", user.getDisplayName());

        return user;
    }
}
