package com.adamaszhu.androidcourse.storage;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.util.List;

public class BookContentProvider extends ContentProvider {

    private static final String AUTHORITY = "com.adamaszhu.library";

    static final String BOOKS = "books";

    static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BOOKS);

    private BookDBHelper dbHelper;

    public BookContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(BookDBHelper.TABLE, selection, selectionArgs);
    }

    @Override
    public String getType(Uri uri) {
        List<String> paths = uri.getPathSegments();
        if (paths.get(paths.size()) == BOOKS) {
            return "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + BOOKS;
        } else {
            return "vnd.android.cursor.item/vnd." + AUTHORITY + "." + BOOKS;
        }
        // Image can be `image/jpeg`
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id = db.insert(BookDBHelper.TABLE, null, values);
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new BookDBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.query(BookDBHelper.TABLE, projection, selection,
                selectionArgs, null, null, sortOrder);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.update(BookDBHelper.TABLE, values, selection, selectionArgs);
    }
}