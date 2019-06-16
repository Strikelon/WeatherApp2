package com.strikalov.weatherapp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface SplashView extends MvpView {

    /**
     * В методе запрашиваем из соотвествующего preference файла,
     * загружена ли база данный со списком городов
     */
    @StateStrategyType(SkipStrategy.class)
    void getIsCityDatabaseDownloaded();

    /**
     * Данный метод изменяет значение в соответствующем preference файле,
     * загружена ли база данный данных со списком городов или нет
     * @param value
     */
    @StateStrategyType(SkipStrategy.class)
    void setIsCityDatabaseDownloaded(boolean value);

    /**
     * Метод возвращает индекс города, прогноз погоды для которого должен быть загружен,
     * или возвращает null, если город пользователем не выбран
     */
    @StateStrategyType(SkipStrategy.class)
    void getCityIndex();

    /**
     * Метод проверяет есть ли связь с интернетом
     */
    @StateStrategyType(SkipStrategy.class)
    void isOnline();

    /**
     * Стартуем MainActivity и передаем ему параметр isOnline, чтобы понять
     * была ли связь с интернетом и был обновлен прогноз погоды, или нет
     * @param isOnline
     */
    @StateStrategyType(SkipStrategy.class)
    void startMainActivity(boolean isOnline);

    /**
     * стартуем CitySelectionActivity
     */
    @StateStrategyType(SkipStrategy.class)
    void startCitySelectionActivity();

}
