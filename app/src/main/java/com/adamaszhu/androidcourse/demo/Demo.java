package com.adamaszhu.androidcourse.demo;

public abstract class Demo {
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
