package com.reco.viewmodel;

import com.reco.service.model.TrackModel;
import com.reco.service.repository.APIService;
import com.reco.util.Utilities;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LibraryViewModel extends ViewModel {
    private final Retrofit mRetrofit;
    private final APIService mAPIService;
    private MutableLiveData<List<TrackModel>> mTracks = new MutableLiveData<>();

    public LibraryViewModel() {
        this.mRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.url")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mAPIService = mRetrofit.create(APIService.class);

        // retrieve library data from API
//        mAPIService.getUserLibrary().enqueue(new Callback<List<TrackModel>>() {
//            @Override
//            public void onResponse(Call<List<TrackModel>> call, Response<List<TrackModel>> response) {
//                if(response.isSuccessful()){
//                    mTracks.setValue(response.body());
//                } else {
//                    // something went wrong, tell user - log it
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<TrackModel>> call, Throwable t) {
//                // things went real bad
//            }
//        });

        mTracks.setValue(Utilities.generateUserLibrary());
//        Log.d("RECOSIZE", "RecommendationsViewModel: " + Utilities.generateUserLibrary().size());
    }

    public LiveData<List<TrackModel>> getUserLibrary() {
        return mTracks;
    }

}
