package com.strikalov.weatherapp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.strikalov.weatherapp.App;
import com.strikalov.weatherapp.R;
import com.strikalov.weatherapp.adapters.CityAutoCompleteAdapter;
import com.strikalov.weatherapp.adapters.CitySelectionRecyclerViewAdapter.CitySelectionRecyclerViewAdapter;
import com.strikalov.weatherapp.adapters.CitySelectionRecyclerViewAdapter.SimpleCitySelectionTouchHelperCallback;
import com.strikalov.weatherapp.common.CityDownloadsPreferences;
import com.strikalov.weatherapp.model.entities.City;
import com.strikalov.weatherapp.presenter.CitySelectionPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CitySelectionActivity extends MvpAppCompatActivity implements CitySelectionView  {

    /**
     * Инжектим CitySelectionPresenter с помощью dagger
     */
    @Inject
    @InjectPresenter
    public CitySelectionPresenter citySelectionPresenter;

    @ProvidePresenter
    public CitySelectionPresenter provideCitySelectionPresenter(){
        return citySelectionPresenter;
    }

    {
        App.getInstance().getAppComponent().injectCitySelectionActivity(this);
    }

    /**
     *  С помощью виджета AutoCompleteTextView пользователи будут выбирать город
     */
    private AutoCompleteTextView autoCompleteTextView;
    /**
     * RecyclerView отображает список избранных городов
     */
    private RecyclerView recyclerView;
    /**
     * Адаптер для RecyclerView
     */
    private CitySelectionRecyclerViewAdapter citySelectionRecyclerViewAdapter;
    /**
     * Прогресс бар
     */
    private ProgressBar progressBar;
    /**
     * Макет с отображением текста, о том что список избранных городов пуст
     */
    private FrameLayout frameLayoutEmptyList;
    /**
     * Ссылка на самый главный родительский макет, для отображения snackbar
     */
    private CoordinatorLayout coordinatorLayout;


    /**
     * Метод возвращает интент, чтобы стартовать эту активити из другой активити
     * @param context
     * @return
     */
    public static Intent newIntent(Context context){
        return new Intent(context, CitySelectionActivity.class);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_selection);

        Toolbar toolbar = findViewById(R.id.toolbar_city_selection);
        setSupportActionBar(toolbar);

        initWidgets();
        initRecyclerView();

    }

    /**
     * Инициализация виджетов
     */
    private void initWidgets(){
        coordinatorLayout = findViewById(R.id.coordinator_layout);
        progressBar = findViewById(R.id.progress_bar);
        frameLayoutEmptyList = findViewById(R.id.frame_layout_empty_list);
    }

    /**
     * Инициализация RecyclerView, адаптера и слушателей
     */
    private void initRecyclerView(){
        recyclerView = findViewById(R.id.city_selection_recycler_view);
        citySelectionRecyclerViewAdapter =
                new CitySelectionRecyclerViewAdapter(new ArrayList<City>());
        ItemTouchHelper.Callback callback
                = new SimpleCitySelectionTouchHelperCallback(citySelectionRecyclerViewAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(citySelectionRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        citySelectionRecyclerViewAdapter
                .setOnItemClickListener(new CitySelectionRecyclerViewAdapter.OnItemListener() {
            @Override
            public void onItemClicked(int position) {
                /**
                 * Метод вызывается при клики на элемент RecyclerView
                 */
                onCityItemClicked(position);
            }

            @Override
            public void onSwipeItem(int position) {
                /**
                 * Метод вызывается при свайпе влево или вправо элемента
                 * в RecyclerView
                 */
                onSwipeCityItem(position);
            }
        });
    }

    /**
     * Метод передает в адаптер RecyclerView новый список избранных городов
     * и обновляет его
     * @param favoritesCityList
     */
    @Override
    public void updateRecyclerView(List<City> favoritesCityList) {
        if(citySelectionRecyclerViewAdapter != null){
            citySelectionRecyclerViewAdapter.setCityList(favoritesCityList);
            citySelectionRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    /**
     * При клике на элементе RecyclerView
     * вызываем метод презентера и передаем в него номер позиции элемента
     * @param position
     */
    private void onCityItemClicked(int position){
        citySelectionPresenter.onCityItemClicked(position);
    }

    /**
     * При свайпе элемента RecyclerView влево или вправо
     * вызываем метод презентера и передаем в него номер позиции элемента
     * @param position
     */
    private void onSwipeCityItem(int position){
        citySelectionPresenter.deleteCityFromFavorite(position);
    }

    /**
     * Инициализация виджета AutoCompleteTextView
     * @param cityList
     */
    @Override
    public void initAutoCompleteTextView(List<City> cityList) {

        autoCompleteTextView = findViewById(R.id.auto_complete_text_search_city);
        final CityAutoCompleteAdapter cityAutoCompleteAdapter = new CityAutoCompleteAdapter(CitySelectionActivity.this, cityList);
        autoCompleteTextView.setAdapter(cityAutoCompleteAdapter);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                City city = cityAutoCompleteAdapter.getItem(position);
                autoCompleteTextView.setText("");
                citySelectionPresenter.addCityInFavorites(city);
            }
        });
    }

    /**
     * Метод делает ProgressBar видимым
     */
    @Override
    public void setProgressBarVisible() {
        if(progressBar != null) {
            progressBar.setVisibility(ProgressBar.VISIBLE);
        }
    }

    /**
     * Метод делает ProgressBar невидимым
     */
    @Override
    public void setProgressBarGone() {
        if(progressBar != null) {
            progressBar.setVisibility(ProgressBar.GONE);
        }
    }

    /**
     * Метод делает видимым FrameLayout с тектом, что список избранных городов пуст
     */
    @Override
    public void setFrameLayoutEmptyListVisible() {
        if(frameLayoutEmptyList != null) {
            frameLayoutEmptyList.setVisibility(View.VISIBLE);
        }
    }
    /**
     * Метод делает невидимым FrameLayout с тектом, что список избранных городов пуст
     */
    @Override
    public void setFrameLayoutEmptyListGone() {
        if(frameLayoutEmptyList != null) {
            frameLayoutEmptyList.setVisibility(View.GONE);
        }
    }
    /**
     * Метод делает RecyclerView с видимыми городами видимым
     */
    @Override
    public void setRecyclerViewVisible() {
        if(recyclerView != null){
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Метод делает RecyclerView с видимыми городами невидимым
     */
    @Override
    public void setRecyclerViewGone() {
        if(recyclerView != null){
            recyclerView.setVisibility(View.GONE);
        }
    }

    /**
     * Метод сохраняет в Preferences файл индекс и имя выбранного
     * пользователем города для прогноза погоды
     * @param cityIndex
     * @param cityName
     */
    @Override
    public void setCityDownloadsPreferences(String cityIndex, String cityName) {
        CityDownloadsPreferences.setCityIndex(this, cityIndex);
        CityDownloadsPreferences.setCityName(this, cityName);
    }

    /**
     * Метод возвращает из Preferences файла индекс выбранного
     * пользователем города для прогноза погоды и передает в презентер
     */
    @Override
    public void getCityDownloadsPreferences() {
        String cityIndex = CityDownloadsPreferences.getCityIndex(this);
        citySelectionPresenter.setCityIndex(cityIndex);
    }

    /**
     * Метод создает options меню с кнопкой завершения выбора города
     * city_selection_done
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.city_selection_option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * При нажатии на кнопку city_selection_done
     * вызывается метод презентера onCitySelectionDone(), для проверки,
     * выбран город или нет
     * @param menuItem
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){

        int id = menuItem.getItemId();

        switch (id){
            case R.id.city_selection_done:
                citySelectionPresenter.onCitySelectionDone();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }

    }

    /**
     * При нажатии back button на телефоне,
     * вызывается метод onCitySelectionDone(), для проверки,
     * выбран город или нет
     */
    @Override
    public void onBackPressed() {
        citySelectionPresenter.onCitySelectionDone();
    }

    /**
     * Метод завершает выбор города
     */
    @Override
    public void citySelectionComplete() {
        /**
         * Нужно доработать
         */
        finish();
    }

    /**
     * Метод выдает сообщение через snackbar, о том,
     * что город не выбран и спрашивает о том, хочет ли пользователь
     * выйти из приложения
     */
    @Override
    public void cityNotSelectedShow() {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, R.string.snack_bar_message_city_not_selected, Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.snack_bar_action_exit, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        snackbar.show();
    }
}
