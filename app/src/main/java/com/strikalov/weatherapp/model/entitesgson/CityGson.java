package com.strikalov.weatherapp.model.entitesgson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Класс для преобразования информации из json файла в assets в объекты класса CityGson
 */
public class CityGson {

    @SerializedName("cityid")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                "}\n";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
