package com.strikalov.weatherapp.model.repositories;

import com.strikalov.weatherapp.model.entities.City;

import java.util.List;

import io.reactivex.Completable;

public interface CityDatabaseRepository {

    Completable insertCityList(List<City> cities);

}
