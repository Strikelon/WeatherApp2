package com.strikalov.weatherapp.model.interactors;

import com.strikalov.weatherapp.model.entities.City;
import com.strikalov.weatherapp.model.repositories.AssetsInputStreamRepository;
import com.strikalov.weatherapp.model.repositories.CityDatabaseRepository;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CityInteractorImpl implements CityInteractor {

    /**
     * Репозиторий для работы с json файлов в папке assets
     */
    private AssetsInputStreamRepository assetsInputStreamRepository;
    /**
     * Репозиторий для работой с таблицей CityEntity из базы данных
     */
    private CityDatabaseRepository cityDatabaseRepository;

    /**
     * Шедулеры для работы с многопоточностью
     */
    private Scheduler subscribeOnScheduler;
    private Scheduler observeOnScheduler;

    public CityInteractorImpl(
            AssetsInputStreamRepository assetsInputStreamRepository,
            CityDatabaseRepository cityDatabaseRepository,
            Scheduler subscribeOnScheduler,
            Scheduler observeOnScheduler)
    {
        this.assetsInputStreamRepository = assetsInputStreamRepository;
        this.cityDatabaseRepository = cityDatabaseRepository;
        this.subscribeOnScheduler = subscribeOnScheduler;
        this.observeOnScheduler = observeOnScheduler;
    }

    public CityInteractorImpl(AssetsInputStreamRepository assetsInputStreamRepository,
                              CityDatabaseRepository cityDatabaseRepository){

        this(assetsInputStreamRepository, cityDatabaseRepository, Schedulers.io(), AndroidSchedulers.mainThread());

    }

    /**
     * Метод возвращает список объектов класса City, полученных из json файла из папки assets
     * с использование репозитория assetsInputStreamRepository
     * @return
     */
    @Override
    public Single<List<City>> downloadCityListFromAssets() {
        return Single.fromCallable(new Callable<List<City>>() {
            @Override
            public List<City> call() throws Exception {
                return assetsInputStreamRepository.downloadCityList();
            }
        }).subscribeOn(subscribeOnScheduler)
                .observeOn(observeOnScheduler);
    }

    /**
     * Метод отправляет на сохранение в базу данных список бъектов класса City
     * с помощью репозитория cityDatabaseRepository
     * @param cities
     * @return
     */
    @Override
    public Completable saveCityListInDatabase(List<City> cities) {
        return cityDatabaseRepository.insertCityList(cities)
                .subscribeOn(subscribeOnScheduler)
                .observeOn(observeOnScheduler);
    }



}
