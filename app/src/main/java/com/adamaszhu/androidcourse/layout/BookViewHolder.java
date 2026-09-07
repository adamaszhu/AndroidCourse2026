package com.adamaszhu.androidcourse.layout;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adamaszhu.androidcourse.R;
import com.adamaszhu.androidcourse.entity.Book;

import org.w3c.dom.Text;

public class BookViewHolder extends RecyclerView.ViewHolder {

    private TextView tvTitle;
    private TextView tvAuthor;

    public BookViewHolder(@NonNull View itemView) {
        super(itemView);
        tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        tvAuthor = (TextView) itemView.findViewById(R.id.tv_author);
    }

    public void setBook(Book book) {
        tvTitle.setText(book.getName());
        tvAuthor.setText(book.getAuthor());
    }
}
