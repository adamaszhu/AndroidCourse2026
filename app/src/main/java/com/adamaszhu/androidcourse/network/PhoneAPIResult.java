package com.adamaszhu.androidcourse.network;

public interface PhoneAPIResult<Object> {
    public void onReceiveObject(Object object);
    public void onFailure();
}
