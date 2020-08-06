package com.reco.view.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.reco.R;
import com.reco.view.adapter.HomeAdapter;
import com.reco.view.callback.APIErrorCallbacks;
import com.reco.viewmodel.HomeViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends Fragment implements APIErrorCallbacks {
    private HomeViewModel mHomeViewModel;
    private HomeAdapter mHomeAdapter;
    private RecyclerView mRecyclerView;

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

        // show bottom navigation menu
        BottomNavigationView mBottomNav = getActivity().findViewById(R.id.activity_main_bottomNavigationView);
        mBottomNav.setVisibility(View.VISIBLE);

        mRecyclerView = view.findViewById(R.id.fragment_home_recyclerView);
        mHomeViewModel = new HomeViewModel(this);

        mHomeViewModel.getLatestRecommendedUsers().observe(this, recommendedUsers -> {
            if (recommendedUsers != null) {
                mHomeAdapter = new HomeAdapter(this, recommendedUsers);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
                mRecyclerView.setAdapter(mHomeAdapter);

                // let the fragment know that we just added some data to it
                mHomeAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onAPIError(String errorMsg) {
        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }
}