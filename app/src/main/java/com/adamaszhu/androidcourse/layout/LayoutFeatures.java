package com.adamaszhu.androidcourse.layout;

import com.adamaszhu.androidcourse.R;
import com.adamaszhu.androidcourse.demo.Demo;
import com.adamaszhu.androidcourse.features.Feature;

public enum LayoutFeatures implements Feature {
    LINEAR, CONSTRAINT, LIST, RECYCLE;

    @Override
    public int getTitleId() {
        switch (this) {
            case LINEAR:
                return R.string.feature_linear_layout;
            case CONSTRAINT:
                return R.string.feature_constraint_layout;
            case LIST:
                return R.string.feature_list;
            case RECYCLE:
                return R.string.feature_recycle;
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
            case LIST:
                return ListActivity.class;
            case RECYCLE:
                return RecycleActivity.class;
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
