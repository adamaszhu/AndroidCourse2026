package com.adamaszhu.androidcourse.layout;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.adamaszhu.androidcourse.R;
import com.adamaszhu.androidcourse.entity.Book;
import com.adamaszhu.androidcourse.entity.MockDataGenerator;
import com.adamaszhu.androidcourse.utility.BaseActivity;

import java.util.Arrays;
import java.util.List;

public class RecycleActivity extends BaseActivity {

    @Override
    public void load() { setContentView(R.layout.activity_recycle); }

    @Override
    public void setup() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        Book[] books = MockDataGenerator.mockBooks(100);

        // Adapter Version 1: Base adapter
//        BookAdapter adapter = new BookAdapter(Arrays.asList(books));

        // Adapter Version 2: List adapter
        BookListAdapter adapter = new BookListAdapter();
        adapter.submitList(Arrays.asList(books));
        recyclerView.setAdapter(adapter);

        // Layout Version 1: Linear
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Layout Version 2: Stagged
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }
}