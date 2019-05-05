package com.strikalov.weatherapp.model.databases;

import android.arch.persistence.room.ColumnInfo;
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
    @ColumnInfo(index = true)
    private String cityIndex;

    /**
     * Название города
     */
    private String cityName;

    /**
     * Параметр, обозначающий, добавлен ли город в избранный список
     */
    @ColumnInfo(index = true)
    private int isFavorites;

    public CityEntity(String cityIndex, String cityName, int isFavorites){

        this.cityIndex = cityIndex;
        this.cityName = cityName;
        this.isFavorites = isFavorites;
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

    public int getIsFavorites() {
        return isFavorites;
    }

    public void setIsFavorites(int isFavorites) {
        this.isFavorites = isFavorites;
    }
}
