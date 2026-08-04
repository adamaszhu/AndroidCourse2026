package com.adamaszhu.androidcourse;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_demo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Log.i("DemoActivity", "Screen is created");

        Button btnVisibility = findViewById(R.id.btn_visibility);
        btnVisibility.setText("Change Visibility");
        btnVisibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tvResult = findViewById(R.id.tv_result);
                // Flip visibility of the result text view
                if (tvResult.getVisibility() == View.VISIBLE) {
                    tvResult.setVisibility(View.INVISIBLE);
                } else {
                    tvResult.setVisibility(View.VISIBLE);
                }
            }
        });
        test();
    }

    /**
     * Test JavaDoc
     */
    void test() {}
}