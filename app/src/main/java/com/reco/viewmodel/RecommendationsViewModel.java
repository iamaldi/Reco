package com.reco.viewmodel;

import com.reco.R;
import com.reco.service.model.RecommendedUserModel;
import com.reco.service.repository.APIService;
import com.reco.view.callback.APIErrorCallbacks;
import com.reco.view.ui.RecommendationsFragment;

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

public class RecommendationsViewModel extends ViewModel {
    private APIErrorCallbacks apiErrorCallbacks;
    private MutableLiveData<List<RecommendedUserModel>> mUsers = new MutableLiveData<>();

    public RecommendationsViewModel(RecommendationsFragment recommendationsFragment) {
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(recommendationsFragment.getString(R.string.API_URL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService mAPIService = mRetrofit.create(APIService.class);
        apiErrorCallbacks = recommendationsFragment;

        mAPIService.getRecommendedUsers().enqueue(new Callback<List<RecommendedUserModel>>() {
            @Override
            public void onResponse(@NotNull Call<List<RecommendedUserModel>> call, @NotNull Response<List<RecommendedUserModel>> response) {
                if (response.isSuccessful()) {
                    mUsers.setValue(response.body());
                } else {
                    // some error happened, let the user know - and log it
                    apiErrorCallbacks.onAPIError(response.message());
                    mUsers.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<RecommendedUserModel>> call, @NotNull Throwable t) {
                // things went south
                apiErrorCallbacks.onAPIError(t.getMessage());
                mUsers.setValue(null);
            }
        });
    }

    public LiveData<List<RecommendedUserModel>> getRecommendedUsers() {
        return mUsers;
    }


}
