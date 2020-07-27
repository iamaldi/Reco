package com.reco.service.repository;

import com.reco.service.model.TrackModel;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LastFMService {
    // search tracks
    @GET("/?method=track.search&track={query}&api_key={LASTFM_API_KEY}&format=json")
    Call<List<TrackModel>> searchTrack(@Path("query") String query, @Path("LASTFM_API_KEY") String lastFMApiKey);

    // get track info
    @GET("/?method=track.getInfo&artist={artist}&track={trackName}&api_key={LASTFM_API_KEY}&format=json")
    Call<TrackModel> getTrackInfo(@Path("artist") String artist, @Path("trackName") String trackName, @Path("LASTFM_API_KEY") String lastFMApiKey);

}
