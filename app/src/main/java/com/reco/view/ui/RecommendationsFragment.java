package com.reco.view.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.reco.R;
import com.reco.service.model.TrackModel;
import com.reco.util.Utilities;
import com.reco.view.adapter.RecommendationsAdapter;
import com.reco.view.callback.APIErrorCallbacks;
import com.reco.viewmodel.RecommendationsViewModel;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecommendationsFragment extends Fragment implements APIErrorCallbacks {
    private RecommendationsAdapter mRecommendationsAdapter;

    public RecommendationsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            return;
        }
        mRecommendationsAdapter = new RecommendationsAdapter(getContext());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView mRecyclerView = view.findViewById(R.id.fragment_recommendations_recycler_view);
        RecommendationsViewModel mRecommendationsViewModel = new RecommendationsViewModel(this);
        TextView noRecommendationsMessage = view.findViewById(R.id.fragment_recommendations_no_recommendations_message);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.setAdapter(mRecommendationsAdapter);

        mRecommendationsViewModel.getRecommendedUsers().observe(this, recommendedUsers -> {
            if (recommendedUsers != null) {
                mRecommendationsAdapter.setRecommendedUsers(recommendedUsers);
                mRecommendationsAdapter.notifyDataSetChanged();
            }
        });

        mRecommendationsAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                List<TrackModel> trackModelList = Utilities.getLocalLibrary((AppCompatActivity) Objects.requireNonNull(getActivity()));
                if (trackModelList != null) {
                    if (mRecommendationsAdapter.getItemCount() == 0 || trackModelList.isEmpty()) {
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