package com.adamaszhu.androidcourse.demo;

import android.app.Activity;

import java.io.Serializable;

public abstract class Demo implements Serializable {
    private OutputListener listener;

    protected Activity activity;

    public void setListener(OutputListener listener) {
        this.listener = listener;
    }

    public void setActivity(Activity activity) { this.activity = activity; }

    public OutputListener getListener() {
        return listener;
    }

    public abstract int getTitleId();

    public abstract DemoButton[] getDemoButtons();

    public void initialize() {}

    public void show() {}

    public void hide() {}
}
