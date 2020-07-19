package com.reco.service.repository;

import com.reco.service.model.TrackModel;
import com.reco.util.Utilities;

import java.util.List;

import androidx.lifecycle.MutableLiveData;

public class LastFMRepository {

    // search tracks
    public MutableLiveData<List<TrackModel>> searchTrack(String a, String b){
        return Utilities.generateTracks();
    }

    // get track info
}
