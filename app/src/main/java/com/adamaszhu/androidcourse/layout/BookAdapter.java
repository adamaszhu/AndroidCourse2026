package com.adamaszhu.androidcourse.layout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adamaszhu.androidcourse.R;
import com.adamaszhu.androidcourse.entity.Book;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookViewHolder> {

    private static final String TAG = BookAdapter.class.getName();

    private List<Book> bookList;

    public BookAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "Create new view holder");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Log.i(TAG, "Bind data to view holder for item at index " + String.valueOf(position));
        Book book = bookList.get(position);
        holder.setBook(book);
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }
}
