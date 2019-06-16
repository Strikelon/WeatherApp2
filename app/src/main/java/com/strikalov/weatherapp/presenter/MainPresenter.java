package com.strikalov.weatherapp.presenter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.strikalov.weatherapp.model.entities.City;
import com.strikalov.weatherapp.model.entities.WeatherForecast;
import com.strikalov.weatherapp.model.interactors.CityInteractor;
import com.strikalov.weatherapp.model.interactors.WeatherForecastInteractor;
import com.strikalov.weatherapp.view.MainView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.observers.DisposableObserver;

@InjectViewState
@Singleton
public class MainPresenter extends MvpPresenter<MainView> {

    /**
     * Тэг для логирования ошибок
     */
    private static final String ERROR_TAG = "ERROR_MAIN_PRESENTER";

    /**
     * Реквест код означает, что результат пришел от SettingsActivity
     */
    private static final int REQUEST_CODE_SETTINGS = 1;

    /**
     * Реквест код означает, что результат пришел от CitySelectionActivity
     */
    private static final int REQUEST_CODE_CITY_SELECTION = 2;

    /**
     * Результат возвращается в случае если нужное действие в активити
     * отменено пользователем (Пользователь не выбрал город в CitySelection и вышел)
     */
    private static final int RESULT_CANCELED = 0;

    /** Результат возвращается, если нужное действие в активити выполнено успешное
     */
    private static final int RESULT_OK = -1;

    /**
     * Константа обозначает, что MainActivity запущено первый раз из SplashActivity
     * или CitySelectionActivity
     */
    private static final int FIRST_START = 0;

    /**
     * Константа обозначает, что MainActivity запущено, после завершения работы
     * SettingsActivity и возращения ею результата
     */
    private static final int START_FROM_SETTINGS_RESULT_OK = 1;

    /**
     * Константа обозначает, что MainActivity запущено, после завершения работы
     * CitySelectionActivity и возращения ею результата
     */
    private static final int START_FROM_CITY_SELECTION_RESULT_OK = 2;

    /**
     * Интерактор для работы с объектами класса City, получения их из базы
     * данных или сохранения в базе данных
     */
    private CityInteractor cityInteractor;

    /**
     * Интерактор для работы с объектами класса WeatherForecast, получения их из базы
     * данных или сохранения в базе данных, получения от сервера через интернет
     */
    private WeatherForecastInteractor weatherForecastInteractor;

    /**
     * Список содержит избранные пользователем города
     */
    private List<City> favoritesCityList = new ArrayList<>();

    /**
     * Значение индекса (по которому можно запросить прогноз погоды у сервера)
     * для текущего выбранного города
     */
    private String cityIndex;

    /**
     * Название текущего выбранного города
     */
    private String cityName;

    /**
     * Значение текущей настройки для единицы измерения скорости ветра
     */
    private int windMeasure;

    /**
     * Значение текущей настройки для единицы измерения температуры
     */
    private int temperatureMeasure;

    /**
     * Значение текущей настройки для единицы измерения атмосферного давления
     */
    private int pressureMeasure;

    /**
     * Значение состояние старта активити устанавливаем
     * FIRST_START (запущено первый раз из SplashActivity или CitySelectionActivity)
     */
    private int startActivityState = FIRST_START;

    @Inject
    public MainPresenter(CityInteractor cityInteractor, WeatherForecastInteractor weatherForecastInteractor){
        this.cityInteractor = cityInteractor;
        this.weatherForecastInteractor = weatherForecastInteractor;
    }

    /**
     * Метод срабатывает при присоединении активити к презентеру
     * @param view
     */
    @Override
    public void attachView(MainView view) {
        super.attachView(view);
        /**
         * Запрашиваем у активти:
         * - индекса выбранного города
         * - название города
         * - настройки системы измерения для скорости ветра, температуры,
         * атмосферного давления
         */
        getViewState().getCityIndex();
        getViewState().getCityName();
        getViewState().getWindMeasure();
        getViewState().getTemperatureMeasure();
        getViewState().getPressureMeasure();
        /**
         * Вызываем метод для закгрузки из базы данных избранных городов
         * и помещения их в recyclerView в NavigationDrawer
         */
        downloadFavoritesCitiesFromDatabase();
        /**
         * Проверяем текущее состояние старта активти
         */
        switch (startActivityState) {
            /**
             * Если MainActivity запущена первый раз из SplashActivity или CitySelectionActivity,
             * запрашиваем из интента значение boolean переменной wasOnline, чтобы понять,
             * был ли получен у сервера прогноз актуальный прогноз погоды и сохранен ли он в базе данных
             */
            case FIRST_START:
                getViewState().getFromIntentWasOnline();
                break;
            /**
             * Если MainActivity был запущена после выхода из SettingsActivity c передачей
             * из нее результата
             * показываем SnackBaк о том, что настройки успешно сохранены и
             * загружаем из базы данных прогноз, после чего его показываем с примененными
             * новыми настройками системы измерения
             */
            case START_FROM_SETTINGS_RESULT_OK:
                getViewState().showSnackBarSettingsSavedSuccessfully();
                downloadWeatherForecast();
                break;
            /**
             * Если MainActivity был запущена после выхода из CitySelectionActivity c передачей
             * из нее результата
             * Проверяем, есть ли соединение с интернетом, чтобы загрузить
             * с сервера прогноз погоды для нового выбранного города
             */
            case START_FROM_CITY_SELECTION_RESULT_OK:
                getViewState().isOnline();
                break;
        }
    }

    /**
     * Коллбэк метод вызывается из MainActivity, после получения в ней из preference файла
     * значения cityIndex
     * @param cityIndex
     */
    public void onGetCityIndex(String cityIndex){
        if(cityIndex != null){
            this.cityIndex = cityIndex;
        }else {
            Log.e(ERROR_TAG, "onGetCityIndex(String cityIndex) : cityIndex = null");
        }
    }

    /**
     * Коллбэк метод вызывается из MainActivity, после получения в ней из preference файла
     * значения cityName
     * @param cityName
     */
    public void onGetCityName(String cityName){
        if(cityName != null){
            this.cityName = cityName;
            getViewState().setToolbarTitle(cityName);
        }else {
            Log.e(ERROR_TAG, "onGetCityName(String cityName) : cityName = null");
        }
    }

    /**
     * Коллбэк метод вызывается из MainActivity, после получения в ней из preference файла
     * значения windMeasure
     * @param windMeasure
     */
    public void onGetWindMeasure(int windMeasure){
        this.windMeasure = windMeasure;
    }

    /**
     * Коллбэк метод вызывается из MainActivity, после получения в ней из preference файла
     * значения temperatureMeasure
     * @param temperatureMeasure
     */
    public void onGetTemperatureMeasure(int temperatureMeasure){
        this.temperatureMeasure = temperatureMeasure;
    }

    /**
     * Коллбэк метод вызывается из MainActivity, после получения в ней из preference файла
     * значения pressureMeasure
     * @param pressureMeasure
     */
    public void onGetPressureMeasure(int pressureMeasure){
        this.pressureMeasure = pressureMeasure;
    }

    /**
     * Коллбэк метод вызывается из MainActivity, после получения в ней из интента значения wasOnline
     * Если wasOnline равен true, значит актуальный прогноз погоды уже был загружен с сервера и сохранен
     * в базе данных в активти, которая вызвала MainActivity. Значит, чтобы получить прогноз погоды,
     * нужно его получить из базы данных.
     * Если wasOnline равен false, значит актуальный прогноз погоды не был загружен
     * с сервера и его нужно загрузить, вызываем метод getViewState().isOnline(), чтобы
     * проверить, что есть интернет, чтобы загрузить прогноз погоды с сервера
     * @param wasOnline
     */
    public void onGetFromIntentWasOnline(boolean wasOnline){
        if(!wasOnline){
            getViewState().isOnline();
        }else {
            downloadWeatherForecast();
        }
    }

    /**
     * Данный метод загружает из базы данных список избранных пользователем городов
     */
    private void downloadFavoritesCitiesFromDatabase(){
        cityInteractor.getFavoritesCitiesFromDatabase()
                .subscribe(new DisposableMaybeObserver<List<City>>() {
                    @Override
                    public void onSuccess(List<City> cityList) {
                        /**
                         * В случае успеха, мы передаем полученный список в адаптер
                         * recyclerView, с выделенным текущим городоа
                         */
                        favoritesCityList = cityList;
                        if(cityIndex != null){
                            City city = getCityByIndex(cityIndex, favoritesCityList);
                            if(city != null){
                                city.setSelected(true);
                            }
                        }
                        getViewState().updateNavigationRecyclerView(favoritesCityList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(ERROR_TAG, "downloadFavoritesCitiesFromDatabase()", e);
                    }

                    @Override
                    public void onComplete() {
                        /**
                         * В случае если избранных городов нет, мы передаем пустой список
                         * в адаптер recyclerView
                         */
                        favoritesCityList = new ArrayList<>();
                        getViewState().updateNavigationRecyclerView(favoritesCityList);
                    }
                });
    }

    /**
     * Метод загружает из базы данных сохраненный прогноз погоды
     */
    private void downloadWeatherForecast(){
        if(cityIndex != null) {
            weatherForecastInteractor.loadWeatherForecastFromDatabase(cityIndex)
                    .subscribe(new DisposableMaybeObserver<WeatherForecast>() {
                        @Override
                        public void onSuccess(WeatherForecast weatherForecast) {
                            /**
                             * В случае успеха, просим активти показать данный прогноз погоды
                             * с учетом настроек отображения систем измерений
                             */
                            getViewState().showWeatherForecast(weatherForecast, windMeasure, temperatureMeasure, pressureMeasure);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("MyLogger", "downloadWeatherForecast()", e);
                        }

                        @Override
                        public void onComplete() {
                            /**
                             * В случае неудачи, мы проверяем, есть ли связь с интернетом,
                             * чтобы азгрузить прогноз погоды с сервера
                             */
                            getViewState().isOnline();
                        }
                    });
        }else {
            Log.e(ERROR_TAG, "downloadWeatherForecast() : cityIndex = null");
        }
    }

    /**
     * Методы вызывается из активти, когда пользователь свайпит вниз
     * чтобы обновить прогноз погоды.
     * Мы вызываем метод isOnline() для проверки , есть ли связь с интернетом,
     * перед попыткой загрузки прогноза погоды с сервера
     */
    public void onSwipeRefreshLayout(){

        getViewState().isOnline();

    }

    /**
     * Метод срабатывает, после клика в recyclerView из navigationDrawer,
     * на одном из избранных городов
     * Новый выбранный город становится веделенным, обновляется названией города в toolbar,
     * сохраняется новый cityIndex и cityName, вызывается метод isOnline, перед
     * загрузкой с сервера прогноза погоды для нового города
     * @param position
     */
    public void onCitySelected(int position){
        City city = favoritesCityList.get(position);
        if(!cityIndex.equals(city.getCityIndex())) {
            getViewState().swipeRefreshShow();
            City oldCity = getCityByIndex(cityIndex, favoritesCityList);
            if(oldCity != null){
                oldCity.setSelected(false);
            }
            city.setSelected(true);
            getViewState().setCityDownloadsPreferences(city.getCityIndex(), city.getCityName());
            cityIndex = city.getCityIndex();
            cityName = city.getCityName();
            getViewState().setToolbarTitle(cityName);
            getViewState().updateNavigationRecyclerView(favoritesCityList);
            getViewState().resetWeatherForecastData();
            getViewState().isOnline();
        }
    }

    /**
     * Коллбэк метод, вызывается из MainActivity, после проверки соединения с интернетом
     * Если isOnline = true, вызываем метод loadWeatherForecastFromServer()
     * для загрузки с сервера прогноза погоды
     * Если isOnline = false, показываем с snackbar с информацией о том, что нет соединения с интернетом,
     * чтобы пользователь проверил соединение и обновил страницу
     * @param isOnline
     */
    public void onIsOnline(boolean isOnline){
        if(isOnline) {

            if(cityIndex != null) {
                loadWeatherForecastFromServer();
            }else {
                Log.e(ERROR_TAG, "onIsOnline : cityIndex = null");
            }

        }else {

            getViewState().swipeRefreshHide();
            getViewState().showSnackBarNoDataToDisplay();

        }
    }

    /**
     * Метод осуществляет загрузку с сервера прогноза погода
     */
    private void loadWeatherForecastFromServer() {

        weatherForecastInteractor.loadWeatherForecast(cityIndex)
                .subscribe(new DisposableObserver<WeatherForecast>() {
                    @Override
                    public void onNext(WeatherForecast weatherForecast) {
                        /**
                         * В случае успеха просим активити показать прогнозы погоды
                         * и запускаем метод для сохранения актуального прогноза погоды в базе данных
                         */
                        getViewState().swipeRefreshHide();
                        getViewState().showWeatherForecast(weatherForecast,
                                windMeasure, temperatureMeasure, pressureMeasure);
                        saveWeatherForecastInDatabase(weatherForecast);
                    }

                    @Override
                    public void onError(Throwable e) {
                        /**
                         * В случае неудачи показываем с snackbar с информацией о том, что нет соединения с интернетом,
                         * чтобы пользователь проверил соединение и обновил страницу
                         */
                        getViewState().swipeRefreshHide();
                        getViewState().showSnackBarNoDataToDisplay();
                        Log.e(ERROR_TAG, "loadWeatherForecast()", e);
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }

    /**
     * Метод сохраняет прогноз погоды в базе данных
     * @param weatherForecast
     */
    private void saveWeatherForecastInDatabase(WeatherForecast weatherForecast){

        weatherForecastInteractor.insertWeatherForecastInDatabase(cityIndex, weatherForecast)
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(ERROR_TAG, "weatherForecastInteractor: ", e);
                    }
                });
    }

    /**
     * Метод находит в списке город с нужным индексом и возвращает его
     * либо возвращает null
     * @param desireIndex
     * @param cityList
     * @return
     */
    private City getCityByIndex(String desireIndex, List<City> cityList){
        for(City city: cityList){
            if(city.getCityIndex().equals(desireIndex)){
                return city;
            }
        }
        return null;
    }

    /**
     * Коллбэк метод вызывается из MainActivity, когда пользователь
     * нажимает на кнопку, для отправки сообщения об ошибке команде разработчиков
     * Вызываем метод causeMailApp, метод предоставляет
     * пользователю на выбор почтовые приложения. После выбора пользователем приложения,
     * в графе кому, уже будет вставлен контактный email, команды разработки
     */
    public void onClickSendFeedback(){
        getViewState().causeMailApp();
    }

    /**
     * Коллбэк метод вызывается из MainActivity, когда пользователь
     * нажимает на кнопку, для вызова экрана настроек
     * Вызываем метод старта SettingsActivity и передаем REQUEST_CODE_SETTINGS
     * чтобы SettingsActivity вернула результат по этому реквест коду
     */
    public void onClickSettings(){
        getViewState().startSettingsActivityForResult(REQUEST_CODE_SETTINGS);
    }

    /**
     * Коллбэк метод вызывается из MainActivity, когда пользователь
     * нажимает на кнопку, для вызова экрана выбора города
     * Вызываем метод старта CitySelectionActivity и передаем REQUEST_CODE_CITY_SELECTION
     * чтобы CitySelectionActivity вернула результат по этому реквест коду
     */
    public void onClickCitySelection(){
        getViewState().startCitySelectionActivityForResult(REQUEST_CODE_CITY_SELECTION);
    }

    /**
     * Коллбэк метод, вызывается из MainActivity, когда одна из активити завершает
     * свою работы и передает в MainActivity результат
     * В заивисимости от реквест кода и результата, присваиваем переменной
     * startActivityState, т.к. после метода onActivityResult(), вызовется метод
     * attachView(), в котором в зависимости от startActivityState будет работать разная логика
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        if(requestCode == REQUEST_CODE_SETTINGS){
            if(resultCode == RESULT_OK){
                startActivityState = START_FROM_SETTINGS_RESULT_OK;
            }
        }else if (requestCode == REQUEST_CODE_CITY_SELECTION){
            if(resultCode == RESULT_CANCELED){
                getViewState().finishActivity();
            }
            else if(resultCode == RESULT_OK) {
                startActivityState = START_FROM_CITY_SELECTION_RESULT_OK;
            }
        }
    }
}
