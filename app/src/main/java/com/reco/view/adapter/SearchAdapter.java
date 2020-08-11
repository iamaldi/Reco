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
    private List<TrackModel> searchTracksList;
    private int DELETE_TAG = 0;
    private int ADD_TAG = 1;

    public SearchAdapter(SearchFragment mFragment) {
        this.mAdapterCallbacks = mFragment;
    }

    public void setSearchTracksList(List<TrackModel> searchTracksList) {
        this.searchTracksList = searchTracksList;
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

        holder.mButton.setTag(android.R.drawable.ic_input_add);

//        // if a track is already in the local library show delete icon
//        if (localLibrary != null && searchTracksList != null) {
////            Log.d("RECO-RemoveAAdd", "Library not empty");
//            for (TrackModel tr : localLibrary) {
//                TrackModel sss = searchTracksList.get(position);
//                if (sss.getTitle().equals(tr.getTitle())) {
//                    holder.mButton.setImageResource(android.R.drawable.ic_delete);
//                    holder.mButton.setTag(DELETE_TAG);
//                    Log.d("RECO-RemoveAAdd", "DELETE icon added");
//                    Log.d("RECO-RemoveAAdd", sss.getTitle());
//                    Log.d("RECO-RemoveAAdd", sss.getArtist());
//                }
//            }
//        }

        holder.mButton.setOnClickListener(view -> {
            if (holder.mButton.getTag().equals(DELETE_TAG)) {
                // remove item from library
                mAdapterCallbacks.onRemoveTrackFromLibraryCallback(track, position);
                // change button icon
                holder.mButton.setImageResource(android.R.drawable.ic_input_add);
                holder.mButton.setTag(android.R.drawable.ic_input_add);
            } else if (holder.mButton.getTag().equals(android.R.drawable.ic_input_add)) {
                // add item to library
                mAdapterCallbacks.onAddTrackToLibraryCallback(track);
                // change button icon
                holder.mButton.setImageResource(android.R.drawable.ic_delete);
                holder.mButton.setTag(android.R.drawable.ic_delete);
                Log.d("RECO-SEARCH", "DELETE icon added");
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