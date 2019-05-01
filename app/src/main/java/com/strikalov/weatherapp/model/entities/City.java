package com.strikalov.weatherapp.model.entities;

/**
 * Сущность город
 */
public class City {

    /**
     * Индекс города для запроса прогноза погоды у сервера-погоды
     */
    private String cityIndex;

    /**
     * Название города
     */
    private String cityName;

    public City(String cityIndex, String cityName){

        this.cityIndex = cityIndex;
        this.cityName = cityName;

    }

    /**
     * Методы геттеры и сеттеры
     */

    public String getCityIndex() {
        return cityIndex;
    }

    public void setCityIndex(String cityIndex) {
        this.cityIndex = cityIndex;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     * Методы для облегчения логирования значения объекта класса City
     */

    @Override
    public String toString() {
        return "City{" +
                "cityIndex='" + cityIndex + '\'' +
                ", cityName='" + cityName + '\'' +
                "}\n";
    }
}
