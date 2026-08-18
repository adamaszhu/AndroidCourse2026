package com.adamaszhu.androidcourse.demo;

import android.view.View;

import com.adamaszhu.androidcourse.R;

public class LocationDemo extends Demo {
    @Override
    public int getTitleId() {
        return R.string.feature_location;
    }

    @Override
    public DemoButton[] getDemoButtons() {
        return new DemoButton[] {
                new DemoButton(R.string.feature_location, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
        };
    }

    @Override
    public void initialize() {
        getListener().updateOutput("-");
    }
}
