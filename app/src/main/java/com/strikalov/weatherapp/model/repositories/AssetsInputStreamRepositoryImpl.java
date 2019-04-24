package com.strikalov.weatherapp.model.repositories;

import android.util.Log;

import com.google.gson.Gson;
import com.strikalov.weatherapp.common.Constants;
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

    private InputStream assetsInputStream;

    public AssetsInputStreamRepositoryImpl(InputStream assetsInputStream){

        this.assetsInputStream = assetsInputStream;

    }

    @Override
    public List<City> downloadCityList() {

        if (assetsInputStream != null) {

            StringBuilder text = new StringBuilder();
            Gson gson = new Gson();
            List<City> cityList = new ArrayList<>();

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
                Log.e(Constants.TAG_APP_ERROS, "JsonFileError: ", e);
            }

            CityGsonArray cityGsonArray = gson.fromJson(text.toString(), CityGsonArray.class);
            CityGson[] citiesGson = cityGsonArray.getCityArray();

            for (CityGson cityGson : citiesGson) {

                City city = new City(cityGson.getId(), cityGson.getName());

                cityList.add(city);

            }

            return cityList;

        }else {

            return null;

        }
    }
}
