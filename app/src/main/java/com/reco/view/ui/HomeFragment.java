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
import com.reco.service.model.TrackModel;
import com.reco.service.model.UserProfileModel;
import com.reco.util.Utilities;
import com.reco.view.adapter.RecommendationsAdapter;
import com.reco.view.callback.APIErrorCallbacks;
import com.reco.viewmodel.HomeViewModel;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends Fragment implements APIErrorCallbacks {
    private RecommendationsAdapter recommendationsAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            return;
        }
        recommendationsAdapter = new RecommendationsAdapter(getContext());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView noRecommendationsMessage = view.findViewById(R.id.fragment_home_no_data_message);
        TextView userGreeting = view.findViewById(R.id.fragment_home_greeting_message);
        ImageButton settingsButton = Objects.requireNonNull(getActivity()).findViewById(R.id.fragment_home_settings_button);
        RecyclerView mRecyclerView = view.findViewById(R.id.fragment_home_recyclerView);
        NavController navController = Navigation.findNavController(view);

        HomeViewModel mHomeViewModel = new HomeViewModel(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.setAdapter(recommendationsAdapter);

        // show bottom navigation menu
        BottomNavigationView mBottomNav = Objects.requireNonNull(getActivity()).
                findViewById(R.id.activity_main_bottomNavigationView);
        mBottomNav.setVisibility(View.VISIBLE);

        // use AI/ML to greet the user
        UserProfileModel user = Utilities.getLocalUser((AppCompatActivity) Objects.requireNonNull(getActivity()));
        if (user != null) {
            String displayName = user.getDisplayName();
            userGreeting.setText(String.format(getResources().getString(R.string.welcome_message_placeholder),
                    displayName.substring(0, 1).toUpperCase() +
                            displayName.substring(1).split(" ")[0]));
        } else {
            userGreeting.setText(String.format(getResources().getString(R.string.welcome_message_placeholder), "User"));
        }

        mHomeViewModel.getLatestRecommendedUsers().observe(this, recommendedUsers -> {
            if (recommendedUsers != null) {
                recommendationsAdapter.setRecommendedUsers(recommendedUsers);
                recommendationsAdapter.notifyDataSetChanged();
            }
        });

        recommendationsAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                List<TrackModel> trackModelList = Utilities.getLocalLibrary((AppCompatActivity) Objects.requireNonNull(getActivity()));
                if (trackModelList != null) {
                    if (recommendationsAdapter.getItemCount() == 0 || trackModelList.isEmpty()) {
                        mRecyclerView.setVisibility(View.INVISIBLE);
                        noRecommendationsMessage.setVisibility(View.VISIBLE);
                    } else {
                        noRecommendationsMessage.setVisibility(View.INVISIBLE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                    }
                } else {
                    mRecyclerView.setVisibility(View.INVISIBLE);
                    noRecommendationsMessage.setVisibility(View.VISIBLE);
                }
            }
        });

        settingsButton.setOnClickListener(view1 -> {
            navController.navigate(R.id.action_homeFragment_to_settingsFragment);
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
        if (!errorMsg.isEmpty()) {
            Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
        }
    }
}