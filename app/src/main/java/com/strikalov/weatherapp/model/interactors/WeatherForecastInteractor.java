package com.strikalov.weatherapp.model.interactors;

import com.strikalov.weatherapp.model.entities.WeatherForecast;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;

/**
 * Интерактор для работы с объектами класса WeatherForecast
 */
public interface WeatherForecastInteractor {

    /**
     * Метод возвращает прогноз погоды от сервера погоды, для города имеющего
     * индекс равный cityId
     * @param cityId
     * @return
     */
    Observable<WeatherForecast> loadWeatherForecast(String cityId);

    /**
     * Метод сохраняет индекс города и прогноз погоды для этого города
     * в базе данных
     * @param cityIndex
     * @param weatherForecast
     * @return
     */
    Completable insertWeatherForecastInDatabase(String cityIndex,WeatherForecast weatherForecast);

    /**
     * Метод возвращает из базы данных прогноз погоды для города с искомым cityIndex
     * @param cityIndex
     * @return
     */
    Maybe<WeatherForecast> loadWeatherForecastFromDatabase(String cityIndex);

}
