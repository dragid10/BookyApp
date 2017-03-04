package com.alexoladele.testingshit;

/**
 * Alex Oladele
 * BookyApp
 */

public class BookModel {
    //
    private String title, author, md5;

    public BookModel() {
    }

    public BookModel(String title, String author, String md5) {
        this.title = title;
        this.author = author;
        this.md5 = md5;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}
