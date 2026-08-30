package com.adamaszhu.androidcourse.layout;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.adamaszhu.androidcourse.R;
import com.adamaszhu.androidcourse.entity.Book;

public class BookArrayAdapter extends ArrayAdapter<Book> {

    private final static String TAG = BookArrayAdapter.class.getName();

    public BookArrayAdapter(@NonNull Context context, @NonNull Book[] objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View reusableView, @NonNull ViewGroup parent) {
        Book book = getItem(position);
        View view;
        if (reusableView == null) {
            Log.i(TAG, "Create new item view for item at " + String.valueOf(position));
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        } else {
            Log.i(TAG, "Reuse new item view for item at " + String.valueOf(position));
            view = reusableView;
        }
        // Fill the data into the view
        TextView tvTitle = view.findViewById(R.id.tv_title);
        TextView tvAuthor = view.findViewById(R.id.tv_author);
        tvTitle.setText(book.getName());
        tvAuthor.setText(book.getAuthor());
        return view;
    }
}
