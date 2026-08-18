package com.adamaszhu.androidcourse.features;

import com.adamaszhu.androidcourse.R;
import com.adamaszhu.androidcourse.components.ComponentsActivity;
import com.adamaszhu.androidcourse.components.FirstActivity;
import com.adamaszhu.androidcourse.demo.Demo;
import com.adamaszhu.androidcourse.demo.DemoActivity;
import com.adamaszhu.androidcourse.demo.LocationDemo;

public enum MainFeatures implements Feature {
    MAIN, UI_COMPONENTS, NAVIGATION, LOCATION;

    @Override
    public int getTitleId() {
        switch (this) {
            case MAIN:
                return R.string.feature_main;
            case NAVIGATION:
                return R.string.feature_navigation;
            case UI_COMPONENTS:
                return R.string.feature_ui_components;
            case LOCATION:
                return R.string.feature_location;
            default:
                throw new RuntimeException();
        }
    }

    @Override
    public Class getActivityClass() {
        switch (this) {
            case MAIN:
                return FeaturesActivity.class;
            case NAVIGATION:
                return FirstActivity.class;
            case UI_COMPONENTS:
                return ComponentsActivity.class;
            case LOCATION:
                return DemoActivity.class;
            default:
                throw new RuntimeException();
        }
    }

    @Override
    public Feature[] getSubFeatures() {
        switch (this) {
            case MAIN:
                return new Feature[] {
                        UI_COMPONENTS, NAVIGATION, LOCATION, MAIN
                };
            case NAVIGATION:
            case UI_COMPONENTS:
            case LOCATION:
                return null;
            default:
                throw new RuntimeException();
        }
    }

    @Override
    public Demo getDemo() {
        switch (this) {
            case MAIN:
            case NAVIGATION:
            case UI_COMPONENTS:
                return null;
            case LOCATION:
                return new LocationDemo();
            default:
                throw new RuntimeException();
        }
    }
}
