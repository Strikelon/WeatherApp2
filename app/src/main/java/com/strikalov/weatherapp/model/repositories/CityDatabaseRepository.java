package com.strikalov.weatherapp.model.repositories;

import com.strikalov.weatherapp.model.entities.City;

import java.util.List;

import io.reactivex.Completable;

/**
 * Репозиторий для работы с таблицей CityEntity из базы данных
 */
public interface CityDatabaseRepository {

    /**
     * Метод сохраняет список объектов класса City в базе данных
     * @param cities
     * @return
     */
    Completable insertCityList(List<City> cities);

}
