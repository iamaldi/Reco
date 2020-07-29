package com.reco.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.reco.R;
import com.reco.service.model.TrackModel;
import com.reco.view.callback.AdapterCallbacks;
import com.reco.view.ui.SearchFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchAdapterViewHolder> {
    private AdapterCallbacks mAdapterCallbacks;
    private List<TrackModel> tracks;

    public SearchAdapter(SearchFragment mFragment, List<TrackModel> tracks) {
        this.mAdapterCallbacks = mFragment;
        this.tracks = tracks;
    }

    @NonNull
    @Override
    public SearchAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.track_item, parent, false);
        return new SearchAdapterViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchAdapterViewHolder holder, final int position) {
        holder.mTrackArtist.setText(tracks.get(position).getArtist());
        holder.mTrackTitle.setText(tracks.get(position).getName());

        holder.mAddButton.setOnClickListener(view -> mAdapterCallbacks.onAddTrackToLibrary(tracks.get(position)));
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    public class SearchAdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView mTrackArtist, mTrackTitle;
        private AppCompatImageButton mAddButton;

        public SearchAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            mTrackArtist = itemView.findViewById(R.id.track_item_artist);
            mTrackTitle = itemView.findViewById(R.id.track_item_title);
            mAddButton = itemView.findViewById(R.id.track_item_add_button);
        }
    }


}