package com.reco.view.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.reco.R;
import com.reco.view.adapter.RecommendationsAdapter;
import com.reco.viewmodel.RecommendationsViewModel;

public class RecommendationsFragment extends Fragment {
    private RecommendationsViewModel mRecommendationsViewModel;
    private RecyclerView mRecyclerView;
    private RecommendationsAdapter mRecommendationsAdapter;

    public RecommendationsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = view.findViewById(R.id.fragment_recommendations_recycler_view);
        mRecommendationsViewModel = new RecommendationsViewModel();

        initRecyclerView();

        mRecommendationsViewModel.getRecommendedUsers().observe(this, recommendedUserModels -> {
            Toast.makeText(getContext(), "Users: " + recommendedUserModels.size(), Toast.LENGTH_SHORT).show();
            mRecommendationsAdapter.notifyDataSetChanged();
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recommendations, container, false);
    }

    public void initRecyclerView() {
        mRecommendationsAdapter = new RecommendationsAdapter(mRecommendationsViewModel.getRecommendedUsers().getValue());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.setAdapter(mRecommendationsAdapter);
    }
}