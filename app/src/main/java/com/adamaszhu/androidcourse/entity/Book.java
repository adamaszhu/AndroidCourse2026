package com.adamaszhu.androidcourse.entity;

import androidx.annotation.NonNull;

import java.util.UUID;

public class Book {

    private String id;
    private String name;

    private String author;

    public Book(String name, String author) {
        this.name = name;
        this.author = author;
        id = UUID.randomUUID().toString();
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

    public String getId() {
        return id;
    }
}
