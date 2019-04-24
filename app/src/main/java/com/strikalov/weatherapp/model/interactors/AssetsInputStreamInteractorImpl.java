package com.strikalov.weatherapp.model.interactors;

import com.strikalov.weatherapp.model.entities.City;
import com.strikalov.weatherapp.model.repositories.AssetsInputStreamRepository;
import com.strikalov.weatherapp.model.repositories.AssetsInputStreamRepositoryImpl;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AssetsInputStreamInteractorImpl implements AssetsInputStreamInteractor {

    private AssetsInputStreamRepository assetsInputStreamRepository;
    private Scheduler subscribeOnScheduler;
    private Scheduler observeOnScheduler;

    public AssetsInputStreamInteractorImpl(
            AssetsInputStreamRepository assetsInputStreamRepository,
            Scheduler subscribeOnScheduler,
            Scheduler observeOnScheduler){

        this.assetsInputStreamRepository = assetsInputStreamRepository;
        this.subscribeOnScheduler = subscribeOnScheduler;
        this.observeOnScheduler = observeOnScheduler;

    }

    public AssetsInputStreamInteractorImpl(AssetsInputStreamRepository assetsInputStreamRepository){

        this(assetsInputStreamRepository, Schedulers.io(), AndroidSchedulers.mainThread());

    }

    @Override
    public Single<List<City>> downloadCityList() {
        return Single.fromCallable(new Callable<List<City>>() {
            @Override
            public List<City> call() throws Exception {
                return assetsInputStreamRepository.downloadCityList();
            }
        }).subscribeOn(subscribeOnScheduler)
                .observeOn(observeOnScheduler);
    }
}
