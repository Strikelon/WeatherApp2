package com.strikalov.weatherapp.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.strikalov.weatherapp.model.entities.City;
import com.strikalov.weatherapp.model.entities.WeatherForecast;
import com.strikalov.weatherapp.model.interactors.CityInteractor;
import com.strikalov.weatherapp.model.interactors.WeatherForecastInteractor;
import com.strikalov.weatherapp.view.SplashView;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;

@InjectViewState
@Singleton
public class SplashPresenter extends MvpPresenter<SplashView> {

    /**
     * Тэг для логирования ошибок
     */
    private static final String ERROR_TAG = "ERROR_SPLASH";

    /**
     * Интерактор для работы с объектами класса City
     */
    private CityInteractor cityInteractor;

    /**
     * Интерактор для работы с объектами класса WeatherForecast
     */
    private WeatherForecastInteractor weatherForecastInteractor;

    /**
     * Переменная для хранения значения полученного cityIndex
     */
    private String loadCityIndex;

    /**
     * Переменная для хранения значения boolen, которая обозначает есть ли
     * связь с интернет или нет
     */
    private boolean isOnlineConnected;

    /**
     * Переменная, для отписки от обновления прогноза погоды от сервера
     * при вызове метода destroy у презентера
     */
    private Disposable disposable;

    @Inject
    public SplashPresenter(CityInteractor cityInteractor,
                           WeatherForecastInteractor weatherForecastInteractor){
        this.cityInteractor = cityInteractor;
        this.weatherForecastInteractor = weatherForecastInteractor;
    }

    /**
     * При присоединении активити, запрашиваем, загружен ли список городов
     * из json файла из папки assets в базу данных
     */
    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        getViewState().getIsCityDatabaseDownloaded();
    }

    /**
     * Этот метод вызывается из активити при получении boolean значения,
     * загружен ли список городов из json файла из папки assets в базу данных
     * - Если список городов не загружен в базу данных, вызываем метод downloadCityListFromAssets()
     * для загрузки
     * - Если список городов загружен, вызываем у активити метод getCityIndex(), для получения
     * индекса города выбранного пользователем, для запроса у сервера прогноза погоды для него
     * @param isDownloaded
     */
    public void onGetIsCityDatabaseDownloaded(boolean isDownloaded){

        if(!isDownloaded){

            downloadCityListFromAssets();

        }else{

            getViewState().getCityIndex();

        }
    }

    /**
     * Этот метод вызывается из активти, для передачи в презентер значения cityIndex
     * - Если cityIndex равен null, значит пользователь не выбрал город и вызываем у активити
     * метод startCitySelectionActivity(), чтобы при работе с этой активити пользователь выбрал город
     * - Если cityIndex не равен null, вызываем метод у активити isOnline(), чтобы проверить есть ли
     * связь с интернетом
     * @param cityIndex
     */
    public void onGetCityIndex(String cityIndex){
        if(cityIndex != null){

            loadCityIndex = cityIndex;
            getViewState().isOnline();

        }else {

            getViewState().startCitySelectionActivity();

        }
    }

    /**
     * Данный метод вызывается из активти для передачи в презентер значания boolean,
     * которое сообщает есть связь с интернетом или нет
     * - Если есть, вызываем метод loadWeatherForecast(), чтобы запросить у сервера прогноз погоды
     * - Если нет, вызываем метод startMainActivity(isOnline) и передаем параметр, который говорит
     * о том, что связт с интернетом не было
     * @param isOnline
     */
    public void onIsOnline(boolean isOnline){

        isOnlineConnected = isOnline;

        if(isOnline) {

            loadWeatherForecast();

        }else {

            getViewState().startMainActivity(isOnline);

        }

    }

    /**
     * Метод загрузки списка городов из json файла из папки Assets.
     * В случаем успешной загрузки, вызывается метод saveCityListInDatabase(cities)
     * для сохранения полученного списка городов в базе данных
     */
    private void downloadCityListFromAssets() {

        cityInteractor.downloadCityListFromAssets()
                .subscribe(new DisposableSingleObserver<List<City>>() {
                    @Override
                    public void onSuccess(List<City> cities) {

                        if (cities != null) {

                            saveCityListInDatabase(cities);

                        } else {
                            Log.e(ERROR_TAG, "cityInteractor.downloadCityListFromAssets(): citylist = null");
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(ERROR_TAG, "cityInteractor.downloadCityListFromAssets(): ", e);
                    }
                });


    }

    /**
     * Метод для сохранения списока городов в базе данных.
     * В слуае успешного сохранения вызываем у активти метод setIsCityDatabaseDownloaded(true),
     * чтобы в соответствующий preference файл записать, что список городов из json файла сохранен
     * в базе данных, после этого вызываем метод getIsCityDatabaseDownloaded()
     * @param cities
     */
    private void saveCityListInDatabase(List<City> cities) {

        cityInteractor.saveCityListInDatabase(cities)
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {

                        getViewState().setIsCityDatabaseDownloaded(true);
                        getViewState().getIsCityDatabaseDownloaded();

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(ERROR_TAG, "cityInteractor.saveCityListInDatabase(cities): ", e);
                    }
                });

    }

    /**
     * В данном методе запрашивается прогноз погоды у сервера, для выбранного пользователем города
     * В случае успешного получения прогноза погоды, мы вызываем метод
     * saveWeatherForecastInDatabase(weatherForecast) для сохранения полученного прогноза
     * погоды в базе данных
     */
    private void loadWeatherForecast(){

        weatherForecastInteractor.loadWeatherForecast(loadCityIndex)
                .subscribe(new Observer<WeatherForecast>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(WeatherForecast weatherForecast) {

                        saveWeatherForecastInDatabase(weatherForecast);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(ERROR_TAG, "weatherForecastInteractor: ", e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    /**
     * Метод сохранения прогноза погоды в базе данных.
     * В случае успешного сохранения, вызываем метод startMainActivity(isOnlineConnected) у активти
     * для запуска MainActivity и передачи параметра, что связь с интернетом была и прогноз обновился
     * @param weatherForecast
     */
    private void saveWeatherForecastInDatabase(WeatherForecast weatherForecast){

        weatherForecastInteractor.insertWeatherForecastInDatabase(loadCityIndex, weatherForecast)
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {

                        getViewState().startMainActivity(isOnlineConnected);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(ERROR_TAG, "weatherForecastInteractor: ", e);
                    }
                });


    }

    /**
     * При уничтожении презентаре отписываемся от обновлений сервера прогноза погоды
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }

}
