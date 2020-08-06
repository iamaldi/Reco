package com.reco.viewmodel;

import com.reco.R;
import com.reco.service.model.TrackModel;
import com.reco.service.repository.APIService;
import com.reco.util.Utilities;
import com.reco.view.callback.APIErrorCallbacks;
import com.reco.view.ui.LibraryFragment;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LibraryViewModel extends ViewModel {
    private final Retrofit mRetrofit;
    private final APIService mAPIService;
    private APIErrorCallbacks apiErrorCallbacks;
    private MutableLiveData<List<TrackModel>> mTracks = new MutableLiveData<>();

    public LibraryViewModel(LibraryFragment libraryFragment) {
        this.mRetrofit = new Retrofit.Builder()
                .baseUrl(libraryFragment.getString(R.string.API_URL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mAPIService = mRetrofit.create(APIService.class);
        apiErrorCallbacks = libraryFragment;

        List<TrackModel> tracks = Utilities.getLocalLibrary((AppCompatActivity) libraryFragment.getActivity());

        // check if we have a local copy of the library
        if (tracks != null) {
            mTracks.setValue(tracks);
        } else {
            // retrieve library data from API
            mAPIService.getUserLibrary().enqueue(new Callback<List<TrackModel>>() {
                @Override
                public void onResponse(Call<List<TrackModel>> call, Response<List<TrackModel>> response) {
                    if (response.isSuccessful()) {
                        List<TrackModel> tracks = response.body();
                        mTracks.setValue(tracks);
                        // save data locally
                        Utilities.saveLocalLibrary((AppCompatActivity) libraryFragment.getActivity(), tracks);
                    } else {
                        apiErrorCallbacks.onAPIError(response.message());
                        mTracks.setValue(null);
                    }
                }

                @Override
                public void onFailure(Call<List<TrackModel>> call, Throwable t) {
                    apiErrorCallbacks.onAPIError(t.getMessage());
                    mTracks.setValue(null);
                }
            });
        }
    }

    public LiveData<List<TrackModel>> getUserLibrary() {
        return mTracks;
    }
}
