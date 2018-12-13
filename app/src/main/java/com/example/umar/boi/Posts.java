package com.example.umar.boi;

public class Posts {

    private String uid;
    private String id;
    private String name;
    private String price;
    private String imageUid;
    private String image_URL;
    private String author, rating;

    public Posts(String name, String price, String image_URL) {
        this.name = name;
        this.price = price;
        this.image_URL = image_URL;
    }

    public Posts(String name, String price, String image_URL, String author, String rating) {
        this.name = name;
        this.price = price;
        this.image_URL = image_URL;
        this.author = author;
        this.rating = rating;
    }

    public Posts() {
    }

    public String getImage_URL() {
        return image_URL;
    }

    public String getUid() {
        return uid;
    }

    public String getImageUid() {
        return imageUid;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String Uid() {
        return uid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Posts{" +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", image_URL='" + image_URL + '\'' +
                ", author='" + author + '\'' +
                ", rating='" + rating + '\'' +
                '}';
    }
}
