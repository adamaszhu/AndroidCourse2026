package com.adamaszhu.androidcourse.features;

import com.adamaszhu.androidcourse.demo.Demo;

import java.io.Serializable;

public interface Feature extends Serializable {
    public int getTitleId();

    public Class getActivityClass();

    public Feature[] getSubFeatures();

    public Demo getDemo();
}
