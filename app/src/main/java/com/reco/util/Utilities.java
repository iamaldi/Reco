package com.reco.util;

import com.reco.service.model.TrackModel;
import com.reco.service.model.UserModel;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;

public class Utilities {
    public static UserModel generateUser() {
        return new UserModel("TestUser1", "test_user_1");
    }

    public static MutableLiveData<List<TrackModel>> generateTracks() {
        MutableLiveData<List<TrackModel>> mGeneratedTracks = new MutableLiveData<>();
        List<TrackModel> generatedTracks = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            generatedTracks.add(new TrackModel("Track" + i, "Artist" + i, "https://URL/" + i, "https://IMAGEURL/" + i));
        }
        // this will notify all observers
        mGeneratedTracks.setValue(generatedTracks);
        return mGeneratedTracks;
    }
}
