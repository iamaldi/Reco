package com.reco.view.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.reco.R;
import com.reco.service.model.TrackModel;
import com.reco.service.repository.APIService;
import com.reco.view.adapter.SearchAdapter;
import com.reco.view.callback.SearchAdapterCallback;
import com.reco.viewmodel.SearchFragmentViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchFragment extends Fragment implements SearchAdapterCallback {
    private SearchFragmentViewModel mSearchFragmentViewModel;
    private SearchAdapter mSearchAdapter;
    private RecyclerView mRecyclerView;

    private Retrofit mRetrofit;
    private APIService mAPIService;

    public SearchFragment() {
        // we have to do this on every fragment -
        // alternatively we could use DI but that takes a LOT more time
        this.mRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.url")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mAPIService = mRetrofit.create(APIService.class);
    }

    @Override
    public void onItemClickedCallback(TrackModel track) {
        // test - start - shows that we can open a new fragment when
        Log.d("RECOSIZE", "onClick: CLICKED");
        Toast.makeText(getContext(), "Adding to library" + track.getName(), Toast.LENGTH_SHORT).show();
        // test - end

        // call repository to save the track/song to library
        mAPIService.addTrackToLibrary(track).enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                // when callback is void none of these are going to execute,
                // that means, No Toast!
                Toast.makeText(getContext(), "Added to library!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // things went bazooka here - or maybe there's not internet connection available
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.fragment_search_recyclerView);

        mSearchFragmentViewModel = new SearchFragmentViewModel();

        // pass data to fragment via adapter
        initRecyclerView();

        // observe data and notify adapter when it changes
        mSearchFragmentViewModel.getGeneratedTracks().observe(this, new Observer<List<TrackModel>>() {
            @Override
            public void onChanged(List<TrackModel> trackModels) {
                // test toast
                Toast.makeText(getContext(), "List size: " + trackModels.size(), Toast.LENGTH_SHORT).show();
                mSearchAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    public void initRecyclerView() {
        // get livedata from the viewModel - this gets a list of tracks
        mSearchAdapter = new SearchAdapter(this, mSearchFragmentViewModel.getGeneratedTracks().getValue());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.setAdapter(mSearchAdapter);
    }
}