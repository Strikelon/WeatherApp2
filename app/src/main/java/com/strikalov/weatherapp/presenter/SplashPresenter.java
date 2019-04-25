package com.strikalov.weatherapp.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.strikalov.weatherapp.common.Constants;
import com.strikalov.weatherapp.model.entities.City;
import com.strikalov.weatherapp.model.interactors.AssetsInputStreamInteractor;
import com.strikalov.weatherapp.model.interactors.CityDatabaseInteractor;
import com.strikalov.weatherapp.view.SplashView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;

@InjectViewState
public class SplashPresenter extends MvpPresenter<SplashView> {

    private AssetsInputStreamInteractor assetsInputStreamInteractor;
    private CityDatabaseInteractor cityDatabaseInteractor;

    @Inject
    public SplashPresenter(AssetsInputStreamInteractor assetsInputStreamInteractor,
                           CityDatabaseInteractor cityDatabaseInteractor){
        this.assetsInputStreamInteractor = assetsInputStreamInteractor;
        this.cityDatabaseInteractor = cityDatabaseInteractor;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        getViewState().getIsCityDatabaseDownloaded();
    }

    public void onGetIsCityDatabaseDownloaded(boolean isDownloaded){
        Log.i("MyLogger", Boolean.toString(isDownloaded));

        if(!isDownloaded){

            downloadCities();

        }else{

            getViewState().isOnline();

        }
    }

    public void onGetCityIndex(String cityIndex){
        Log.i("MyLogger", "CityIndex: " + cityIndex);
    }

    public void onIsOnline(boolean isOnline){

        Log.i("MyLogger", "IsOnline: " + Boolean.toString(isOnline));

    }

    private void downloadCities(){

        if(assetsInputStreamInteractor != null){

            assetsInputStreamInteractor.downloadCityList()
                    .subscribe(new DisposableSingleObserver<List<City>>() {
                        @Override
                        public void onSuccess(List<City> cities) {

                            /**
                             * Удалить это
                             */
                            for(City city: cities){
                                Log.i("MyLogger", city.toString());
                            }

                            if(cities != null){

                                insertCitiesInDatabase(cities);

                            }else {
                                Log.e(Constants.TAG_APP_ERROS, "assetsInputStreamInteractor.downloadCityList(): citylist = null");
                            }

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(Constants.TAG_APP_ERROS, "assetsInputStreamInteractor.downloadCityList(): ", e);
                        }
                    });

        }else {
            Log.e(Constants.TAG_APP_ERROS, "assetsInputStreamInteractor == null");
        }

    }

    private void insertCitiesInDatabase(List<City> cities){

        if(cityDatabaseInteractor != null){

            cityDatabaseInteractor.insertCityListInDatabase(cities)
                    .subscribe(new DisposableCompletableObserver() {
                        @Override
                        public void onComplete() {

                            getViewState().setIsCityDatabaseDownloaded(true);
                            getViewState().getIsCityDatabaseDownloaded();

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(Constants.TAG_APP_ERROS, "cityDatabaseInteractor.insertCityListInDatabase(cities): ", e);
                        }
                    });

        }else {
            Log.e(Constants.TAG_APP_ERROS, "cityDatabaseInteractor == null");
        }

    }



}
