package com.strikalov.weatherapp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface SettingsView extends MvpView {

    /**
     * В методе запрашиваются текущие настройки единиц измерения погоды
     * из соответствующего файла preferences и радиокнопки устанавливают в
     * соответствующии значения
     */
    @StateStrategyType(SkipStrategy.class)
    void initMeasureSettings();

    /**
     * В файл настроек preferences сохраняются новые настройки для
     * единиц измерения скорости ветра
     */
    @StateStrategyType(SkipStrategy.class)
    void changeWindSpeedSettings();

    /**
     * В файл настроек preferences сохраняются новые настройки для
     * единиц измерения температуры
     */
    @StateStrategyType(SkipStrategy.class)
    void changeTemperatureSettings();

    /**
     * В файл настроек preferences сохраняются новые настройки для
     * единиц измерения давления
     */
    @StateStrategyType(SkipStrategy.class)
    void changePressureSettings();

    /**
     * Метод закрывает активити
     */
    @StateStrategyType(SkipStrategy.class)
    void finishView();

}
