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
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends Fragment {
    private HomeViewModel mHomeViewModel;
    private HomeAdapter mHomeAdapter;
    private RecyclerView mRecyclerView;
    private AppCompatImageButton mSettingsButton;
    private Button mSearchButton, mMyLibraryButton;
    private FragmentTransaction transaction;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSettingsButton = view.findViewById(R.id.fragment_home_profile_imageButton);
        mSearchButton = view.findViewById(R.id.fragment_home_search_button);
        mMyLibraryButton = view.findViewById(R.id.fragment_home_myLib_button);

        transaction = getActivity().getSupportFragmentManager().beginTransaction();

        mSettingsButton.setOnClickListener(mView -> {
            Toast.makeText(mView.getContext(), "Settings", Toast.LENGTH_SHORT).show();
        });

        mSearchButton.setOnClickListener(mView -> {
            Toast.makeText(mView.getContext(), "Search", Toast.LENGTH_SHORT).show();

            SearchFragment mSearchFragment = new SearchFragment();

            transaction.replace(R.id.fragment_container, mSearchFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        mMyLibraryButton.setOnClickListener(mView -> {
            Toast.makeText(mView.getContext(), "MyLibrary", Toast.LENGTH_SHORT).show();
            MyLibraryFragment mMyLibraryFragment = new MyLibraryFragment();

            transaction.replace(R.id.fragment_container, mMyLibraryFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        mRecyclerView = view.findViewById(R.id.fragment_home_recyclerView);

        mHomeViewModel = new HomeViewModel();

        initRecyclerView();

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
        // get LiveData from the viewModel - this gets a list of tracks
        mHomeAdapter = new HomeAdapter(mHomeViewModel.getLatestRecommendedUsers().getValue());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.setAdapter(mHomeAdapter);
    }
}