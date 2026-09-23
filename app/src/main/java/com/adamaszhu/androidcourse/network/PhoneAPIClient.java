package com.adamaszhu.androidcourse.network;

import com.adamaszhu.androidcourse.entity.Phone;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PhoneAPIClient {

    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.restful-api.dev")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private final PhoneAPIService service = retrofit.create(PhoneAPIService.class);

    public void getPhones(PhoneAPIResult<List<Phone>> result) {
        Call<List<Phone>> request = service.getPhones();
        request.enqueue(new Callback<List<Phone>>() {
            @Override
            public void onResponse(Call<List<Phone>> call, Response<List<Phone>> response) {
                result.onReceiveObject(response.body());
            }

            @Override
            public void onFailure(Call<List<Phone>> call, Throwable t) {
                result.onFailure();
            }
        });
    }

    public void getPhone(int id, PhoneAPIResult<Phone> result) {
        Call<Phone> request = service.getPhone(1);
        request.enqueue(new Callback<Phone>() {
            @Override
            public void onResponse(Call<Phone> call, Response<Phone> response) {
                result.onReceiveObject(response.body());
            }

            @Override
            public void onFailure(Call<Phone> call, Throwable t) {
                result.onFailure();
            }
        });
    }
}