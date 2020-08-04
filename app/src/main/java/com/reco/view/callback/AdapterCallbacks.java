package com.reco.view.callback;

import com.reco.service.model.TrackModel;

public interface AdapterCallbacks {
    void onAddTrackToLibraryCallback(TrackModel track);
    void onRemoveTrackFromLibraryCallback(TrackModel track, int position);
}
