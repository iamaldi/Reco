package com.reco.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.reco.R;
import com.reco.service.model.TrackModel;
import com.reco.view.callback.AdapterCallbacks;
import com.reco.view.ui.LibraryFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.MyLibraryAdapterViewHolder> {
    private AdapterCallbacks mAdapterCallbacks;
    private List<TrackModel> mTracks;

    public LibraryAdapter(LibraryFragment mFragment, List<TrackModel> mTracks) {
        this.mAdapterCallbacks = mFragment;
        this.mTracks = mTracks;
    }

    @NonNull
    @Override
    public MyLibraryAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.track_item, parent, false);
        return new MyLibraryAdapterViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyLibraryAdapterViewHolder holder, final int position) {
        TrackModel track = mTracks.get(position);

        holder.mTitle.setText(track.getTitle());
        holder.mArtist.setText(track.getArtist());

        holder.mButton.setImageResource(android.R.drawable.ic_delete);
        holder.mButton.setOnClickListener(view -> {
            mAdapterCallbacks.onRemoveTrackFromLibraryCallback(track, position);
        });
    }

    @Override
    public int getItemCount() {
        if (mTracks != null) {
            return mTracks.size();
        } else {
            return 0;
        }
    }

    public class MyLibraryAdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView mArtist, mTitle;
        private AppCompatImageButton mButton;

        public MyLibraryAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.track_item_title);
            mArtist = itemView.findViewById(R.id.track_item_artist);
            mButton = itemView.findViewById(R.id.track_item_add_button);
        }
    }
}
