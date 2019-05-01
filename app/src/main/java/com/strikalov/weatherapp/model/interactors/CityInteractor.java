package com.strikalov.weatherapp.model.interactors;

import com.strikalov.weatherapp.model.entities.City;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Интерактор для работы с объектами класса City
 */
public interface CityInteractor {

    /**
     * Метод возвращает список объектов класса City, полученных из json файла из папки assets
     * @return
     */
    Single<List<City>> downloadCityListFromAssets();

    /**
     * Метод отправляет на сохранение в базу данных список бъектов класса City
     * @param cities
     * @return
     */
    Completable saveCityListInDatabase(List<City> cities);

}
