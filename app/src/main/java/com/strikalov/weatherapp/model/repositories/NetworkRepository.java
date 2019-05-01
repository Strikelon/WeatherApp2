package com.strikalov.weatherapp.model.repositories;

import com.strikalov.weatherapp.model.entities.WeatherForecast;

import io.reactivex.Observable;

/**
 * Репозиторий, для получения прогноза погоды у сервера-погоды
 */
public interface NetworkRepository {

    /**
     * Метод возвращает прогноз погоды по id города
     * @param cityId
     * @return
     */
    Observable<WeatherForecast> getWeatherForecast(String cityId);

}
