package com.strikalov.weatherapp.model.databases;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Сущность для обозначения города в базе данных
 */
@Entity
public class CityEntity {

    /**
     * Первичный ключ в базе данных
     */
    @PrimaryKey(autoGenerate = true)
    private long id;

    /**
     * Индекс города для запроса прогноза погоды у сервера-погоды
     */
    private String cityIndex;

    /**
     * Название города
     */
    private String cityName;

    /**
     * Параметр, обозначающий, добавлен ли город в избранный список
     */
    private int isSelected;

    public CityEntity(String cityIndex, String cityName, int isSelected){

        this.cityIndex = cityIndex;
        this.cityName = cityName;
        this.isSelected = isSelected;
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

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(int isSelected) {
        this.isSelected = isSelected;
    }
}
