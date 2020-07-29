package com.reco.view.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.reco.R;
import com.reco.view.adapter.HomeAdapter;
import com.reco.viewmodel.HomeViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends Fragment {
    private HomeViewModel mHomeViewModel;
    private HomeAdapter mHomeAdapter;
    private RecyclerView mRecyclerView;
    private AppCompatImageButton mSettingsButton;
    private Button mSearchButton, mMyLibraryButton;

    public HomeFragment() {
        // Required empty public constructor


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSettingsButton = view.findViewById(R.id.fragment_home_profile_imageButton);
        mSearchButton = view.findViewById(R.id.fragment_home_search_button);
        mMyLibraryButton = view.findViewById(R.id.fragment_home_myLib_button);

        mRecyclerView = view.findViewById(R.id.fragment_home_recyclerView);
        mHomeViewModel = new HomeViewModel();

        initRecyclerView();

        mSettingsButton.setOnClickListener(mView -> {
            Toast.makeText(mView.getContext(), "Settings", Toast.LENGTH_SHORT).show();
        });

        mSearchButton.setOnClickListener(mView -> {
            Toast.makeText(mView.getContext(), "Search", Toast.LENGTH_SHORT).show();
            MainActivity.changeToFragment((AppCompatActivity) getActivity(),
                    new SearchFragment(), true,
                    "search-from-home");
        });

        mMyLibraryButton.setOnClickListener(mView -> {
            Toast.makeText(mView.getContext(), "MyLibrary", Toast.LENGTH_SHORT).show();
            MainActivity.changeToFragment((AppCompatActivity) getActivity(),
                    new LibraryFragment(), true,
                    "library-from-home");
        });

        mHomeViewModel.getLatestRecommendedUsers().observe(this, recommendedUserModels -> {
            mHomeAdapter.notifyDataSetChanged();
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void initRecyclerView() {
        mHomeAdapter = new HomeAdapter(this, mHomeViewModel.getLatestRecommendedUsers().getValue());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.setAdapter(mHomeAdapter);
    }
}