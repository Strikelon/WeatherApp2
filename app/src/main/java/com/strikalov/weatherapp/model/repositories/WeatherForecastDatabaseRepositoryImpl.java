package com.strikalov.weatherapp.model.repositories;

import com.strikalov.weatherapp.model.databases.AppDatabase;
import com.strikalov.weatherapp.model.databases.WeatherForecastEntity;
import com.strikalov.weatherapp.model.entities.WeatherForecast;

import java.util.concurrent.Callable;

import io.reactivex.Completable;

/**
 * Репозитрий для работы с таблицей WeatherForecastEntity из базы данных
 */
public class WeatherForecastDatabaseRepositoryImpl implements WeatherForecastDatabaseRepository {

    /**
     * Ссылка на базу данных
     */
    private AppDatabase appDatabase;

    public WeatherForecastDatabaseRepositoryImpl(AppDatabase appDatabase){
        this.appDatabase = appDatabase;
    }

    /**
     * Метод сохраняет прогноз погоды в базе данных
     * @param cityIndex
     * @param weatherForecast
     * @return
     */
    @Override
    public Completable insertWeatherForecast(String cityIndex, WeatherForecast weatherForecast) {

        final WeatherForecastEntity weatherForecastEntity = createWeatherForecastEntity(cityIndex, weatherForecast);

        return Completable.fromCallable(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return appDatabase.weatherForecastDatabaseDao().insert(weatherForecastEntity);
            }
        });
    }

    /**
     * Метод создает объект класса WeatherForecastEntity из входящих параметров и возвращает его
     * @param cityIndex
     * @param weatherForecast
     * @return
     */
    private WeatherForecastEntity createWeatherForecastEntity(String cityIndex, WeatherForecast weatherForecast){

        return new WeatherForecastEntity(cityIndex,
                weatherForecast.getDate(),
                weatherForecast.getTemperatureDegreesCelsius(),
                weatherForecast.getTemperatureDegreesFahrenheit(),
                weatherForecast.getWindMetersPerSecond(),
                weatherForecast.getWindKilometersPerHour(),
                weatherForecast.getWindDirection().getDescription(),
                weatherForecast.getPressureMillimeters(),
                weatherForecast.getPressureHpa(),
                weatherForecast.getHumidity(),
                weatherForecast.getWeatherPicture().getDescription(),
                weatherForecast.getSunriseTime(),
                weatherForecast.getSunsetTime());

    }
}
