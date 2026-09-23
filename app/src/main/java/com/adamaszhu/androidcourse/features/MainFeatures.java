package com.adamaszhu.androidcourse.features;

import com.adamaszhu.androidcourse.R;
import com.adamaszhu.androidcourse.components.ComponentsActivity;
import com.adamaszhu.androidcourse.navigation.FirstActivity;
import com.adamaszhu.androidcourse.fragment.FragmentActivity;
import com.adamaszhu.androidcourse.demo.Demo;
import com.adamaszhu.androidcourse.demo.DemoActivity;
import com.adamaszhu.androidcourse.layout.LayoutFeatures;
import com.adamaszhu.androidcourse.network.NetworkDemo;
import com.adamaszhu.androidcourse.sensor.SensorDemo;
import com.adamaszhu.androidcourse.service.ServiceDemo;
import com.adamaszhu.androidcourse.storage.StorageDemo;
import com.adamaszhu.androidcourse.thread.ThreadDemo;

import java.util.Arrays;

public enum MainFeatures implements Feature {
    MAIN, LAYOUT, NAVIGATION, UI_COMPONENTS, FRAGMENT, THREAD, SERVICE, SENSOR, STORAGE, NETWORK;

    @Override
    public int getTitleId() {
        switch (this) {
            case MAIN:
                return R.string.feature_main;
            case LAYOUT:
                return R.string.feature_layout;
            case NAVIGATION:
                return R.string.feature_navigation;
            case UI_COMPONENTS:
                return R.string.feature_ui_components;
            case FRAGMENT:
                return R.string.feature_fragment;
            case THREAD:
                return R.string.feature_thread;
            case SERVICE:
                return R.string.feature_service;
            case SENSOR:
                return R.string.feature_sensor;
            case STORAGE:
                return R.string.feature_storage;
            case NETWORK:
                return R.string.feature_network;
            default:
                throw new RuntimeException();
        }
    }

    @Override
    public Class getActivityClass() {
        switch (this) {
            case MAIN:
            case LAYOUT:
                return FeaturesActivity.class;
            case NAVIGATION:
                return FirstActivity.class;
            case UI_COMPONENTS:
                return ComponentsActivity.class;
            case THREAD:
            case SERVICE:
            case SENSOR:
            case STORAGE:
            case NETWORK:
                return DemoActivity.class;
            case FRAGMENT:
                return FragmentActivity.class;
            default:
                throw new RuntimeException();
        }
    }

    @Override
    public Feature[] getSubFeatures() {
        switch (this) {
            case MAIN:
                Feature[] features = MainFeatures.values();
                return Arrays.copyOfRange(features, 1, features.length);
            case LAYOUT:
                return LayoutFeatures.values();
            default:
                return null;
        }
    }

    @Override
    public Demo getDemo() {
        switch (this) {
            case THREAD:
                return new ThreadDemo();
            case SERVICE:
                return new ServiceDemo();
            case SENSOR:
                return new SensorDemo();
            case NETWORK:
                return new NetworkDemo();
            case STORAGE:
                return new StorageDemo();
            default:
                return null;
        }
    }
}
