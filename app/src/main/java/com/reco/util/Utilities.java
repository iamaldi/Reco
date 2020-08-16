package com.reco.util;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.reco.service.model.TrackModel;
import com.reco.service.model.UserProfileModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class Utilities {
    public static void setLoggedInStatus(AppCompatActivity appCompatActivity, boolean loggedInStatus) {
        appCompatActivity.getSharedPreferences("hasLocalProfile", Context.MODE_PRIVATE)
                .edit()
                .putBoolean("hasLocalProfile", loggedInStatus)
                .apply();
    }

    public static boolean getLoggedInStatus(AppCompatActivity appCompatActivity) {
        return appCompatActivity.getSharedPreferences("hasLocalProfile", Context.MODE_PRIVATE).getBoolean("hasLocalProfile", false);
    }

    public static void clearLocalData(AppCompatActivity appCompatActivity) {
        // clear everything we've saved locally
        appCompatActivity.getSharedPreferences("user", Context.MODE_PRIVATE)
                .edit().clear().apply();

        appCompatActivity.getSharedPreferences("library", Context.MODE_PRIVATE)
                .edit().clear().apply();
    }

    public static void saveLocalUser(AppCompatActivity mActivity, UserProfileModel user) {
        mActivity.getSharedPreferences("user", Context.MODE_PRIVATE)
                .edit()
                .putString("user", new Gson().toJson(user))
                .apply();
    }

    public static UserProfileModel getLocalUser(AppCompatActivity mActivity) {
        SharedPreferences mPrefs;
        mPrefs = mActivity.getSharedPreferences("user", Context.MODE_PRIVATE);
        return new Gson().fromJson(mPrefs.getString("user", ""), UserProfileModel.class);
    }

    public static void deleteLocalUser(AppCompatActivity appCompatActivity) {
        appCompatActivity.getSharedPreferences("user", Context.MODE_PRIVATE)
                .edit()
                .remove("user")
                .apply();
    }

    public static void saveLocalLibrary(AppCompatActivity appCompatActivity, List<TrackModel> tracks) {
        appCompatActivity.getSharedPreferences("library", Context.MODE_PRIVATE)
                .edit()
                .putString("library", new Gson().toJson(tracks))
                .apply();
    }

    public static List<TrackModel> getLocalLibrary(AppCompatActivity appCompatActivity) {
        SharedPreferences mPrefs;
        mPrefs = appCompatActivity.getSharedPreferences("library", Context.MODE_PRIVATE);
        // get type of TrackModel class - needed by Gson
        Type trackListType = new TypeToken<ArrayList<TrackModel>>() {
        }.getType();

        return new Gson().fromJson(mPrefs.getString("library", ""), trackListType);
    }

    public static void addTrackToLocalLibrary(AppCompatActivity appCompatActivity, TrackModel track) {
        // get current copy of library
        List<TrackModel> tracksList = getLocalLibrary(appCompatActivity);
        // add the track to the copy
        if (tracksList != null) {
            // check if track already exists
            if (tracksList.stream().noneMatch(trackModel -> trackModel.getTitle().equalsIgnoreCase(track.getTitle()))) {
                tracksList.add(track);
                // save the new copy
                saveLocalLibrary(appCompatActivity, tracksList);
            }
        }
    }

    public static void removeTrackFromLocalLibrary(AppCompatActivity appCompatActivity, TrackModel track) {
        // get current copy of library
        List<TrackModel> tracksList = getLocalLibrary(appCompatActivity);
        // remove the track to the copy
        if (tracksList != null) {
            tracksList.removeIf(mTrack -> mTrack.getTitle().equalsIgnoreCase(track.getTitle()));
            // save the new copy
            saveLocalLibrary(appCompatActivity, tracksList);
        }
    }


    public static String saveToInternalStorage(AppCompatActivity appCompatActivity, Bitmap bitmap) {
        ContextWrapper contextWrapper = new ContextWrapper(appCompatActivity.getApplicationContext());
        File dataDirectory = contextWrapper.getDir("profileImage", Context.MODE_PRIVATE);
        File profileImagePath = new File(dataDirectory, "profile.jpg");
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(profileImagePath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return profileImagePath.toString();
    }

}
