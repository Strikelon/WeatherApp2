package com.strikalov.weatherapp.model.repositories;

import com.strikalov.weatherapp.model.databases.AppDatabase;
import com.strikalov.weatherapp.model.databases.CityEntity;
import com.strikalov.weatherapp.model.entities.City;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Completable;

public class CityDatabaseRepositoryImpl implements CityDatabaseRepository {

    private AppDatabase appDatabase;

    public CityDatabaseRepositoryImpl(AppDatabase appDatabase){
        this.appDatabase = appDatabase;
    }

    @Override
    public Completable insertCityList(List<City> cities) {

        final List<CityEntity> cityEntityList = new ArrayList<>();

        for(City city: cities){

            CityEntity cityEntity = new CityEntity(city.getCityIndex(), city.getCityName());

            cityEntityList.add(cityEntity);
        }

        return Completable.fromCallable(new Callable<Long[]>() {
            @Override
            public Long[] call() throws Exception {
                return appDatabase.cityDatabaseDao().insertList(cityEntityList);
            }
        });

    }
}
