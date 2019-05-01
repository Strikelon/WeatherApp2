package com.strikalov.weatherapp.model.repositories;

import com.strikalov.weatherapp.model.entities.City;

import java.util.List;

/**
 * Репозиторий для работы с json файлом из папки assets
 */
public interface AssetsInputStreamRepository {

    /**
     * Метод возвращает список объектов класса City из json файла из папки assets
     * @return
     */
    List<City> downloadCityList();

}
