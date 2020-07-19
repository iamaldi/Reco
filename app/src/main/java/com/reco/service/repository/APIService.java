package com.reco.service.repository;

import com.reco.service.model.MyLibraryModel;
import com.reco.service.model.RecommendationsModel;
import com.reco.service.model.TrackModel;
import com.reco.service.model.UserLoginModel;
import com.reco.service.model.UserModel;
import com.reco.service.model.UserRegisterModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIService {
    // user register
    @POST("/users/register")
    Call<Void> userRegister(@Body UserRegisterModel user);

    // user login
    @POST("/users/login")
    Call<Void> userLogin(@Body UserLoginModel user);

    // get user profile
    @GET("/users/me")
    Call<UserModel> getMyProfile();

    // update user profile
    @PUT("/users/me")
    Call<Void> userUpdateProfile(@Body UserModel user);

    // delete user profile
    @DELETE("/users/me")
    Call<Void> deleteMyProfile();

    // add track to library
    @POST("/library/tracks/")
    Call<Void> addTrack(@Body TrackModel track);

    // remove track from library
    @DELETE("/library/tracks/{id}")
    Call<Void> removeTrack(@Path("id") int trackId);

    // list my library tracks
    @GET("/library/")
    Call<MyLibraryModel> getMyLibrary();

    // list recommended users
    @GET("/recommendations/")
    Call<RecommendationsModel> getAllRecommendedUsers();

    // get latest recommended users
    @GET("/recommendations/latest")
    Call<RecommendationsModel> getLatestRecommendedUsers();

}
