package com.pakholchuk.testskillbranch2.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
    private static NetworkService instance;
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private Retrofit retrofit;
    private NetworkService(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static NetworkService getInstance(){
        if (instance == null) {
            instance = new NetworkService();
        }
        return instance;
    }

    public JSONPlaceHolderApi getJSONApi() {
        return retrofit.create(JSONPlaceHolderApi.class);
    }

}
