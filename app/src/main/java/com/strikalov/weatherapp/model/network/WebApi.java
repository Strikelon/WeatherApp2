package com.strikalov.weatherapp.model.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebApi {

    private final String URL_REQUEST = "http://api.openweathermap.org/";

    public WebApiService getWebApiService(){

        /**
         * Создаем объект класса Retrofit для запроса прогноза погоды у погодного сервера
         */
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(URL_REQUEST)
                .build();

        return retrofit.create(WebApiService.class);
    }

}
