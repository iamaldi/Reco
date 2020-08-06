package com.reco.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.reco.service.model.TrackModel;
import com.reco.service.model.UserProfileModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class Utilities {
    public static boolean isUserLoggedIn(AppCompatActivity mActivity) {
        // do we have a user object locally
        return getLocalUser(mActivity) != null;
    }

    public static void clearLocalData(AppCompatActivity appCompatActivity){
        SharedPreferences mPrefs;
        mPrefs = appCompatActivity.getPreferences(Context.MODE_PRIVATE);
        // clear everything we've saved locally
        mPrefs.edit().clear().apply();
    }

    public static void saveLocalUser(AppCompatActivity mActivity, UserProfileModel user) {
        SharedPreferences mPrefs;
        SharedPreferences.Editor mPrefsEditor;

        mPrefs = mActivity.getPreferences(Context.MODE_PRIVATE);

        mPrefsEditor = mPrefs.edit();
        mPrefsEditor.putString("user", new Gson().toJson(user));

        mPrefsEditor.apply();
    }

    public static UserProfileModel getLocalUser(AppCompatActivity mActivity) {
        SharedPreferences mPrefs;
        mPrefs = mActivity.getPreferences(Context.MODE_PRIVATE);

        UserProfileModel user;
        user = new Gson().fromJson(mPrefs.getString("user", ""), UserProfileModel.class);

        return user;
    }

    public static void saveLocalLibrary(AppCompatActivity appCompatActivity, List<TrackModel> tracks) {
        SharedPreferences mPrefs;
        SharedPreferences.Editor mPrefsEditor;

        mPrefs = appCompatActivity.getPreferences(Context.MODE_PRIVATE);
        mPrefsEditor = mPrefs.edit();

        mPrefsEditor.putString("library", new Gson().toJson(tracks));
        mPrefsEditor.apply();
    }

    public static List<TrackModel> getLocalLibrary(AppCompatActivity appCompatActivity) {
        SharedPreferences mPrefs;
        mPrefs = appCompatActivity.getPreferences(Context.MODE_PRIVATE);
        List<TrackModel> tracks;

        // get type of TrackModel class - needed by Gson
        Type trackListType = new TypeToken<ArrayList<TrackModel>>() {
        }.getType();

        tracks = new Gson().fromJson(mPrefs.getString("library", ""), trackListType);

        return tracks;
    }

    public static void addToLocalLibrary(AppCompatActivity appCompatActivity, TrackModel track) {
        // get current copy of library
        List<TrackModel> tracksList = getLocalLibrary(appCompatActivity);
        // add the track to the copy
        if (tracksList != null) {
            // check if track already exists
            if(tracksList.stream().noneMatch(trackModel -> trackModel.getTitle().equalsIgnoreCase(track.getTitle()))){
                tracksList.add(track);
                // save the new copy
                saveLocalLibrary(appCompatActivity, tracksList);
            }
        }
    }

    public static void removeFromLocalLibrary(AppCompatActivity appCompatActivity, TrackModel track) {
        // get current copy of library
        List<TrackModel> tracksList = getLocalLibrary(appCompatActivity);
        // remove the track to the copy
        if (tracksList != null) {
            tracksList.removeIf(mTrack -> mTrack.getTitle().equalsIgnoreCase(track.getTitle()));
            // save the new copy
            saveLocalLibrary(appCompatActivity, tracksList);
        }
    }
}
