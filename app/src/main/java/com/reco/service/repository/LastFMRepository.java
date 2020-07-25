package com.reco.service.repository;

import com.reco.service.model.TrackModel;
import com.reco.util.Utilities;

import java.util.List;

public class LastFMRepository {

    // search tracks
    public static List<TrackModel> searchTrack(String a, String b) {
        return Utilities.generateTracks();
    }

    // get track info
    public static TrackModel getTrackInfo(String a, String b) {
        return Utilities.generateTracks().get(0);
    }
}
