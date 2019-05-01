package com.strikalov.weatherapp.model.entities;

/**
 * Константы для обозначения картинок с описанием, которые соответствуют
 * прогнозам погоды
 */
public enum WeatherPicture {

    P01("CLEAR_SKY"),
    P02("FEW_CLOUDS"),
    P03("SCATTERED_CLOUDS"),
    P04("BROKEN_CLOUDS"),
    P09("SHOWER_RAIN"),
    P10("RAIN"),
    P11("THUNDERSTORM"),
    P13("SNOW"),
    P50("MIST"),
    NO_ICON("NO_ICON");


    private String description;

    WeatherPicture(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "WeatherPicture{" +
                "description='" + description + '\'' +
                "}\n";
    }
}
