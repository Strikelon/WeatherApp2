package com.strikalov.weatherapp.model.network;

import com.strikalov.weatherapp.model.network.apimodels.WeatherForecastApi;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WebApiService {

    /**
     * Get запрос у сервера погоды, для получения прогноза погоды в json формате
     * Используется cityId для индетификации нужного города и keyApi так как для пользования
     * сервером нужно быть зарегистрированным пользователем и иметь ключ
     *
     * @param cityId
     * @param keyApi
     * @return
     */
    @GET("data/2.5/weather")
    Observable<WeatherForecastApi> loadWeather(@Query("id") String cityId, @Query("appid") String keyApi);

}
