package com.strikalov.weatherapp.model.repositories;

import com.strikalov.weatherapp.model.databases.AppDatabase;
import com.strikalov.weatherapp.model.databases.WeatherForecastEntity;
import com.strikalov.weatherapp.model.entities.WeatherForecast;
import com.strikalov.weatherapp.model.entities.WeatherPicture;
import com.strikalov.weatherapp.model.entities.WindDirection;

import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.functions.Function;

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
     * Метод возвращает из базы данных прогноз погоды, для города с нужным cityIndex
     * @param cityIndex
     * @return
     */
    @Override
    public Maybe<WeatherForecast> getWeatherForecastByCityIndex(String cityIndex) {
        return appDatabase.weatherForecastDatabaseDao().getWeatherForecastByCityIndex(cityIndex)
                .map(new Function<WeatherForecastEntity, WeatherForecast>() {
                    @Override
                    public WeatherForecast apply(WeatherForecastEntity weatherForecastEntity) throws Exception {
                        return createWeatherForecast(weatherForecastEntity);
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

    /**
     * Метод получает объект класса WeatherForecastEntity в качестве параметра, с помощью преобразований
     * данных хранящихся в этом объекте создает новый объект класса WeatherForecast
     * @param weatherForecastEntity
     * @return
     */
    private WeatherForecast createWeatherForecast(WeatherForecastEntity weatherForecastEntity){
        WindDirection windDirection = createWindDirection(weatherForecastEntity.getWindDirection());
        WeatherPicture weatherPicture = createWeatherPicture(weatherForecastEntity.getWeatherPicture());

        return new WeatherForecast(weatherForecastEntity.getDate(),
                weatherForecastEntity.getTemperatureDegreesCelsius(),
                weatherForecastEntity.getTemperatureDegreesFahrenheit(),
                weatherForecastEntity.getWindMetersPerSecond(),
                weatherForecastEntity.getWindKilometersPerHour(),
                windDirection,
                weatherForecastEntity.getPressureMillimeters(),
                weatherForecastEntity.getPressureHpa(),
                weatherForecastEntity.getHumidity(),
                weatherPicture,
                weatherForecastEntity.getSunriseTime(),
                weatherForecastEntity.getSunsetTime());
    }

    /**
     * Метод возвращает константу класса WindDirection, соответствующую
     * входящему строковому параметру
     * @param windDirectionString
     * @return
     */
    private WindDirection createWindDirection(String windDirectionString){
        WindDirection windDirection;

        switch (windDirectionString){
            case "NORTH":
                windDirection = WindDirection.NORTH;
                break;
            case "NORTH_WEST":
                windDirection = WindDirection.NORTH_WEST;
                break;
            case "NORTH_EAST":
                windDirection = WindDirection.NORTH_EAST;
                break;
            case "WEST":
                windDirection = WindDirection.WEST;
                break;
            case "EAST":
                windDirection = WindDirection.EAST;
                break;
            case "SOUTH":
                windDirection = WindDirection.SOUTH;
                break;
            case "SOUTH_WEST":
                windDirection = WindDirection.SOUTH_WEST;
                break;
            case "SOUTH_EAST":
                windDirection = WindDirection.SOUTH_EAST;
                break;
            default:
                windDirection = WindDirection.NO_DIRECTION;
                break;
        }

        return windDirection;
    }


    /**
     * Метод возвращает константу класса WeatherPicture, соответствующую
     * входящему строковому параметру
     * @param weatherPictureString
     * @return
     */
    private WeatherPicture createWeatherPicture(String weatherPictureString){
        WeatherPicture weatherPicture;

        switch (weatherPictureString){
            case "CLEAR_SKY":
                weatherPicture = WeatherPicture.P01;
                break;
            case "FEW_CLOUDS":
                weatherPicture = WeatherPicture.P02;
                break;
            case "SCATTERED_CLOUDS":
                weatherPicture = WeatherPicture.P03;
                break;
            case "BROKEN_CLOUDS":
                weatherPicture = WeatherPicture.P04;
                break;
            case "SHOWER_RAIN":
                weatherPicture = WeatherPicture.P09;
                break;
            case "RAIN":
                weatherPicture = WeatherPicture.P10;
                break;
            case "THUNDERSTORM":
                weatherPicture = WeatherPicture.P11;
                break;
            case "SNOW":
                weatherPicture = WeatherPicture.P13;
                break;
            case "MIST":
                weatherPicture = WeatherPicture.P50;
                break;
            default:
                weatherPicture = WeatherPicture.NO_ICON;
                break;
        }

        return weatherPicture;
    }
}
