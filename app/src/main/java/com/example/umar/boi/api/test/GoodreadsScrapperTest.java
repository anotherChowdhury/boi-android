package com.example.umar.boi.api.test;

import com.example.umar.boi.api.network.GoodreadsScrapper;
import com.example.umar.boi.api.service.Book;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class GoodreadsScrapperTest {
    public static void main(String[] args) {
        Call<List<Book>> books = GoodreadsScrapper.getBookList("The Godfather");
        try {
            Response<List<Book>> response = books.execute();
            if (!response.isSuccessful()) {
                System.out.println("Response Error!");
            } else {
                System.out.println("Reponse successful!");
            }

            List<Book> bookList = response.body();
            for (Book b :
                    bookList) {
                System.out.println(b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
