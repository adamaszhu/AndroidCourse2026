package com.adamaszhu.androidcourse.network;

import com.adamaszhu.androidcourse.entity.Phone;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PhoneAPIService {
    @GET("objects")
    Call<List<Phone>> getPhones();

    @GET("objects/{id}")
    Call<Phone> getPhone(@Path("id") int id);
}
