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
    private List<TrackModel> searchTracksList;
    private List<TrackModel> localLibrary;

    public SearchAdapter(SearchFragment mFragment) {
        this.mAdapterCallbacks = mFragment;
    }

    public void setSearchTracksList(List<TrackModel> searchTracksList) {
        this.searchTracksList = searchTracksList;
    }

    public void setLocalLibrary(List<TrackModel> localLibrary) {
        this.localLibrary = localLibrary;
    }

    @NonNull
    @Override
    public SearchAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.track_item, parent, false);
        return new SearchAdapterViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchAdapterViewHolder holder, final int position) {
        TrackModel track = searchTracksList.get(position);
        holder.mTrackArtist.setText(track.getArtist());
        holder.mTrackTitle.setText(track.getTitle());

        holder.mButton.setImageResource(R.drawable.ic_baseline_add_24);
        holder.mButton.setTag(R.drawable.ic_baseline_add_24);

        // if a track is already in the local library show delete icon
        if (localLibrary != null) {
            for (TrackModel trackModel : localLibrary) {
                if (trackModel.getTitle().equals(track.getTitle()) && trackModel.getArtist().equals(track.getArtist())) {
                    holder.mButton.setImageResource(R.drawable.ic_baseline_remove_24);
                    holder.mButton.setTag(R.drawable.ic_baseline_remove_24);
                    break; // so we don't alter the same item more than once
                }
            }
        }

        holder.mButton.setOnClickListener(view -> {
            if (holder.mButton.getTag().equals(R.drawable.ic_baseline_remove_24)) {
                // remove item from library
                mAdapterCallbacks.onRemoveTrackFromLibraryCallback(track, position);
                // change button icon
                holder.mButton.setImageResource(R.drawable.ic_baseline_add_24);
                holder.mButton.setTag(R.drawable.ic_baseline_add_24);
            } else if (holder.mButton.getTag().equals(R.drawable.ic_baseline_add_24)) {
                // add item to library
                mAdapterCallbacks.onAddTrackToLibraryCallback(track);
                // change button icon
                holder.mButton.setImageResource(R.drawable.ic_baseline_remove_24);
                holder.mButton.setTag(R.drawable.ic_baseline_remove_24);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (searchTracksList != null) {
            return searchTracksList.size();
        } else {
            return 0;
        }
    }

    public static class SearchAdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView mTrackArtist, mTrackTitle;
        private AppCompatImageButton mButton;

        public SearchAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            mTrackTitle = itemView.findViewById(R.id.track_item_title);
            mTrackArtist = itemView.findViewById(R.id.track_item_artist);
            mButton = itemView.findViewById(R.id.track_item_add_button);
        }
    }


}