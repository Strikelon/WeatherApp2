package com.strikalov.weatherapp.model.interactors;

import com.strikalov.weatherapp.model.entities.WeatherForecast;
import com.strikalov.weatherapp.model.repositories.NetworkRepository;
import com.strikalov.weatherapp.model.repositories.WeatherForecastDatabaseRepository;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WeatherForecastInteractorImpl implements WeatherForecastInteractor {

    /**
     * Репозиторий для получения прогноза погоды с сервера
     */
    private NetworkRepository networkRepository;
    /**
     * Репозиторий для работы с таблицей базы данных, где хранятся прогнозы погоды
     */
    private WeatherForecastDatabaseRepository weatherForecastDatabaseRepository;

    /**
     * Shedulers для работы с многопоточностью
     */
    private Scheduler subscribeOnScheduler;
    private Scheduler observeOnScheduler;

    public WeatherForecastInteractorImpl(
            NetworkRepository networkRepository,
            WeatherForecastDatabaseRepository weatherForecastDatabaseRepository,
            Scheduler subscribeOnScheduler,
            Scheduler observeOnScheduler){

        this.networkRepository = networkRepository;
        this.weatherForecastDatabaseRepository = weatherForecastDatabaseRepository;
        this.subscribeOnScheduler = subscribeOnScheduler;
        this.observeOnScheduler = observeOnScheduler;
    }

    public WeatherForecastInteractorImpl(NetworkRepository networkRepository,
                                         WeatherForecastDatabaseRepository weatherForecastDatabaseRepository){

        this(networkRepository, weatherForecastDatabaseRepository ,Schedulers.io(), AndroidSchedulers.mainThread());

    }

    /**
     * Метод возвращает прогноз погоды от сервера погоды, для города имеющего
     * индекс равный cityId, использует networkRepository
     * @param cityId
     * @return
     */
    @Override
    public Observable<WeatherForecast> loadWeatherForecast(String cityId) {
        return networkRepository.getWeatherForecast(cityId)
                .subscribeOn(subscribeOnScheduler)
                .observeOn(observeOnScheduler);
    }

    /**
     * Метод сохраняет индекс города и прогноз погоды для этого города
     * в базе данных с помощью weatherForecastDatabaseRepository
     * @param cityIndex
     * @param weatherForecast
     * @return
     */
    @Override
    public Completable insertWeatherForecastInDatabase(String cityIndex, WeatherForecast weatherForecast) {
        return weatherForecastDatabaseRepository.insertWeatherForecast(cityIndex, weatherForecast)
                .subscribeOn(subscribeOnScheduler)
                .observeOn(observeOnScheduler);
    }

    /**
     * Метод возвращает из базы данных прогноз погоды для города с искомым cityIndex
     * с помощью weatherForecastDatabaseRepository
     * @param cityIndex
     * @return
     */
    @Override
    public Maybe<WeatherForecast> loadWeatherForecastFromDatabase(String cityIndex) {
        return weatherForecastDatabaseRepository.getWeatherForecastByCityIndex(cityIndex)
                .subscribeOn(subscribeOnScheduler)
                .observeOn(observeOnScheduler);
    }
}
