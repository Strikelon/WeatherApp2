package com.strikalov.weatherapp.model.interactors;

import com.strikalov.weatherapp.model.entities.City;

import java.util.List;

import io.reactivex.Single;

public interface AssetsInputStreamInteractor {

    Single<List<City>> downloadCityList();

}
