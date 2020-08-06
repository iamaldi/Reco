package com.reco.viewmodel;

import com.reco.R;
import com.reco.service.model.RecommendedUserModel;
import com.reco.service.repository.APIService;
import com.reco.view.callback.APIErrorCallbacks;
import com.reco.view.ui.HomeFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<List<RecommendedUserModel>> mUsers = new MutableLiveData<>();
    private APIErrorCallbacks mAPIErrorCallback;

    public HomeViewModel(HomeFragment homeFragment) {
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(homeFragment.getString(R.string.API_URL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService mAPIService = mRetrofit.create(APIService.class);
        mAPIErrorCallback = homeFragment;

        // call the API to get the latest recommendations
        mAPIService.getLatestRecommendedUsers().enqueue(new Callback<List<RecommendedUserModel>>() {
            @Override
            public void onResponse(@NotNull Call<List<RecommendedUserModel>> call, @NotNull Response<List<RecommendedUserModel>> response) {
                if (response.isSuccessful()) {
                    mUsers.setValue(response.body());
                } else {
                    mAPIErrorCallback.onAPIError(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<RecommendedUserModel>> call, @NotNull Throwable t) {
                // things went south, tell the user via callback
                mAPIErrorCallback.onAPIError(t.getMessage());
                mUsers.setValue(null);
            }
        });
    }

    public LiveData<List<RecommendedUserModel>> getLatestRecommendedUsers() {
        return mUsers;
    }
}
