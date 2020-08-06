package com.reco.view.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.reco.R;
import com.reco.view.adapter.RecommendationsAdapter;
import com.reco.view.callback.APIErrorCallbacks;
import com.reco.viewmodel.RecommendationsViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecommendationsFragment extends Fragment implements APIErrorCallbacks {
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
        mRecommendationsViewModel = new RecommendationsViewModel(this);

        mRecommendationsViewModel.getRecommendedUsers().observe(this, recommendedUsers -> {
            Toast.makeText(getContext(), "Users: " + recommendedUsers.size(), Toast.LENGTH_SHORT).show();
            mRecommendationsAdapter = new RecommendationsAdapter(recommendedUsers);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
            mRecyclerView.setAdapter(mRecommendationsAdapter);

            mRecommendationsAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recommendations, container, false);
    }

    @Override
    public void onAPIError(String errorMsg) {
        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }
}