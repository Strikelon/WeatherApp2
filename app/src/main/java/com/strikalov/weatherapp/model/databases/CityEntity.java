package com.strikalov.weatherapp.model.databases;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class CityEntity {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String cityIndex;

    private String cityName;

    public CityEntity(String cityIndex, String cityName){

        this.cityIndex = cityIndex;
        this.cityName = cityName;

    }

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
}
