package com.reco.view.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.reco.R;
import com.reco.service.model.TrackModel;
import com.reco.service.repository.APIService;
import com.reco.view.adapter.SearchAdapter;
import com.reco.view.callback.APIErrorCallbacks;
import com.reco.view.callback.AdapterCallbacks;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchFragment extends Fragment implements AdapterCallbacks, APIErrorCallbacks {
    private SearchAdapter mSearchAdapter;
    private RecyclerView mRecyclerView;
    private SearchView searchView;

    private Retrofit mRetrofit;
    private APIService mAPIService;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mRetrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.API_URL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mAPIService = mRetrofit.create(APIService.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.fragment_search_recyclerView);
        searchView = view.findViewById(R.id.fragment_search_searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // query the api for tracks as user is typing
                mAPIService.searchTracks(query).enqueue(new Callback<List<TrackModel>>() {
                    @Override
                    public void onResponse(@NotNull Call<List<TrackModel>> call, @NotNull Response<List<TrackModel>> response) {
                        if (response.isSuccessful()) {
                            List<TrackModel> tracks = response.body();
                            addTrackToLibrary(tracks);
                        } else {
                            Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<List<TrackModel>> call, @NotNull Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                return false;
            }
        });
    }

    public void addTrackToLibrary(List<TrackModel> tracks) {
        mSearchAdapter = new SearchAdapter(this, tracks);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mSearchAdapter);

        // let the fragment know that data changed
        mSearchAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onAddTrackToLibraryCallback(TrackModel track) {
        // call repository to save the track/song to library
        mAPIService.addTrackToLibrary(track).enqueue(new Callback<Void>() {

            @Override
            public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Added to library!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<Void> call, @NotNull Throwable t) {
                // things went bazooka here - or maybe there's not internet connection available
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRemoveTrackFromLibraryCallback(TrackModel track, int position) {
        // call API to remove the track
        mAPIService.removeTrackFromLibrary(111).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Removed from library!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<Void> call, @NotNull Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onAPIError(String errorMsg) {

    }
}