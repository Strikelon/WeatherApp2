package com.strikalov.weatherapp.view;

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

import javax.inject.Inject;

public class SplashActivity extends MvpAppCompatActivity implements SplashView{

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

    @Override
    public void getIsCityDatabaseDownloaded() {
        boolean isCityDatabaseDownloaded =  CityDownloadsPreferences.isCityDatabaseDownloaded(this);
        splashPresenter.onGetIsCityDatabaseDownloaded(isCityDatabaseDownloaded);
    }

    @Override
    public void setIsCityDatabaseDownloaded(boolean value) {
        CityDownloadsPreferences.setIsCityDatabaseDownloaded(this, value);
    }

    @Override
    public void getCityIndex() {
        String cityIndex = CityDownloadsPreferences.getCityIndex(this);
        splashPresenter.onGetCityIndex(cityIndex);
    }

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
     * Удалить эти методы
     */

    @Override
    public void setCityIndex(String cityIndex) {
        CityDownloadsPreferences.setCityIndex(this, cityIndex);
    }

}
