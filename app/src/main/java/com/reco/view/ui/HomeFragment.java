package com.reco.view.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.reco.R;
import com.reco.service.model.UserProfileModel;
import com.reco.util.Utilities;
import com.reco.view.adapter.HomeAdapter;
import com.reco.view.callback.APIErrorCallbacks;
import com.reco.viewmodel.HomeViewModel;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends Fragment implements APIErrorCallbacks {
    private HomeAdapter mHomeAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            return;
        }
        mHomeAdapter = new HomeAdapter();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView noRecommendationsMessage = view.findViewById(R.id.fragment_home_no_data_msg_textView);
        // use AI to greet the user
        TextView userGreeting = view.findViewById(R.id.fragment_home_greeting_user);
        UserProfileModel user = Utilities.getLocalUser((AppCompatActivity) Objects.requireNonNull(getActivity()));

        if (user != null) {
            userGreeting.setText(user.getDisplayName());
        }

        ImageButton settingsButton = Objects.requireNonNull(getActivity()).
                findViewById(R.id.fragment_home_settings_imageButton);

        settingsButton.setOnClickListener(view1 -> {
            MainActivity.changeToFragment((AppCompatActivity) getActivity(),
                    new SettingsFragment(), true,
                    "settings-fragment");
        });

        // show bottom navigation menu
        BottomNavigationView mBottomNav = Objects.requireNonNull(getActivity()).
                findViewById(R.id.activity_main_bottomNavigationView);
        mBottomNav.setVisibility(View.VISIBLE);

        RecyclerView mRecyclerView = view.findViewById(R.id.fragment_home_recyclerView);
        HomeViewModel mHomeViewModel = new HomeViewModel(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.setAdapter(mHomeAdapter);

        mHomeViewModel.getLatestRecommendedUsers().observe(this, recommendedUsers -> {
            if (recommendedUsers != null) {
                mHomeAdapter.setRecommendedUsers(recommendedUsers);
                // let the fragment know that we just added some data to it
                mHomeAdapter.notifyDataSetChanged();
            }
        });

        mHomeAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                if(mHomeAdapter.getItemCount() == 0){
                    noRecommendationsMessage.setVisibility(View.VISIBLE);
                } else {
                    noRecommendationsMessage.setVisibility(View.GONE);
                }
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