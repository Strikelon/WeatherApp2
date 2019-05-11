package com.strikalov.weatherapp.model.databases;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Сущность для обозначения прогноза погоды в базе данных
 */
@Entity
public class WeatherForecastEntity {

    /**
     * Первичный ключ для базы данных
     */
    @PrimaryKey
    private long id;

    /**
     * Индекс города для запроса прогноза погоды у сервера-погоды
     */
    @ColumnInfo(index = true)
    private String cityIndex;

    /**
     * Дата прогноза погоды
     */
    private String date;

    /**
     * Значение температуры в градусах цельсия
     */
    private String temperatureDegreesCelsius;

    /**
     * Значение температуры в градусах Фаренгейта
     */
    private String temperatureDegreesFahrenheit;

    /**
     * Значение скорости ветра в метрах в секунду
     */
    private String windMetersPerSecond;

    /**
     * Значение скорости ветра в километрах в час
     */
    private String windKilometersPerHour;

    /**
     * Направление ветра
     */
    private String windDirection;

    /**
     * Значение атмосферного давления в милиметрах ртутного столба
     */
    private String pressureMillimeters;

    /**
     * Значение атмосферного давления в гекто паскалях
     */
    private String pressureHpa;

    /**
     * Значение влажности в процентах
     */
    private String humidity;

    /**
     * Картинка, которая соответствует прогнозу погоды
     */
    private String weatherPicture;

    /**
     * Время восхода солнца
     */
    private String sunriseTime;

    /**
     * Время захода солнца
     */
    private String sunsetTime;

    public WeatherForecastEntity(String cityIndex,
                                 String date,
                                 String temperatureDegreesCelsius,
                                 String temperatureDegreesFahrenheit,
                                 String windMetersPerSecond,
                                 String windKilometersPerHour,
                                 String windDirection,
                                 String pressureMillimeters,
                                 String pressureHpa,
                                 String humidity,
                                 String weatherPicture,
                                 String sunriseTime,
                                 String sunsetTime){

        this.id = Long.parseLong(cityIndex);
        this.cityIndex = cityIndex;
        this.date = date;
        this.temperatureDegreesCelsius = temperatureDegreesCelsius;
        this.temperatureDegreesFahrenheit = temperatureDegreesFahrenheit;
        this.windMetersPerSecond = windMetersPerSecond;
        this.windKilometersPerHour = windKilometersPerHour;
        this.windDirection = windDirection;
        this.pressureMillimeters = pressureMillimeters;
        this.pressureHpa = pressureHpa;
        this.humidity = humidity;
        this.weatherPicture = weatherPicture;
        this.sunriseTime = sunriseTime;
        this.sunsetTime = sunsetTime;

    }

    /**
     * Методы геттеры и сеттеры
     */

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCityIndex() {
        return cityIndex;
    }

    public void setCityIndex(String cityIndex) {
        this.cityIndex = cityIndex;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTemperatureDegreesCelsius() {
        return temperatureDegreesCelsius;
    }

    public void setTemperatureDegreesCelsius(String temperatureDegreesCelsius) {
        this.temperatureDegreesCelsius = temperatureDegreesCelsius;
    }

    public String getTemperatureDegreesFahrenheit() {
        return temperatureDegreesFahrenheit;
    }

    public void setTemperatureDegreesFahrenheit(String temperatureDegreesFahrenheit) {
        this.temperatureDegreesFahrenheit = temperatureDegreesFahrenheit;
    }

    public String getWindMetersPerSecond() {
        return windMetersPerSecond;
    }

    public void setWindMetersPerSecond(String windMetersPerSecond) {
        this.windMetersPerSecond = windMetersPerSecond;
    }

    public String getWindKilometersPerHour() {
        return windKilometersPerHour;
    }

    public void setWindKilometersPerHour(String windKilometersPerHour) {
        this.windKilometersPerHour = windKilometersPerHour;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getPressureMillimeters() {
        return pressureMillimeters;
    }

    public void setPressureMillimeters(String pressureMillimeters) {
        this.pressureMillimeters = pressureMillimeters;
    }

    public String getPressureHpa() {
        return pressureHpa;
    }

    public void setPressureHpa(String pressureHpa) {
        this.pressureHpa = pressureHpa;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWeatherPicture() {
        return weatherPicture;
    }

    public void setWeatherPicture(String weatherPicture) {
        this.weatherPicture = weatherPicture;
    }

    public String getSunriseTime() {
        return sunriseTime;
    }

    public void setSunriseTime(String sunriseTime) {
        this.sunriseTime = sunriseTime;
    }

    public String getSunsetTime() {
        return sunsetTime;
    }

    public void setSunsetTime(String sunsetTime) {
        this.sunsetTime = sunsetTime;
    }
}
