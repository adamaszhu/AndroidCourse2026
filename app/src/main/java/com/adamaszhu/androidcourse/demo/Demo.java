package com.adamaszhu.androidcourse.demo;

import java.io.Serializable;

public abstract class Demo implements Serializable {
    private OutputListener listener;

    public void setListener(OutputListener listener) {
        this.listener = listener;
    }

    public OutputListener getListener() {
        return listener;
    }

    public abstract int getTitleId();

    public abstract DemoButton[] getDemoButtons();

    public abstract void initialize();
}
