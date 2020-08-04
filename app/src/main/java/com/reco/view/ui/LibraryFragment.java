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
import com.reco.view.adapter.LibraryAdapter;
import com.reco.view.callback.AdapterCallbacks;
import com.reco.viewmodel.LibraryViewModel;

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

public class LibraryFragment extends Fragment implements AdapterCallbacks {
    private LibraryViewModel mLibraryViewModel;
    private LibraryAdapter mLibraryAdapter;
    private RecyclerView mRecyclerView;
    private Button mButton;

    private Retrofit mRetrofit;
    private APIService mAPIService;

    public LibraryFragment() {
        this.mRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.url")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mAPIService = mRetrofit.create(APIService.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.fragment_myLibrary_recyclerview);
        mButton = view.findViewById(R.id.fragment_myLibrary_addMore_button);
        mLibraryViewModel = new LibraryViewModel();

        initRecyclerView();

        mButton.setOnClickListener(mView -> {
            MainActivity.changeToFragment((AppCompatActivity) getActivity(),
                    new SearchFragment(), true,
                    "search-from-library");
        });

        mLibraryViewModel.getUserLibrary().observe(this, trackModels -> {
            Toast.makeText(getContext(), "Library: " + trackModels.size(), Toast.LENGTH_SHORT).show();
            mLibraryAdapter.notifyDataSetChanged();
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_library, container, false);
    }

    public void initRecyclerView() {
        mLibraryAdapter = new LibraryAdapter(this, mLibraryViewModel.getUserLibrary().getValue());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.setAdapter(mLibraryAdapter);
    }

    @Override
    public void onAddTrackToLibraryCallback(TrackModel track) {
        mLibraryViewModel.getUserLibrary().getValue().add(track);
        mLibraryAdapter.notifyDataSetChanged();

        mAPIService.addTrackToLibrary(track).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // item added
                } else {
                    // oops, smth went wrong
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // whoops, no internet?
            }
        });
    }

    @Override
    public void onRemoveTrackFromLibraryCallback(TrackModel track, int position) {
        // debug toast
        Toast.makeText(getContext(), "REMOVE", Toast.LENGTH_SHORT).show();

        // remove item from recycler view
        mLibraryViewModel.getUserLibrary().getValue().remove(position);
        mLibraryAdapter.notifyDataSetChanged();

        // call the API to remove item
        mAPIService.removeTrackFromLibrary(1).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // item removed
                } else {
                    // something went wrong
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // things went a bit south
            }
        });
    }
}