package com.adamaszhu.androidcourse.layout;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.adamaszhu.androidcourse.R;
import com.adamaszhu.androidcourse.components.ComponentsActivity;
import com.adamaszhu.androidcourse.entity.Book;
import com.adamaszhu.androidcourse.entity.MockDataGenerator;
import com.adamaszhu.androidcourse.utility.BaseActivity;

public class ListActivity extends BaseActivity {

    @Override
    public void load() {
        setContentView(R.layout.activity_list);
    }

    @Override
    public void setup() {
        ListView listView = findViewById(R.id.list);
        Book[] books = MockDataGenerator.mockBooks(100);

        // Version: Default array adapter
//        ArrayAdapter<Book> bookArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, books);
//        listView.setAdapter(bookArrayAdapter);

        // Version: Customized array adapter
        BookArrayAdapter bookArrayAdapter = new BookArrayAdapter(this, books);
        listView.setAdapter(bookArrayAdapter);
    }
}