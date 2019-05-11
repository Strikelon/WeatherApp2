package com.strikalov.weatherapp.model.repositories;

import com.strikalov.weatherapp.model.databases.WeatherForecastEntity;
import com.strikalov.weatherapp.model.entities.WeatherForecast;

import io.reactivex.Completable;
import io.reactivex.Maybe;

/**
 * Репозитрий для работы с таблицей WeatherForecastEntity из базы данных
 */
public interface WeatherForecastDatabaseRepository {

    /**
     * Метод сохраняет прогноз погоды в базе данных
     * @param cityIndex
     * @param weatherForecast
     * @return
     */
    Completable insertWeatherForecast(String cityIndex,WeatherForecast weatherForecast);

    /**
     * Метод возвращает из базы данных прогноз погоды, для города с нужным cityIndex
     * @param cityIndex
     * @return
     */
    Maybe<WeatherForecast> getWeatherForecastByCityIndex(String cityIndex);

}
