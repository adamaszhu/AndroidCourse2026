package com.adamaszhu.androidcourse.entity;

import androidx.annotation.NonNull;

public class Book {
    private String name;
    private String author;

    public Book(String name, String author) {
        this.name = name;
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public String getName() {
        return name;
    }

    @NonNull
    @Override
    public String toString() {
        return name + " (" + author + ")";
    }
}
