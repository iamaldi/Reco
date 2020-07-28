package com.reco.viewmodel;

import com.reco.service.model.TrackModel;
import com.reco.service.repository.APIService;
import com.reco.service.repository.LastFMService;
import com.reco.util.Utilities;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchViewModel extends ViewModel {
    private final Retrofit mRetrofit;
    private final LastFMService mLastFMService;
    private MutableLiveData<List<TrackModel>> mTrackSearchResults = new MutableLiveData<>();

    public SearchViewModel() {
        // retrieve data from repository and save it as mutable data
        this.mRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.url")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mLastFMService = mRetrofit.create(LastFMService.class);

//        mLastFMService.searchTrack("test_title", "test_api_key").enqueue(new Callback<List<TrackModel>>() {
//            @Override
//            public void onResponse(Call<List<TrackModel>> call, Response<List<TrackModel>> response) {
//                if(response.isSuccessful()){
//                    mTrackSearchResults.setValue(response.body());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<TrackModel>> call, Throwable t) {
//
//            }
//        });

        // test - get test generated data from utility
        mTrackSearchResults.setValue(Utilities.generateTracks());
    }

    // provide the results as livedata
    public LiveData<List<TrackModel>> getGeneratedTracks() {
        return mTrackSearchResults;
    }
}
