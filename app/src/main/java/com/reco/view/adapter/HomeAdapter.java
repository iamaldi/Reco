package com.reco.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.reco.R;
import com.reco.service.model.RecommendedUserModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeAdapterViewHolder> {
    private List<RecommendedUserModel> mRecommendedUsers;

    public HomeAdapter(List<RecommendedUserModel> mRecommendedUsers) {
        this.mRecommendedUsers = mRecommendedUsers;
    }

    @NonNull
    @Override
    public HomeAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommendation_item, parent, false);
        return new HomeAdapterViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapterViewHolder holder, int position) {
        // attach data to item
//        holder.mUserImage.setImageIcon();
        holder.mName.setText(mRecommendedUsers.get(position).getName());
        holder.mSimilarity.setText(String.valueOf(mRecommendedUsers.get(position).getSimilarityMatch()) + "%");
    }

    @Override
    public int getItemCount() {
        return mRecommendedUsers.size();
    }

    public class HomeAdapterViewHolder extends RecyclerView.ViewHolder {
        private ImageView mUserImage;
        private TextView mName, mSimilarity;

        public HomeAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            mUserImage = itemView.findViewById(R.id.recommendation_item_image);
            mName = itemView.findViewById(R.id.recommendation_item_name);
            mSimilarity = itemView.findViewById(R.id.recommendation_item_similarity);
        }
    }
}
