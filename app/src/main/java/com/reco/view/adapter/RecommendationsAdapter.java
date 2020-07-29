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

public class RecommendationsAdapter extends RecyclerView.Adapter<RecommendationsAdapter.RecommendationsAdapterViewHolder> {
    private List<RecommendedUserModel> mRecommendedUsers;

    public RecommendationsAdapter(List<RecommendedUserModel> mRecommendedUsers) {
        this.mRecommendedUsers = mRecommendedUsers;
    }


    @NonNull
    @Override
    public RecommendationsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new RecommendationsAdapterViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendationsAdapterViewHolder holder, int position) {
//        holder.image.setImageResource(images.get(position));
        holder.mName.setText(mRecommendedUsers.get(position).getName());
        holder.mSimilarity.setText(String.valueOf(mRecommendedUsers.get(position).getSimilarityMatch()) + "%");
    }

    @Override
    public int getItemCount() {
        return mRecommendedUsers.size();
    }

    public class RecommendationsAdapterViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImage;
        private TextView mName, mSimilarity;

        public RecommendationsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.recommendation_item_image);
            mName = itemView.findViewById(R.id.recommendation_item_name);
            mSimilarity = itemView.findViewById(R.id.recommendation_item_similarity);

        }
    }
}
