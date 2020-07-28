package com.reco.viewmodel;

import com.reco.service.model.RecommendedUserModel;
import com.reco.service.repository.APIService;
import com.reco.util.Utilities;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecommendationsFragmentViewModel extends ViewModel {
    private final Retrofit mRetrofit;
    private final APIService mAPIService;
    private MutableLiveData<List<RecommendedUserModel>> mUsers = new MutableLiveData<>();

    public RecommendationsFragmentViewModel() {
        // retrieve data from repository and save it as mutable data
        this.mRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.url")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mAPIService = mRetrofit.create(APIService.class);

//        mAPIService.getRecommendedUsers().enqueue(new Callback<List<RecommendedUserModel>>() {
//            @Override
//            public void onResponse(Call<List<RecommendedUserModel>> call, Response<List<RecommendedUserModel>> response) {
//                if(response.isSuccessful()){
//                    mUsers.setValue(response.body());
//                } else {
//                    // some error happened, let the user know - and log it
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<RecommendedUserModel>> call, Throwable t) {
//                // things went south
//            }
//        });

        mUsers.setValue(Utilities.generateRecommendedUsers());
    }

    public LiveData<List<RecommendedUserModel>> getRecommendedUsers() {
        return mUsers;
    }


}
