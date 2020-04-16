package com.pakholchuk.testskillbranch2.network;

import com.pakholchuk.testskillbranch2.pojo.Post;
import com.pakholchuk.testskillbranch2.pojo.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JSONPlaceHolderApi {
    @GET("/posts")
    public Call<ArrayList<Post>> getAllPosts();

    @GET("/users/{userId}")
    public Call<User> getUserById(@Path("userId") int userId);
}
