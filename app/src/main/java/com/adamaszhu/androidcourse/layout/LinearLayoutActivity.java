package com.adamaszhu.androidcourse.layout;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.adamaszhu.androidcourse.R;
import com.adamaszhu.androidcourse.databinding.ActivityLinearLayoutBinding;
import com.adamaszhu.androidcourse.utility.BaseActivity;

public class LinearLayoutActivity extends BaseActivity {

    private ActivityLinearLayoutBinding binding;

    // TODO: Programmatically add a text view

    @Override
    public void load() {
        binding = ActivityLinearLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    public void setup() {
    }
}