package com.reco.view.callback;

import com.reco.service.model.TrackModel;

public interface AdapterCallbacks {
    void onAddTrackToLibrary(TrackModel track);
    void onRemoveTrackFromLibrary(TrackModel track, int position);
}
