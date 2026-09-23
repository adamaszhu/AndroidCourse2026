package com.adamaszhu.androidcourse.demo;

import android.view.View;

public class DemoButton {

    private final int titleId;
    private final View.OnClickListener listener;

    public DemoButton(int titleId,
                      View.OnClickListener listener) {
        this.listener = listener;
        this.titleId = titleId;
    }

    public int getTitleId() { return titleId; }
    public View.OnClickListener getListener() { return listener; }
}
