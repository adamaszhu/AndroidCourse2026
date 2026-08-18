package com.adamaszhu.androidcourse.layout;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.adamaszhu.androidcourse.R;
import com.adamaszhu.androidcourse.databinding.ActivityConstraintLayoutBinding;
import com.adamaszhu.androidcourse.utility.BaseActivity;

public class ConstraintLayoutActivity extends BaseActivity {

    private ActivityConstraintLayoutBinding binding;

    @Override
    public void load() {
        binding = ActivityConstraintLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    public void setup() {
    }
}