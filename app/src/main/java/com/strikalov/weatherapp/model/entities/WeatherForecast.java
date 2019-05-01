package com.strikalov.weatherapp.model.entities;

public class WeatherForecast {

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
    private WindDirection windDirection;

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
    private WeatherPicture weatherPicture;

    /**
     * Время восхода солнца
     */
    private String sunriseTime;

    /**
     * Время захода солнца
     */
    private String sunsetTime;

    public WeatherForecast(String date,
                           String temperatureDegreesCelsius,
                           String temperatureDegreesFahrenheit,
                           String windMetersPerSecond,
                           String windKilometersPerHour,
                           WindDirection windDirection,
                           String pressureMillimeters,
                           String pressureHpa,
                           String humidity,
                           WeatherPicture weatherPicture,
                           String sunriseTime,
                           String sunsetTime){

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

    public WindDirection getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(WindDirection windDirection) {
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

    public WeatherPicture getWeatherPicture() {
        return weatherPicture;
    }

    public void setWeatherPicture(WeatherPicture weatherPicture) {
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

    /**
     * Методы для облегчения логирования значения объекта класса WeatherForecast
     */

    @Override
    public String toString() {
        return "WeatherForecast{" +
                "date='" + date + '\'' +
                ", temperatureDegreesCelsius='" + temperatureDegreesCelsius + '\'' +
                ", temperatureDegreesFahrenheit='" + temperatureDegreesFahrenheit + '\'' +
                ", windMetersPerSecond='" + windMetersPerSecond + '\'' +
                ", windKilometersPerHour='" + windKilometersPerHour + '\'' +
                ", windDirection=" + windDirection +
                ", pressureMillimeters='" + pressureMillimeters + '\'' +
                ", pressureHpa='" + pressureHpa + '\'' +
                ", humidity='" + humidity + '\'' +
                ", weatherPicture=" + weatherPicture +
                ", sunriseTime='" + sunriseTime + '\'' +
                ", sunsetTime='" + sunsetTime + '\'' +
                "}\n";
    }
}
