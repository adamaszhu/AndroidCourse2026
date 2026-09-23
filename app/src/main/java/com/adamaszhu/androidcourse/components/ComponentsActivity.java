package com.adamaszhu.androidcourse.components;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.adamaszhu.androidcourse.R;
import com.adamaszhu.androidcourse.databinding.ActivityComponentsBinding;

public class ComponentsActivity extends AppCompatActivity {

    private static final String TAG = ComponentsActivity.class.getName();

    private ActivityComponentsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        load();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Log.i(TAG, "Screen is created");

        setupVisibilityButton();
    }

    private void load() {
        binding = ActivityComponentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void setupVisibilityButton() {
        binding.btnVisibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Flip visibility of the result text view
                if (binding.tvResult.getVisibility() == View.VISIBLE) {
                    binding.tvResult.setVisibility(View.INVISIBLE);
                } else {
                    binding.tvResult.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}