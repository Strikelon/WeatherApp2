package com.strikalov.weatherapp.model.interactors;

import com.strikalov.weatherapp.model.entities.City;
import com.strikalov.weatherapp.model.repositories.CityDatabaseRepository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CityDatabaseInteractorImpl implements CityDatabaseInteractor {

    private CityDatabaseRepository cityDatabaseRepository;
    private Scheduler subscribeOnScheduler;
    private Scheduler observeOnScheduler;

    public CityDatabaseInteractorImpl(
            CityDatabaseRepository cityDatabaseRepository,
            Scheduler subscribeOnScheduler,
            Scheduler observeOnScheduler
    ){
        this.cityDatabaseRepository = cityDatabaseRepository;
        this.subscribeOnScheduler = subscribeOnScheduler;
        this.observeOnScheduler = observeOnScheduler;
    }

    public CityDatabaseInteractorImpl(CityDatabaseRepository cityDatabaseRepository){
        this(cityDatabaseRepository, Schedulers.io(), AndroidSchedulers.mainThread());
    }

    @Override
    public Completable insertCityListInDatabase(List<City> cities) {
        return cityDatabaseRepository.insertCityList(cities)
                .subscribeOn(subscribeOnScheduler)
                .observeOn(observeOnScheduler);
    }
}
