package com.strikalov.weatherapp.model.repositories;

import com.strikalov.weatherapp.model.entities.City;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;

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

    /**
     * Метод запрашивает из базы данных все города
     * @return
     */
    Maybe<List<City>> getAllCities();

    /**
     * Метод запрашивает из базы данных все избранные города
     * @return
     */
    Maybe<List<City>> getFavoritesCities();

    /**
     * Метод меняет в базе данных у города с заданным индексом
     * значение поля избранное/не избранное
     * @param desiredCityIndex
     * @param favoriteValue
     * @return
     */
    Completable updateIsFavoritesByCityIndex(String desiredCityIndex, int favoriteValue);

}
