package com.reco.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.reco.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class RecommendationAdapter extends RecyclerView.Adapter<RecommendationAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Integer> images;
    private ArrayList<String> names;
    private ArrayList<String> similarities;


    public RecommendationAdapter(Context context, ArrayList<Integer> images, ArrayList<String> names, ArrayList<String> similarities) {
        this.context = context;
        this.images = images;
        this.names = names;
        this.similarities = similarities;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommendation_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(names.get(position));
        holder.similarity.setText(similarities.get(position));
        holder.image.setImageResource(images.get(position));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name;
        TextView similarity;
        ConstraintLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            image = itemView.findViewById(R.id.recommendation_item_image);
            name = itemView.findViewById(R.id.recommendation_item_name);
            similarity = itemView.findViewById(R.id.recommendation_item_similarity);
            layout = itemView.findViewById(R.id.recommendation_item_layout);

        }
    }
}
