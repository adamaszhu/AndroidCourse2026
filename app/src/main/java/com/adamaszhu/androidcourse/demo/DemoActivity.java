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

public class DemoActivity extends AppCompatActivity {

    private static final String TAG = DemoActivity.class.getName();

    private ActivityDemoBinding binding;
    private Demo demo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Temporary hardcode
        demo = new Demo() {
            @Override
            public int getTitleId() {
                return R.string.demo_ui_components;
            }

            @Override
            public DemoButton[] getDemoButtons() {
                return new DemoButton[] {
                        new DemoButton(R.string.change_visibility, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getListener().updateOutput("Become Visible");
                            }
                        })
                };
            }

            @Override
            public void initialize() {
                getListener().updateOutput("-");
            }
        };

        load();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupDemo();
    }

    private void load() {
        binding = ActivityDemoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void setupDemo() {
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
            binding.main.addView(button);
        }
    }
}