package com.adamaszhu.androidcourse.navigation;

import android.app.ComponentCaller;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.adamaszhu.androidcourse.utility.BaseActivity;
import com.adamaszhu.androidcourse.R;
import com.adamaszhu.androidcourse.databinding.ActivityFirstBinding;

public class FirstActivity extends BaseActivity {

    private final static int SECOND_ACTIVITY_RESULT_CODE = 10;

    private ActivityFirstBinding binding;

    public void load() {
        binding = ActivityFirstBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    public void setup() {
        setTitle(R.string.activity_first);
        binding.btnOpenFeatureTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                intent.putExtra(SecondActivity.MESSAGE_KEY, getString(R.string.activity_first));
                startActivityForResult(intent, SECOND_ACTIVITY_RESULT_CODE);
            }
        });
        binding.btnOpenWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://google.com.au"));
                startActivity(intent);
            }
        });
        binding.btnShareText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.activity_first));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data, @NonNull ComponentCaller caller) {
        if (requestCode == SECOND_ACTIVITY_RESULT_CODE && data != null) {
            binding.tvResult.setText(data.getStringExtra(SecondActivity.RESULT_KEY));
        }
        super.onActivityResult(requestCode, resultCode, data, caller);
    }
}