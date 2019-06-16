package com.strikalov.weatherapp.view.activities;

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
import com.strikalov.weatherapp.view.CitySelectionView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CitySelectionActivity extends MvpAppCompatActivity implements CitySelectionView {

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

    public static final String CITY_SELECTION_ACTIVITY_EXTRA = "EXTRA_CITY_SELECTION_ACTIVITY";

    /**
     * Метод возвращает интент, чтобы стартовать эту активити из другой активити
     * @param context
     * @return
     */
    public static Intent newIntent(Context context){
        return new Intent(context, CitySelectionActivity.class);
    }

    /**
     * Метод возвращает интенет с вложенным булевским параметров, для запуска CitySelectionActivity
     * из другой активити
     * @param context
     * @param isParentWaitingResult
     * @return
     */
    public static Intent newIntent(Context context, boolean isParentWaitingResult){
        Intent intent = new Intent(context, CitySelectionActivity.class);
        intent.putExtra(CITY_SELECTION_ACTIVITY_EXTRA, isParentWaitingResult);
        return intent;
    }

    /**
     *  С помощью виджета AutoCompleteTextView пользователи будут выбирать город
     */
    @BindView(R.id.auto_complete_text_search_city)
    AutoCompleteTextView autoCompleteTextView;
    /**
     * RecyclerView отображает список избранных городов
     */
    @BindView(R.id.city_selection_recycler_view)
    RecyclerView recyclerView;
    /**
     * Адаптер для RecyclerView
     */
    private CitySelectionRecyclerViewAdapter citySelectionRecyclerViewAdapter;
    /**
     * Прогресс бар
     */
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    /**
     * Макет с отображением текста, о том что список избранных городов пуст
     */
    @BindView(R.id.frame_layout_empty_list)
    FrameLayout frameLayoutEmptyList;
    /**
     * Ссылка на самый главный родительский макет, для отображения snackbar
     */
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_selection);

        Toolbar toolbar = findViewById(R.id.toolbar_city_selection);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        initRecyclerView();

    }

    /**
     * Инициализация RecyclerView, адаптера и слушателей
     */
    private void initRecyclerView(){
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
     * Данный метод запускается в случае успешного выбора города пользователем.
     * Если CitySelectionActivity вызвана из SplashActivity, то мы стартуем MainActivity и закрываем
     * текущую
     * Если CitySelectionActivity вызвана из MainActivity, то мы возвращаем результат в MainActivity
     * RESULT_OK и завершаем текущую активити
     */
    @Override
    public void citySelectionComplete(boolean isParentWaitingResult) {
        if(isParentWaitingResult) {
            setResult(RESULT_OK);
            finish();
        }else {
            Intent intent = MainActivity.newIntent(this, false);
            startActivity(intent);
            finish();
        }
    }

    /**
     * Метод выдает сообщение через snackbar, о том,
     * что город не выбран и спрашивает о том, хочет ли пользователь
     * выйти из приложения
     * При нажатии на кнопку, возвращается результат RESULT_CANCELED и активити закрывается
     */
    @Override
    public void cityNotSelectedShow() {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, R.string.snack_bar_message_city_not_selected, Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.snack_bar_action_exit, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        snackbar.show();
    }

    /**
     * Метод получает из интента boolean значение. По этому значению мы понимаени
     * запущено ли CitySelectionActivity из MainActivity, и MainActivity ожидает, результат, либо
     * CitySelectionActivity запущено из SplashActivity, тогда результат не ожидается
     */
    @Override
    public void getFromIntentIsParentWaitingResult() {

        if(getIntent()!= null){
            boolean isParentWaitingResult = getIntent().getBooleanExtra(CITY_SELECTION_ACTIVITY_EXTRA, false);
            citySelectionPresenter.onGetFromIntentIsParentWaitingResult(isParentWaitingResult);
        }

    }
}
