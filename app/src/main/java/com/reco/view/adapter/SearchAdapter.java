package com.reco.view.adapter;

import android.util.Log;
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
        TrackModel track = tracks.get(position);
        holder.mTrackArtist.setText(track.getArtist());
        holder.mTrackTitle.setText(track.getTitle());

        // add default tag to all search results
        // TODO: what about tracks already in the library?
        holder.mButton.setTag(android.R.drawable.ic_input_add);

        holder.mButton.setOnClickListener(view -> {
            if (holder.mButton.getTag().equals(android.R.drawable.ic_delete)) {
                // remove item from library
                mAdapterCallbacks.onRemoveTrackFromLibraryCallback(track, position);
                // change button icon
                holder.mButton.setImageResource(android.R.drawable.ic_input_add);
                holder.mButton.setTag(android.R.drawable.ic_input_add);
            } else {
                // add item to library
                mAdapterCallbacks.onAddTrackToLibraryCallback(track);
                // change button icon
                holder.mButton.setImageResource(android.R.drawable.ic_delete);
                holder.mButton.setTag(android.R.drawable.ic_delete);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (tracks != null) {
            return tracks.size();
        } else {
            return 0;
        }

    }

    public class SearchAdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView mTrackArtist, mTrackTitle;
        private AppCompatImageButton mButton;

        public SearchAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            mTrackArtist = itemView.findViewById(R.id.track_item_title);
            mTrackTitle = itemView.findViewById(R.id.track_item_artist);
            mButton = itemView.findViewById(R.id.track_item_add_button);
        }
    }


}