package com.strikalov.weatherapp.view.activities;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.strikalov.weatherapp.App;
import com.strikalov.weatherapp.common.CityDownloadsPreferences;
import com.strikalov.weatherapp.presenter.SplashPresenter;
import com.strikalov.weatherapp.view.SplashView;
import com.strikalov.weatherapp.view.activities.CitySelectionActivity;
import com.strikalov.weatherapp.view.activities.MainActivity;

import javax.inject.Inject;

public class SplashActivity extends MvpAppCompatActivity implements SplashView {

    /**
     * Инжектим презентер с помощью dagger
     */

    @Inject
    @InjectPresenter
    public SplashPresenter splashPresenter;

    @ProvidePresenter
    public SplashPresenter provideSplashPresenter(){
        return splashPresenter;
    }

    {
        App.getInstance().getAppComponent().injectSplashActivity(this);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * В методе запрашиваем из соотвествующего preference файла,
     * загружена ли база данный со списком городов
     */
    @Override
    public void getIsCityDatabaseDownloaded() {
        boolean isCityDatabaseDownloaded =  CityDownloadsPreferences.isCityDatabaseDownloaded(this);
        splashPresenter.onGetIsCityDatabaseDownloaded(isCityDatabaseDownloaded);
    }

    /**
     * Данный метод изменяет значение в соответствующем preference файле,
     * загружена ли база данный данных со списком городов или нет
     * @param value
     */
    @Override
    public void setIsCityDatabaseDownloaded(boolean value) {
        CityDownloadsPreferences.setIsCityDatabaseDownloaded(this, value);
    }

    /**
     * Метод получает индекс города, прогноз погоды для которого должен быть загружен,
     * или возвращает null, если город пользователем не выбран и передает презентеру
     */
    @Override
    public void getCityIndex() {
        String cityIndex = CityDownloadsPreferences.getCityIndex(this);
        splashPresenter.onGetCityIndex(cityIndex);
    }

    /**
     * Метод проверяет есть ли связь с интернетом и передает презентеру
     */
    @Override
    public void isOnline() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()){
            splashPresenter.onIsOnline(true);
        }else {
            splashPresenter.onIsOnline(false);
        }

    }

    /**
     * Стартуем MainActivity и передаем ему параметр isOnline, чтобы понять
     * была ли связь с интернетом и был обновлен прогноз погоды, или нет
     * @param isOnline
     */
    @Override
    public void startMainActivity(boolean isOnline) {
        Intent intent = MainActivity.newIntent(this, isOnline);
        startActivity(intent);
        finish();
    }

    /**
     * стартуем CitySelectionActivity
     */
    @Override
    public void startCitySelectionActivity() {
        Intent intent = CitySelectionActivity.newIntent(this);
        startActivity(intent);
        finish();
    }

}
