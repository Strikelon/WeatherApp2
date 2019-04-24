package com.strikalov.weatherapp.model.interactors;

import com.strikalov.weatherapp.model.entities.City;

import java.util.List;

import io.reactivex.Completable;

public interface CityDatabaseInteractor {

    Completable insertCityListInDatabase(List<City> cities);

}
