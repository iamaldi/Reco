package com.reco.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.reco.R;
import com.reco.service.model.RecommendedUserModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

public class RecommendationsAdapter extends RecyclerView.Adapter<RecommendationsAdapter.RecommendationsAdapterViewHolder> {
    private List<RecommendedUserModel> recommendedUsers;
    private Context context;

    public RecommendationsAdapter(Context context) {
        this.context = context;
    }

    public void setRecommendedUsers(List<RecommendedUserModel> recommendedUsers) {
        this.recommendedUsers = recommendedUsers;
    }

    @NonNull
    @Override
    public RecommendationsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new RecommendationsAdapterViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendationsAdapterViewHolder holder, int position) {
        RecommendedUserModel user = recommendedUsers.get(position);
        //holder.image.setImageResource(images.get(position));
        holder.mName.setText(user.getDisplayName());
        holder.mSimilarity.setText(String.format(context.getString(R.string.similarity_placeholder), user.getSimilarityMatch()));
        holder.sendMessageButton.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(R.string.open_in_messenger)
                    .setPositiveButton(R.string.delete_account_dialog_option_cancel, (dialog1, id) -> {
                        // cancel - do nothing
                    }).setNegativeButton(R.string.open_option, (dialog2, id) -> {
            }).create().show();

        });

    }

    @Override
    public int getItemCount() {
        if (recommendedUsers != null) {
            return recommendedUsers.size();
        } else {
            return 0;
        }
    }

    public static class RecommendationsAdapterViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImage;
        private TextView mName, mSimilarity;
        private AppCompatImageButton sendMessageButton;

        public RecommendationsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.user_item_image);
            mName = itemView.findViewById(R.id.user_item_name);
            mSimilarity = itemView.findViewById(R.id.user_item_similarity);
            sendMessageButton = itemView.findViewById(R.id.user_item_send_message_button);
        }
    }
}
