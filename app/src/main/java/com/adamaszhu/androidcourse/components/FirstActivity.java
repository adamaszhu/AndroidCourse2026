package com.adamaszhu.androidcourse.components;

import android.app.ComponentCaller;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.adamaszhu.androidcourse.BaseActivity;
import com.adamaszhu.androidcourse.R;
import com.adamaszhu.androidcourse.databinding.ActivityFirstBinding;

public class FirstActivity extends BaseActivity {

    private final static int SECOND_ACTIVITY_RESULT = 10;

    private ActivityFirstBinding binding;

    public void load() {
        binding = ActivityFirstBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle(R.string.activity_first);
    }

    public void setup() {
        binding.btnNavigationData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                intent.putExtra(SecondActivity.MESSAGE_KEY, getString(R.string.activity_first));
                startActivityForResult(intent, SECOND_ACTIVITY_RESULT);
            }
        });
        binding.btnOpenWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://google.com.au"));
                startActivity(intent);
            }
        });
        binding.btnShareContent.setOnClickListener(new View.OnClickListener() {
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
        if (requestCode == SECOND_ACTIVITY_RESULT && data != null) {
            binding.tvResult.setText(data.getStringExtra(SecondActivity.RESULT_KEY));
        }
        super.onActivityResult(requestCode, resultCode, data, caller);
    }
}