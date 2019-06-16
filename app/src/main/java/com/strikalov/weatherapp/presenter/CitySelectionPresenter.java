package com.strikalov.weatherapp.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.strikalov.weatherapp.model.entities.City;
import com.strikalov.weatherapp.model.interactors.CityInteractor;
import com.strikalov.weatherapp.view.CitySelectionView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableMaybeObserver;

@InjectViewState
@Singleton
public class CitySelectionPresenter extends MvpPresenter<CitySelectionView> {

    /**
     * Тэг для логирования ошибок
     */
    private static final String ERROR_TAG = "ERROR_CITY_SELECTION";

    /**
     * Константа обозначает, что город не добавлен в избранное
     */
    private static final int NOT_FAVORITE = 0;
    /**
     * Константа обозначает, что город добавлен в избранное
     */
    private static final int FAVORITE = 1;

    /**
     * Интерактор для обращения к базе данных
     */
    private CityInteractor cityInteractor;
    /**
     * Список избранных городов
     */
    private List<City> favoritesCityList = new ArrayList<>();
    /**
     * Переменная хранит индекс выбранного для получения прогноза погоды города
     */
    private String cityIndex = null;

    /**
     * Флаг, по которому мы определяем, запущено ли CitySelectionActivity
     * из MainActivity, и MainActivity ожидает, результат, либо
     * CitySelectionActivity запущено из SplashActivity, тогда результат не ожидается
     */
    private boolean isParentWaitingResult = false;

    @Inject
    public CitySelectionPresenter(CityInteractor cityInteractor){
        this.cityInteractor = cityInteractor;
    }

    /**
     * При привязке активити мы запрашиваем текущий cityIndex из preference файла,
     * с помощью метода getViewState().getCityDownloadsPreferences()
     * Вызываем метод getFromIntentIsParentWaitingResult() для получения значения
     * для переменной isParentWaitingResult
     * Вызываем downloadAllCitiesFromDatabase() для загрузки из базы данных всех городов,
     * для последующей передаче в активити в AutoCompleteTextView
     * @param view
     */
    @Override
    public void attachView(CitySelectionView view) {
        super.attachView(view);
        getViewState().getCityDownloadsPreferences();
        getViewState().getFromIntentIsParentWaitingResult();
        downloadAllCitiesFromDatabase();
    }

    /**
     * Метод вызывается из активити для получения значения cityIndex
     * @param cityIndex
     */
    public void setCityIndex(String cityIndex){
        this.cityIndex = cityIndex;
    }

    /**
     * Загрузка из базы данных всех городов
     */
    private void downloadAllCitiesFromDatabase(){
        /**
         * Скрываем RecyclerView и FrameLayoutEmptyLis,
         * делаем видимым ProgressBar
         */
        getViewState().setRecyclerViewGone();
        getViewState().setFrameLayoutEmptyListGone();
        getViewState().setProgressBarVisible();

        cityInteractor.getAllCitiesFromDatabase()
                .subscribe(new DisposableMaybeObserver<List<City>>() {
                    @Override
                    public void onSuccess(List<City> cityList) {

                        /**
                         * При удачном получении из базы данных списка всех городов,
                         * вызываем инициализацию AutoCompleteTextView и
                         * запрашиваем получение из базы данных списка всех избранных городов
                         */
                        getViewState().initAutoCompleteTextView(cityList);
                        downloadFavoritesCitiesFromDatabase();

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(ERROR_TAG, "downloadAllCitiesFromDatabase()", e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * Метод получения всех избранных городов из базы данных
     */
    private void downloadFavoritesCitiesFromDatabase(){
        cityInteractor.getFavoritesCitiesFromDatabase()
                .subscribe(new DisposableMaybeObserver<List<City>>() {
                    @Override
                    public void onSuccess(List<City> cityList) {
                        /**
                         * При удачном получении всех избранных городов
                         * убираем ProgressBarGone
                         * Если cityIndex != null, находим в получившимся списке город
                         * с соответствующим индексом и и делаем его selected, чтобы он был
                         * выделен в RecyclerView
                         */
                        getViewState().setProgressBarGone();
                        if(cityIndex != null){
                            City city = getCityByIndex(cityIndex, cityList);
                            if(city != null){
                                city.setSelected(true);
                            }
                        }
                        /**
                         * Передаем полученный список в RecyclerView адаптер и
                         * просим его обновиться
                         */
                        favoritesCityList = cityList;
                        getViewState().updateRecyclerView(favoritesCityList);
                        /**
                         * Если список пуст, показываем FrameLayoutEmptyList
                         * c текстом, что список пуст
                         * Если нет, показываем RecyclerView
                         */
                        if(cityList.size() == 0){
                            getViewState().setFrameLayoutEmptyListVisible();
                        }else {
                            getViewState().setRecyclerViewVisible();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(ERROR_TAG, "downloadFavoritesCitiesFromDatabase()", e);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    /**
     * Метод добавляет новоый город в избранное
     * СityIndex получает значение индекса нового избранного города
     * обновляется значение в preference файле
     * changeCityFavorite - метод обновляет значения isFavorite в базе данных
     * @param city
     */
    public void addCityInFavorites(City city){
        cityIndex = city.getCityIndex();
        getViewState().setCityDownloadsPreferences(city.getCityIndex(), city.getCityName());
        changeCityFavorite(city.getCityIndex(), FAVORITE);
    }

    /**
     * Метод убирает город из избранного
     * Если cityIndex был равен cityIndex этого города, ему присваивается значение null
     * обновляется значение в preference файле
     * метод changeCityFavorite обновляет значения isFavorite в базе данных
     * @param position
     */
    public void deleteCityFromFavorite(int position){
        City city = favoritesCityList.get(position);
        if(cityIndex != null){
            if(cityIndex.equals(city.getCityIndex())){
                cityIndex = null;
                getViewState().setCityDownloadsPreferences(null, null);
            }
        }
        changeCityFavorite(city.getCityIndex(), NOT_FAVORITE);
    }

    /**
     * Метод обновляет значения isFavorite в базе данных
     * @param desiredCityIndex
     * @param favoriteValue
     */
    private void changeCityFavorite(String desiredCityIndex, int favoriteValue){
        /**
         * Скрываем RecyclerView и FrameLayoutEmptyLis,
         * делаем видимым ProgressBar
         */
        getViewState().setRecyclerViewGone();
        getViewState().setFrameLayoutEmptyListGone();
        getViewState().setProgressBarVisible();
        cityInteractor.updateCityFavoriteInDatabase(desiredCityIndex, favoriteValue)
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        downloadFavoritesCitiesFromDatabase();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(ERROR_TAG, "changeCityFavorite", e);
                    }
                });
    }

    /**
     * При клике на элементе в RecyclerView
     * cityIndex получает значение этого города
     * обновляется значение в preference файле
     * в списке этот город становится выделенным, а прошлый выбранный город
     * становится не выделенным
     * @param position
     */
    public void onCityItemClicked(int position){
        City city = favoritesCityList.get(position);
        if(cityIndex == null){
            cityIndex = city.getCityIndex();
            city.setSelected(true);
            getViewState().setCityDownloadsPreferences(city.getCityIndex(), city.getCityName());
            getViewState().updateRecyclerView(favoritesCityList);
        }else if(!cityIndex.equals(city.getCityIndex())){
            City oldCity = getCityByIndex(cityIndex, favoritesCityList);
            if(oldCity != null){
                oldCity.setSelected(false);
            }
            cityIndex = city.getCityIndex();
            city.setSelected(true);
            getViewState().setCityDownloadsPreferences(city.getCityIndex(), city.getCityName());
            getViewState().updateRecyclerView(favoritesCityList);
        }

    }

    /**
     * Метод ищет в списке избранных городов, город с нужным индексом,
     * если находит возвращает этот город, если нет возвращает null
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
     * При нажатии на кнопку в Activity в optionsMenu
     * или при нажатии backbutton - вызывается этот метод
     * Если cityIndex не равен null, и город для получения прогноза погоды выбран,
     * вызывается метод citySelectionComplete(), если город не выбран вызывается метод
     * cityNotSelectedShow(), чтобы сообщить об это пользователю
     */
    public void onCitySelectionDone(){
        if(cityIndex != null){
            getViewState().citySelectionComplete(isParentWaitingResult);
        }else {
            getViewState().cityNotSelectedShow();
        }
    }

    /**
     * Данный метод запускается из активити, после того, как мы получили boolean
     * значение из intent. По этому значению мы определяем, из какой активити запустилась
     * CitySelectionActivity и соответственно, нужно ли возвращать результат
     * @param isParentWaitingResult
     */
    public void onGetFromIntentIsParentWaitingResult(boolean isParentWaitingResult){
        this.isParentWaitingResult = isParentWaitingResult;
    }
}
