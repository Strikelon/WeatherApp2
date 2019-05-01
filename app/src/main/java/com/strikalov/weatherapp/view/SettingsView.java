package com.strikalov.weatherapp.view;

import com.arellomobile.mvp.MvpView;

public interface SettingsView extends MvpView {

    /**
     * В методе запрашиваются текущие настройки единиц измерения погоды
     * из соответствующего файла preferences и радиокнопки устанавливают в
     * соответствующии значения
     */
    void initMeasureSettings();

    /**
     * В файл настроек preferences сохраняются новые настройки для
     * единиц измерения скорости ветра
     */
    void changeWindSpeedSettings();

    /**
     * В файл настроек preferences сохраняются новые настройки для
     * единиц измерения температуры
     */
    void changeTemperatureSettings();

    /**
     * В файл настроек preferences сохраняются новые настройки для
     * единиц измерения давления
     */
    void changePressureSettings();

    /**
     * Метод закрывает активити
     */
    void finishView();

}
