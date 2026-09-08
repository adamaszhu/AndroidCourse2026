package com.adamaszhu.androidcourse.demo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.adamaszhu.androidcourse.R;
import com.adamaszhu.androidcourse.databinding.ActivityDemoBinding;
import com.adamaszhu.androidcourse.utility.BaseActivity;

public class DemoActivity extends BaseActivity {

    public final static String INTENT_KEY = "DEMO";

    private static final String TAG = DemoActivity.class.getName();

    private ActivityDemoBinding binding;
    private Demo demo;

    public void load() {
        binding = ActivityDemoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        demo = getIntent().getSerializableExtra(INTENT_KEY, Demo.class);
        demo.setActivity(this);
    }

    @Override
    public void show() {
        demo.show();
    }

    @Override
    public void hide() {
        demo.hide();
    }

    public void setup() {
        setTitle(demo.getTitleId());
        demo.setListener(new OutputListener() {
            @Override
            public void updateOutput(String output) {
                binding.tvResult.setText(output);
            }
        });
        setupButtons();
        demo.initialize();
    }

    private void setupButtons() {
        for (DemoButton demoButton : demo.getDemoButtons()) {
            Button button = new Button(this);
            button.setText(demoButton.getTitleId());
            button.setOnClickListener(demoButton.getListener());
            binding.content.addView(button);
        }
    }
}