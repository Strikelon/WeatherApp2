package com.strikalov.weatherapp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.strikalov.weatherapp.view.SettingsView;

import javax.inject.Inject;

@InjectViewState
public class SettingsPresenter extends MvpPresenter<SettingsView> {

    @Inject
    public SettingsPresenter(){}

    /**
     * При присоединении активити к презентеру, вызываем у него метод
     * получения текущих настроек и установления радио кнопок в соответствующее положение
     * @param view
     */
    @Override
    public void attachView(SettingsView view) {
        super.attachView(view);
        getViewState().initMeasureSettings();
    }

    /**
     * При нажатии на радио кнопку группы WindSpeedGroup
     * вызываем у активити метод changeWindSpeedSettings()
     * для сохранения новых настроек в файле preferences
     */
    public void onRadioButtonWindSpeedGroupClicked(){
        getViewState().changeWindSpeedSettings();
    }

    /**
     * При нажатии на радио кнопку группы TemperatureGroup
     * вызываем у активити метод changeTemperatureSettings()
     * для сохранения новых настроек в файле preferences
     */
    public void onRadioButtonTemperatureGroupClicked(){
        getViewState().changeTemperatureSettings();
    }

    /**
     * При нажатии на радио кнопку группы PressureGroupClicked()
     * вызываем у активити метод changePressureSettings()
     * для сохранения новых настроек в файле preferences
     */
    public void onRadioButtonPressureGroupClicked(){
        getViewState().changePressureSettings();
    }

    /**
     * При нажатии пользователем backbutton вызываем у активити
     * метод для ее закрытия
     */
    public void onBackPressed(){
        getViewState().finishView();
    }

    /**
     * При нажатии на плавающую кнопку done вызываем у активити
     * метод для ее закрытия
     */
    public void onFloatingActionButtonDoneClicked(){
        getViewState().finishView();
    }

}
