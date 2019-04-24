package com.strikalov.weatherapp.model.entities;

public class City {

    private String cityIndex;

    private String cityName;

    public City(String cityIndex, String cityName){

        this.cityIndex = cityIndex;
        this.cityName = cityName;

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

    @Override
    public String toString() {
        return "City{" +
                "cityIndex='" + cityIndex + '\'' +
                ", cityName='" + cityName + '\'' +
                "}\n";
    }
}
