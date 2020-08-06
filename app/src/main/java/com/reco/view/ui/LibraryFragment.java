package com.reco.view.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.reco.R;
import com.reco.service.model.TrackModel;
import com.reco.service.repository.APIService;
import com.reco.util.Utilities;
import com.reco.view.adapter.LibraryAdapter;
import com.reco.view.callback.APIErrorCallbacks;
import com.reco.view.callback.AdapterCallbacks;
import com.reco.viewmodel.LibraryViewModel;

import org.jetbrains.annotations.NotNull;

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

public class LibraryFragment extends Fragment implements AdapterCallbacks, APIErrorCallbacks {
    private LibraryViewModel mLibraryViewModel;
    private LibraryAdapter mLibraryAdapter;
    private APIService mAPIService;

    public LibraryFragment() {
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

        mLibraryAdapter = new LibraryAdapter(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView mRecyclerView = view.findViewById(R.id.fragment_myLibrary_recyclerview);
        Button mButton = view.findViewById(R.id.fragment_myLibrary_addMore_button);

        mLibraryViewModel = new LibraryViewModel(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.setAdapter(mLibraryAdapter);

        mLibraryViewModel.getUserLibrary().observe(this, tracks -> {
            if (tracks != null) {
                mLibraryAdapter.setLibraryTracks(tracks);
                // let the adapter know that the data changed
                mLibraryAdapter.notifyDataSetChanged();
            }
        });

        mButton.setOnClickListener(mView -> {
            MainActivity.changeToFragment((AppCompatActivity) Objects.requireNonNull(getActivity()),
                    new SearchFragment(), true,
                    "search-fragment");
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_library, container, false);
    }

    @Override
    public void onAddTrackToLibraryCallback(TrackModel track) {
        mAPIService.addTrackToLibrary(track).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response) {
                // we're not calling this from nowhere right now
            }

            @Override
            public void onFailure(@NotNull Call<Void> call, @NotNull Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRemoveTrackFromLibraryCallback(TrackModel track, int position) {
        // call the API to remove item
        mAPIService.removeTrackFromLibrary(position).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response) {
                // if item was removed remotely
                if (response.isSuccessful()) {
                    // remove it from the local library
                    Utilities.removeFromLocalLibrary((AppCompatActivity) getActivity(), track);
                    // remove item from recycler view
                    Objects.requireNonNull(mLibraryViewModel.getUserLibrary().getValue()).remove(position);
                    mLibraryAdapter.notifyDataSetChanged();
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
        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }
}