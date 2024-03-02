package com.example.foobarpart2.network.api;

import com.example.foobarpart2.db.entity.Post;
import com.example.foobarpart2.db.entity.Token;
import com.example.foobarpart2.db.entity.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServiceAPI {

    /*
    * Token Requests
    */

    @POST("tokens")
    Call<Token> authenticateUser(@Body String username, String password);

    /*
    * User Requests
    */
    @POST("users")
    Call<Void> createUser(@Body User user);

    @GET("users/{id}")
    Call<User> getUser(@Path("id") String username);
    @PATCH("users/{id}")
    Call<Void> editUser(@Path("id") String username);
    @DELETE("users/{id}")
    Call<Void> deleteUser(@Path("id") String username);

    @GET("users/{id}/posts")
    Call<Post> getPosts(@Path("id") String username);
    @POST("users/{id}/posts")
    Call<Post> createPost(@Path("id") String username);
}
