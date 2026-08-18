package com.adamaszhu.androidcourse.navigation;

import android.content.Intent;
import android.view.View;

import com.adamaszhu.androidcourse.utility.BaseActivity;
import com.adamaszhu.androidcourse.R;
import com.adamaszhu.androidcourse.databinding.ActivitySecondBinding;

public class SecondActivity extends BaseActivity {

    public final static String MESSAGE_KEY = "MESSAGE";
    public final static String RESULT_KEY = "RESULT";

    private ActivitySecondBinding binding;

    public void load() {
        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    public void setup() {
        setTitle(R.string.activity_second);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MESSAGE_KEY);
        binding.tvResult.setText(message);
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra(RESULT_KEY, getString(R.string.activity_second));
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}