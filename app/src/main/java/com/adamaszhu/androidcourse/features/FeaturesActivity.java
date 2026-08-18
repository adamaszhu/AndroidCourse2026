package com.adamaszhu.androidcourse.features;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.adamaszhu.androidcourse.utility.BaseActivity;
import com.adamaszhu.androidcourse.databinding.ActivityFeaturesBinding;
import com.adamaszhu.androidcourse.demo.DemoActivity;

public class FeaturesActivity extends BaseActivity {

    private static final String INTENT_KEY = "FEATURE";

    private ActivityFeaturesBinding binding;

    private Feature feature;

    public void load() {
        binding = ActivityFeaturesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        feature = intent.getSerializableExtra(INTENT_KEY, Feature.class);
        if (feature == null) {
            feature = MainFeatures.MAIN;
        }
    }

    public void setup() {
        setTitle(feature.getTitleId());
        setupButtons();
    }

    private void setupButtons() {
        for (Feature subFeature : feature.getSubFeatures()) {
            Button button = new Button(this);
            button.setText(subFeature.getTitleId());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Class destination = subFeature.getActivityClass();
                    Intent intent = new Intent(FeaturesActivity.this, destination);
                    if (destination == DemoActivity.class) {
                        intent.putExtra(DemoActivity.INTENT_KEY, subFeature.getDemo());
                    } else if (destination == FeaturesActivity.class) {
                        intent.putExtra(FeaturesActivity.INTENT_KEY, subFeature);
                    }
                    startActivity(intent);
                }
            });
            binding.content.addView(button);
        }
    }
}