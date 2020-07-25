package com.reco.viewmodel;

import com.reco.service.model.TrackModel;
import com.reco.service.repository.LastFMRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SearchFragmentViewModel extends ViewModel {
    private MutableLiveData<List<TrackModel>> mTrackSearchResults = new MutableLiveData<>();

    public SearchFragmentViewModel() {
        // retrieve data from repository and save it as mutable data
        mTrackSearchResults.setValue(LastFMRepository.searchTrack("test", "test"));
    }

    // provide the results as livedata
    public LiveData<List<TrackModel>> getGeneratedTracks() {
        return mTrackSearchResults;
    }
}
