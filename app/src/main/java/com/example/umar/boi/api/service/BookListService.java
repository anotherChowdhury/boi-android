package com.example.umar.boi.api.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BookListService {

    @GET("findbook")
    Call<List<Book>> getBooks(@Query("q") String name);
}
