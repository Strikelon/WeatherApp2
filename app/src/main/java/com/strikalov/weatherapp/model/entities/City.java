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

    /**
     * Выбран ли город для прогноза погоды пользователем, или нет
     * Одновременно может быть выбран только один город
     */
    private boolean isSelected;

    public City(String cityIndex, String cityName){

        this(cityIndex, cityName, false);

    }

    public City(String cityIndex, String cityName, boolean isSelected){

        this.cityIndex = cityIndex;
        this.cityName = cityName;
        this.isSelected = isSelected;

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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    /**
     * Методы для облегчения логирования значения объекта класса City
     */

    @Override
    public String toString() {
        return "City{" +
                "cityIndex='" + cityIndex + '\'' +
                ", cityName='" + cityName + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }
}
