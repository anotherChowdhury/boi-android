package com.example.umar.boi.api.network;

import com.example.umar.boi.api.service.Book;
import com.example.umar.boi.api.service.BookListService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class GoodreadsScrapper {

    private static BookListService bookListService;
    private static Retrofit retrofit;


    private static BookListService getGoodreadsScrapperService() {
        if (bookListService == null) {
            retrofit = RetrofitSingleton.getRetrofitInstance();
            bookListService = retrofit.create(BookListService.class);
        }
        return bookListService;
    }

    public static Call<List<Book>> getBookList(String name) {
        if (bookListService == null)
            bookListService = GoodreadsScrapper.getGoodreadsScrapperService();
        return bookListService.getBooks(name);
    }
}
