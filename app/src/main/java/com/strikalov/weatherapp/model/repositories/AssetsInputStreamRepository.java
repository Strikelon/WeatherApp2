package com.strikalov.weatherapp.model.repositories;

import com.strikalov.weatherapp.model.entities.City;

import java.io.InputStream;
import java.util.List;

public interface AssetsInputStreamRepository {

    List<City> downloadCityList();

}
