package com.strikalov.weatherapp.model.entitesgson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class CityGsonArray {

    @SerializedName("citylist")
    @Expose
    private CityGson[] city;

    @Override
    public String toString() {
        return "CityList{\n" +
                Arrays.toString(city) +
                "\n}";
    }

    public CityGson[] getCityArray() {
        return city;
    }

    public void setCityArray(CityGson[] city) {
        this.city = city;
    }

}
