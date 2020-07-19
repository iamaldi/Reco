package com.reco.service.model;

import java.util.List;

public class MyLibraryModel {
    private List<TrackModel> trackList;

    public MyLibraryModel(List<TrackModel> trackList) {
        this.trackList = trackList;
    }

    public List<TrackModel> getTrackList() {
        return trackList;
    }

    public void setTrackList(List<TrackModel> trackList) {
        this.trackList = trackList;
    }
}
