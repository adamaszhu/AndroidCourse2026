package com.adamaszhu.androidcourse.components;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.adamaszhu.androidcourse.R;
import com.adamaszhu.androidcourse.databinding.ActivityComponentsBinding;
import com.adamaszhu.androidcourse.utility.BaseActivity;

public class ComponentsActivity extends BaseActivity {

    private static final String TAG = ComponentsActivity.class.getName();

    private ActivityComponentsBinding binding;

    public void load() {
        binding = ActivityComponentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Log.i(TAG, "Screen is created");
    }

    public void setup() {
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
        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull RadioGroup radioGroup, int i) {
                RadioButton button = radioGroup.findViewById(i);
                binding.tvResult.setText(button.getText());
            }
        });
        CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton compoundButton, boolean b) {
                binding.tvResult.setText(b ? "Checked" : "Unchecked");
            }
        };
        binding.checkbox.setOnCheckedChangeListener(onCheckedChangeListener);
        binding.switchButton.setOnCheckedChangeListener(onCheckedChangeListener);
        binding.toggleButton.setOnCheckedChangeListener(onCheckedChangeListener);
    }
}