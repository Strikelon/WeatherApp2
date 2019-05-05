package com.strikalov.weatherapp.model.repositories;

import android.util.Log;

import com.google.gson.Gson;
import com.strikalov.weatherapp.model.entitesgson.CityGson;
import com.strikalov.weatherapp.model.entitesgson.CityGsonArray;
import com.strikalov.weatherapp.model.entities.City;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AssetsInputStreamRepositoryImpl implements AssetsInputStreamRepository {

    private static final String ERROR_TAG = "ERROR_INPUT_REPOSITORY";

    /**
     * Входящий поток из json файла с городами из папки assets
     */
    private InputStream assetsInputStream;

    public AssetsInputStreamRepositoryImpl(InputStream assetsInputStream){

        this.assetsInputStream = assetsInputStream;

    }

    /**
     * Метод возвращает список объектов класса City из json файла из папки assets
     * @return
     */
    @Override
    public List<City> downloadCityList() {

        if (assetsInputStream != null) {

            //В объект text читается построчно json файл
            StringBuilder text = new StringBuilder();

            /**С помощью объекта gson, будем превращать прочитанный json файл
             * в объект cityGsonArray
             */
            Gson gson = new Gson();

            //Список городов, который будет возвращен, после наполнения
            List<City> cityList = new ArrayList<>();

            // Читаем json файл в объект text
            try {
                BufferedReader fileReader = new BufferedReader(new InputStreamReader(assetsInputStream));
                String line;
                while ((line = fileReader.readLine()) != null) {
                    text.append(line);
                    text.append('\n');
                }
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(ERROR_TAG, "JsonFileError: ", e);
            }

            /**
             * Превращаем прочитанный файл в объект cityGsonArray,
             * а из него получаем массив объектов CityGson[] citiesGson
             */
            CityGsonArray cityGsonArray = gson.fromJson(text.toString(), CityGsonArray.class);
            CityGson[] citiesGson = cityGsonArray.getCityArray();

            /**
             * Проходимся по получившемуся массиву и наполняем список cityList
             */
            for (CityGson cityGson : citiesGson) {

                City city = new City(cityGson.getId(), cityGson.getName());

                cityList.add(city);

            }

            return cityList;

        }else {

            // если assetsInputStream равен null, то возвращаем null
            return null;

        }
    }
}
