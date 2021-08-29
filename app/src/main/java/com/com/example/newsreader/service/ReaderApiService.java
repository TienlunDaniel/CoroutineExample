package com.com.example.newsreader.service;

import com.model.ReaderResponse;

import kotlinx.coroutines.Deferred;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ReaderApiService {
    @GET("/v0/topstories.json?print=pretty")
    Deferred<String> getMoreInformation();


    @GET("https://hacker-news.firebaseio.com/v0/item/{id}.json?print=pretty")
    Deferred<String> getStoryInformation(@Path("id") String id);
}
