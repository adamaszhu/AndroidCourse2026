package com.adamaszhu.androidcourse.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.adamaszhu.androidcourse.entity.Book;

import java.util.ArrayList;

public class BookDBHelper extends SQLiteOpenHelper {

    static final String TABLE = "Book";
    static final String CREATE_BOOK_SQL = "create table " + TABLE + " (id integer primary key autoincrement, name text, author text)";
    static final String DELETE_BOOK_SQL = "drop table if exists " + TABLE;


    public BookDBHelper(@Nullable Context context) {
        super(context, "book.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_BOOK_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DELETE_BOOK_SQL);
        sqLiteDatabase.execSQL(CREATE_BOOK_SQL);
    }

    public ArrayList<Book> getBooks() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE, null, null, null, null, null, null, null);
        ArrayList<Book> results = new ArrayList<>();
        if (!cursor.moveToFirst()) {
            return results;
        }
        int nameIndex = cursor.getColumnIndex("name");
        int authorIndex = cursor.getColumnIndex("author");
        do {
            String name = cursor.getString(nameIndex);
            String author = cursor.getString(authorIndex);
            Book book = new Book(name, author);
            results.add(book);
        } while (cursor.moveToNext());
        return results;
    }

    public void deleteBook(String author) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE, "author = ?", new String[]{ author });
    }

    public void updateBookName(String name, String author) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        db.update(TABLE, values, "author = ?", new String[]{ author });
    }

    public void addBook(String name, String author) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("author", author);
        db.insert(TABLE, null, values);
    }
}
