package com.strikalov.weatherapp.view;

import com.arellomobile.mvp.MvpView;

public interface SplashView extends MvpView {

    /**
     * В методе запрашиваем из соотвествующего preference файла,
     * загружена ли база данный со списком городов
     */
    void getIsCityDatabaseDownloaded();

    /**
     * Данный метод изменяет значение в соответствующем preference файле,
     * загружена ли база данный данных со списком городов или нет
     * @param value
     */
    void setIsCityDatabaseDownloaded(boolean value);

    /**
     * Метод возвращает индекс города, прогноз погоды для которого должен быть загружен,
     * или возвращает null, если город пользователем не выбран
     */
    void getCityIndex();

    /**
     * Метод проверяет есть ли связь с интернетом
     */
    void isOnline();

    /**
     * Стартуем MainActivity и передаем ему параметр isOnline, чтобы понять
     * была ли связь с интернетом и был обновлен прогноз погоды, или нет
     * @param isOnline
     */
    void startMainActivity(boolean isOnline);

    /**
     * стартуем CitySelectionActivity
     */
    void startCitySelectionActivity();

}
