package com.adamaszhu.androidcourse.layout;

import com.adamaszhu.androidcourse.R;
import com.adamaszhu.androidcourse.demo.Demo;
import com.adamaszhu.androidcourse.features.Feature;

public enum LayoutFeatures implements Feature {
    LINEAR, CONSTRAINT;

    @Override
    public int getTitleId() {
        switch (this) {
            case LINEAR:
                return R.string.feature_linear_layout;
            case CONSTRAINT:
                return R.string.feature_constraint_layout;
            default:
                throw new RuntimeException();
        }
    }

    @Override
    public Class getActivityClass() {
        switch (this) {
            case LINEAR:
                return LinearLayoutActivity.class;
            case CONSTRAINT:
                return ConstraintLayoutActivity.class;
            default:
                throw new RuntimeException();
        }
    }

    @Override
    public Feature[] getSubFeatures() {
        return null;
    }

    @Override
    public Demo getDemo() {
        return null;
    }
}
