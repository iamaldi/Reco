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
import com.reco.util.Utilities;
import com.reco.view.adapter.LibraryAdapter;
import com.reco.view.adapter.SearchAdapter;
import com.reco.view.callback.APIErrorCallbacks;
import com.reco.view.callback.AdapterCallbacks;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
    private APIService mAPIService;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            return;
        }
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.API_URL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mAPIService = mRetrofit.create(APIService.class);

        mSearchAdapter = new SearchAdapter(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView mRecyclerView = view.findViewById(R.id.fragment_search_recyclerView);
        SearchView searchView = view.findViewById(R.id.fragment_search_searchView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mSearchAdapter);

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
                            if (tracks != null) {
                                mSearchAdapter.setLocalLibrary(Utilities.getLocalLibrary((AppCompatActivity) Objects.requireNonNull(getActivity())));
                                mSearchAdapter.setSearchTracksList(tracks);
                                mSearchAdapter.notifyDataSetChanged();
                            }
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
                    // add track to local library
                    Utilities.addToLocalLibrary((AppCompatActivity) getActivity(), track);
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
                    // remove track from local library
                    Utilities.removeFromLocalLibrary((AppCompatActivity) getActivity(), track);
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