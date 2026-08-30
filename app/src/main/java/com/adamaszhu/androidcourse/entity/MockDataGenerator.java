package com.adamaszhu.androidcourse.entity;

import java.util.ArrayList;

public class MockDataGenerator {

    public static Book[] mockBooks(int number) {
        ArrayList<Book> books = new ArrayList<>();
        for (int i = 0 ; i < number; i++) {
            books.add(new Book("Book" + String.valueOf(i), "Adamas"));
        }
        return books.toArray(new Book[number]);
    }
}
