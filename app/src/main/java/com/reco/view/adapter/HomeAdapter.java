package com.reco.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.reco.R;
import com.reco.service.model.RecommendedUserModel;
import com.reco.view.callback.APIErrorCallbacks;
import com.reco.view.ui.HomeFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeAdapterViewHolder> {
    private List<RecommendedUserModel> mRecommendedUsers;

    public HomeAdapter(HomeFragment mFragment, List<RecommendedUserModel> mRecommendedUsers) {
        this.mRecommendedUsers = mRecommendedUsers;
    }

    @NonNull
    @Override
    public HomeAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new HomeAdapterViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapterViewHolder holder, int position) {
        RecommendedUserModel user = mRecommendedUsers.get(position);
//        holder.mUserImage.setImageIcon();
        holder.mName.setText(user.getDisplayName());
        holder.mSimilarity.setText(user.getSimilarityMatch() + "%");
    }

    @Override
    public int getItemCount() {
        if(mRecommendedUsers != null){
            return mRecommendedUsers.size();
        } else {
            return 0;
        }
    }

    public class HomeAdapterViewHolder extends RecyclerView.ViewHolder {
        private ImageView mUserImage;
        private TextView mName, mSimilarity;

        public HomeAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            mUserImage = itemView.findViewById(R.id.user_item_image);
            mName = itemView.findViewById(R.id.user_item_name);
            mSimilarity = itemView.findViewById(R.id.user_item_similarity);
        }
    }
}
