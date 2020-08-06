package com.reco.service.repository;

import com.reco.service.model.RecommendedUserModel;
import com.reco.service.model.TrackModel;
import com.reco.service.model.UserLoginModel;
import com.reco.service.model.UserPasswordChangeModel;
import com.reco.service.model.UserProfileModel;
import com.reco.service.model.UserRegisterModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {
    // user register
    @POST("/users/register")
    Call<UserProfileModel> userRegister(@Body UserRegisterModel user);

    // user login
    @POST("/users/login")
    Call<UserProfileModel> userLogin(@Body UserLoginModel user);

    // get user profile
    @GET("/users/me")
    Call<UserProfileModel> getUserProfile();

    // update user profile
    @PUT("/users/me")
    Call<Void> updateUserProfile(@Body UserProfileModel user);

    // delete user profile
    @DELETE("/users/me")
    Call<Void> deleteUserProfile();

    @PATCH("/users/password")
    Call<Void> changeUserPassword(@Body UserPasswordChangeModel user);

    @POST("/users/logout")
    Call<Void> logoutUser();

    // search tracks
    @GET("/search")
    Call<List<TrackModel>> searchTracks(@Query("q") String query);

    // add track to library
    @POST("/library")
    Call<Void> addTrackToLibrary(@Body TrackModel track);

    // remove track from library
    @DELETE("/library/{id}")
    Call<Void> removeTrackFromLibrary(@Path("id") int mTrackId);

    // list my library tracks
    @GET("/library")
    Call<List<TrackModel>> getUserLibrary();

    // list recommended users
    @GET("/recommendations")
    Call<List<RecommendedUserModel>> getRecommendedUsers();

    // get latest recommended users
    @GET("/recommendations/latest")
    Call<List<RecommendedUserModel>> getLatestRecommendedUsers();

}
