package com.strikalov.weatherapp.view.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.strikalov.weatherapp.App;
import com.strikalov.weatherapp.R;
import com.strikalov.weatherapp.adapters.CityNavDrawerRecyclerViewAdapter;
import com.strikalov.weatherapp.common.CityDownloadsPreferences;
import com.strikalov.weatherapp.common.MeasureSettingsPreferences;
import com.strikalov.weatherapp.model.entities.City;
import com.strikalov.weatherapp.model.entities.WeatherForecast;
import com.strikalov.weatherapp.presenter.MainPresenter;
import com.strikalov.weatherapp.view.MainView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends MvpAppCompatActivity implements MainView, SwipeRefreshLayout.OnRefreshListener {

    /**
     * Инжектим mainPresenter c помощью Dagger
     */
    @Inject
    @InjectPresenter
    public MainPresenter mainPresenter;

    @ProvidePresenter
    public MainPresenter provideMainPresenter(){
        return mainPresenter;
    }

    {
        App.getInstance().getAppComponent().injectMainActivity(this);
    }

    /**
     * Вспомогательная константа для получения из интента информации переданной в активити
     */
    public static final String MAIN_ACTIVITY_EXTRA = "EXTRA_MAIN_ACTIVITY";

    /**
     * Метод возвращает интент и помещает внутрь значения boolean переменной.
     * С помощью данного интента можно запускать MainActivity из другой активити
     * @param context
     * @param isOnlineConnected
     * @return
     */
    public static Intent newIntent(Context context, boolean isOnlineConnected){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_EXTRA, isOnlineConnected);

        return intent;
    }

    /**
     * DrawerLayout необходим для показа слева навигационного меню NavigationDrawer
     * при нажатии на кнопку бургер
     */
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    /**
     * Ссылка на самый главный макет в mainActivity, для показа Snackbar
     */
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;

    /**
     * Кнопка с иконкой лупы в NavigationDrawer для вызовал CitySelectionActivity
     */
    @BindView(R.id.search_button)
    ImageButton navSearchButton;

    /**
     * Тексотовая кнопка в NavigationDrawer для вызыва SettingsActivity
     */
    @BindView(R.id.text_settings_button)
    TextView navSettingsButton;

    /**
     * Тексотовая кнопка в NavigationDrawer для отправки сообщения об ошибке
     * команде разработчиков
     */
    @BindView(R.id.text_send_error_button)
    TextView navSendErrorButton;

    /**
     * При свайпе вниз позволяет пользователю обновить прогноз погоды
     */
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    /**
     * RecyclerView расположенный в NavigationDrawer для отображения списка
     * избранных городов и также возможности при нажатии на один из городов получить
     * прогноз погоды для выбранного города
     */
    @BindView(R.id.navigation_menu_recycler)
    RecyclerView navigationRecyclerView;
    /**
     * Адаптер для RecyclerView, который расположен в NavigationDrawer
     */
    private CityNavDrawerRecyclerViewAdapter cityNavDrawerRecyclerViewAdapter;

    /**
     * Текстовое поле для отображения температуры
     */
    @BindView(R.id.temperature_text)
    TextView temperatureText;

    /**
     * ImageView для отображения иконки, которая соответствует прогнозу погоды (Солнце, Тучи и т.д.)
     */
    @BindView(R.id.weather_picture)
    ImageView weatherPicture;

    /**
     * Текстовое поле показывает дату прогноза погоды
     */
    @BindView(R.id.text_date)
    TextView textDate;

    /**
     * Текстовое поле показывает описание текущей погоды (ясное небо, дождь и т.д)
     */
    @BindView(R.id.weather_picture_description)
    TextView weatherPictureDescription;

    /**
     * Текстовое поле показывает значение влажности
     */
    @BindView(R.id.text_humidity)
    TextView textHumidity;

    /**
     * Текстовое поле показывает значение атмосферного давления
     */
    @BindView(R.id.text_pressure)
    TextView textPressure;

    /**
     * Текстовое поле показывает время восхода солнца
     */
    @BindView(R.id.text_sunrise)
    TextView textSunrise;

    /**
     * Текстовое поле показывает значение скорости ветра
     */
    @BindView(R.id.wind_speed)
    TextView windSpeed;

    /**
     * Текстовое поле показывает направление ветра
     */
    @BindView(R.id.wind_direction)
    TextView windDirection;

    /**
     * Текстовое поле показывает время заходы солнца
     */
    @BindView(R.id.text_sunset)
    TextView textSunset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_drawer_activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar_main_activity);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        /**
         * Инициализации для отображения NavigationDrawer
         */
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer,
                toolbar,
                R.string.nav_open_drawer,
                R.string.nav_close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        /**
         * Инициализация свайпа вниз для обновления прогноза погоды
         */
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(this);

        initNavigationRecyclerView();

    }

    /**
     * При нажатии на иконку с лупой в NavigationDrawer закрываем
     * навигационную шторку и вызываем соответствующий метод в презентере
     */
    @OnClick(R.id.search_button)
    public void onClickNavSearchButton(){
        drawer.closeDrawer(GravityCompat.START);
        mainPresenter.onClickCitySelection();
    }

    /**
     * При нажатии на текстовую кнопку для вызыва экрана настроек, закрываем
     * навигационную шторку и вызываем соответствующий метод в презентере
     */
    @OnClick(R.id.text_settings_button)
    public void onClickNavSettingsButton(){
        drawer.closeDrawer(GravityCompat.START);
        mainPresenter.onClickSettings();
    }

    /**
     * При нажатии на текстовую кнопку для отправки сообщения
     * команде разработчиков, закрываем навигационную шторку и
     * вызываем соответствующий метод в презентере
     */
    @OnClick(R.id.text_send_error_button)
    public void onClickNavSendErrorButton(){
        drawer.closeDrawer(GravityCompat.START);
        mainPresenter.onClickSendFeedback();
    }


    /**
     * Инициализируем RecyclerView в NavigationDrawer с избранными городами
     */
    private void initNavigationRecyclerView(){
        cityNavDrawerRecyclerViewAdapter =
                new CityNavDrawerRecyclerViewAdapter(new ArrayList<City>(),
                        getResources().getColor(R.color.colorAccent));
        navigationRecyclerView.setAdapter(cityNavDrawerRecyclerViewAdapter);
        navigationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cityNavDrawerRecyclerViewAdapter.setOnItemClickListener(new CityNavDrawerRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                /**
                 * При нажати на город пользователем, закрываем навигационную шторку и вызываем
                 * соответствующий метод в презентера
                 */
                drawer.closeDrawer(GravityCompat.START);
                mainPresenter.onCitySelected(position);
            }
        });
    }

    /**
     * Метод передает в адаптер cityNavDrawerRecyclerViewAdapter новый список
     * избранных городов и обновляет адаптер
     * @param favoritesCityList
     */
    @Override
    public void updateNavigationRecyclerView(List<City> favoritesCityList) {
        if(cityNavDrawerRecyclerViewAdapter != null){
            cityNavDrawerRecyclerViewAdapter.setCityList(favoritesCityList);
            cityNavDrawerRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Метод срабатывает, когда пользователе свайпит вниз, чтобы обновить прогнозы погоы
     * По свайпу, мы вызываем соответствующий метод у презентера
     */
    @Override
    public void onRefresh() {
        mainPresenter.onSwipeRefreshLayout();
    }

    /**
     * В этом методе мы запрашиваем из интента boolean значение,
     * которое говорит нам, получали ли мы до старта MainActivity
     * доступ к сети и сохраняли ли в базу данных актуальный прогноз погоды
     */
    @Override
    public void getFromIntentWasOnline(){

        if(getIntent()!= null){

            boolean wasOnline = getIntent().getBooleanExtra(MAIN_ACTIVITY_EXTRA, false);
            mainPresenter.onGetFromIntentWasOnline(wasOnline);
        }

    }

    /**
     * Метод срабатывает, когда пользователь нажимает на телефоне backbutton
     * - Если навигационная шторка открыта, она закрывается
     * - Если нет, завершается работа текущей активности
     */
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * В данном методе из preference файла получаем cityIndex
     */
    @Override
    public void getCityIndex() {
        String cityIndex = CityDownloadsPreferences.getCityIndex(this);
        mainPresenter.onGetCityIndex(cityIndex);
    }


    /**
     * В данном методе из preference файла получаем cityName
     */
    @Override
    public void getCityName() {
        String cityName = CityDownloadsPreferences.getCityName(this);
        mainPresenter.onGetCityName(cityName);
    }

    /**
     * В этом методе в preference файл сохранятся новые значения cityIndex
     * и cityName
     * @param cityIndex
     * @param cityName
     */
    @Override
    public void setCityDownloadsPreferences(String cityIndex, String cityName) {
        CityDownloadsPreferences.setCityIndex(this, cityIndex);
        CityDownloadsPreferences.setCityName(this, cityName);
    }

    /**
     * Этот метод устанавливает для toolbar в качестве заголовка
     * название города cityName
     * @param cityName
     */
    @Override
    public void setToolbarTitle(String cityName) {
        getSupportActionBar().setTitle(cityName);
    }

    /**
     * В данном методе из preference файла получаем текущие настроки
     * для единицы измерения скорости ветра
     */
    @Override
    public void getWindMeasure() {
        int windMeasure = MeasureSettingsPreferences.getWindMeasure(this);
        mainPresenter.onGetWindMeasure(windMeasure);
    }

    /**
     * В данном методе из preference файла получаем текущие настроки
     * для единице измерения температуры
     */
    @Override
    public void getTemperatureMeasure() {
        int temperatureMeasure = MeasureSettingsPreferences.getTemperatureMeasure(this);
        mainPresenter.onGetTemperatureMeasure(temperatureMeasure);
    }

    /**
     * В данном методе из preference файла получаем текущие настройки
     * для единицы измерения атмосферного давления
     */
    @Override
    public void getPressureMeasure() {
        int pressureMeasure = MeasureSettingsPreferences.getPressureMeasure(this);
        mainPresenter.onGetPressureMeasure(pressureMeasure);
    }

    /**
     * Метод показывает snackbar, с сообщением, что невозможно показать прогноз
     * погоды из-за отсутствия соединения с интернетом, просим проверить соединение
     * и обновить страницу
     */
    @Override
    public void showSnackBarNoDataToDisplay() {
        final Snackbar snackbar = Snackbar.make(coordinatorLayout, R.string.snack_bar_no_data_to_display, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(R.string.snack_bar_action_ok, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    /**
     * Метод показывает snackbar с сообщением о том, что настройки успешно были сохранены
     */
    @Override
    public void showSnackBarSettingsSavedSuccessfully() {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, R.string.snack_bar_settings_saved_successfully, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    /**
     * Метод показывает progressbar
     */
    @Override
    public void swipeRefreshShow() {
        swipeRefreshLayout.setRefreshing(true);
    }

    /**
     * Метод убирает progressbar
     */
    @Override
    public void swipeRefreshHide() {
        swipeRefreshLayout.setRefreshing(false);
    }

    /**
     * Метод показывает прогноз погоды пользователю с учетом настроек единиц измерения
     * @param weatherForecast
     * @param windMeasure
     * @param temperatureMeasure
     * @param pressureMeasure
     */
    @Override
    public void showWeatherForecast(WeatherForecast weatherForecast, int windMeasure, int temperatureMeasure, int pressureMeasure) {

        String temperatureValue = null;
        switch (temperatureMeasure){
            case MeasureSettingsPreferences.DEGREES_CELSIUS:
                temperatureValue = weatherForecast.getTemperatureDegreesCelsius()
                        + getString(R.string.main_activity_temperature_degrees_celsius);
                break;
            case MeasureSettingsPreferences.DEGREES_FAHRENHEIT:
                temperatureValue = weatherForecast.getTemperatureDegreesFahrenheit()
                        + getString(R.string.main_activity_temperature_degrees_fahrenheit);
                temperatureText.setText(temperatureValue);
                break;
        }
        if(temperatureValue != null) {
            temperatureText.setText(temperatureValue);
        }

        switch (weatherForecast.getWeatherPicture()){
            case P01:
                weatherPicture.setImageResource(R.drawable.p01);
                break;
            case P02:
                weatherPicture.setImageResource(R.drawable.p02);
                break;
            case P03:
                weatherPicture.setImageResource(R.drawable.p03);
                break;
            case P04:
                weatherPicture.setImageResource(R.drawable.p04);
                break;
            case P09:
                weatherPicture.setImageResource(R.drawable.p09);
                break;
            case P10:
                weatherPicture.setImageResource(R.drawable.p10);
                break;
            case P11:
                weatherPicture.setImageResource(R.drawable.p11);
                break;
            case P13:
                weatherPicture.setImageResource(R.drawable.p13);
                break;
            case P50:
                weatherPicture.setImageResource(R.drawable.p50);
                break;
            case NO_ICON:
                break;
        }

        textDate.setText(weatherForecast.getDate());

        switch (weatherForecast.getWeatherPicture().getDescription()){
            case "CLEAR_SKY":
                weatherPictureDescription.setText(R.string.weather_picture_clear_sky);
                break;
            case "FEW_CLOUDS":
                weatherPictureDescription.setText(R.string.weather_picture_few_clouds);
                break;
            case "SCATTERED_CLOUDS":
                weatherPictureDescription.setText(R.string.weather_picture_scattered_clouds);
                break;
            case "BROKEN_CLOUDS":
                weatherPictureDescription.setText(R.string.weather_picture_broken_clouds);
                break;
            case "SHOWER_RAIN":
                weatherPictureDescription.setText(R.string.weather_picture_shower_rain);
                break;
            case "RAIN":
                weatherPictureDescription.setText(R.string.weather_picture_rain);
                break;
            case "THUNDERSTORM":
                weatherPictureDescription.setText(R.string.weather_picture_thunderstorm);
                break;
            case "SNOW":
                weatherPictureDescription.setText(R.string.weather_picture_snow);
                break;
            case "MIST":
                weatherPictureDescription.setText(R.string.weather_picture_mist);
                break;
            case "NO_ICON":
                break;
            default:
                break;
        }

        String humidityValue = weatherForecast.getHumidity() + getString(R.string.main_activity_humidity_percent);
        textHumidity.setText(humidityValue);

        String pressureValue = null;

        switch (pressureMeasure){
            case MeasureSettingsPreferences.MILLIMETERS:
                pressureValue = weatherForecast.getPressureMillimeters()
                        + getString(R.string.main_activity_pressure_millimeters);
                break;
            case MeasureSettingsPreferences.HPA:
                pressureValue = weatherForecast.getPressureHpa()
                        + getString(R.string.main_activity_pressure_hpa);
                break;
            default:
                break;
        }

        if(pressureValue != null){
            textPressure.setText(pressureValue);
        }

        textSunrise.setText(weatherForecast.getSunriseTime());
        textSunset.setText(weatherForecast.getSunsetTime());

        String windSpeedValue = null;

        switch(windMeasure){
            case MeasureSettingsPreferences.METERS_PER_SECOND:
                windSpeedValue = weatherForecast.getWindMetersPerSecond()
                        + getString(R.string.main_activity_wind_speed_meters_per_second);
                break;
            case MeasureSettingsPreferences.KILOMETERS_PER_HOUR:
                windSpeedValue = weatherForecast.getWindKilometersPerHour()
                        + getString(R.string.main_activity_wind_speed_kilometers_per_hour);
                break;
            default:
                break;
        }

        if(windSpeedValue != null){
            windSpeed.setText(windSpeedValue);
        }

        switch (weatherForecast.getWindDirection()){
            case NORTH:
                windDirection.setText(R.string.main_activity_wind_direction_north);
                windDirection.setCompoundDrawablesWithIntrinsicBounds(0,
                        R.drawable.north_pic_36,0,0);
                break;
            case EAST:
                windDirection.setText(R.string.main_activity_wind_direction_east);
                windDirection.setCompoundDrawablesWithIntrinsicBounds(0,
                        R.drawable.east_pic_36,0,0);
                break;
            case WEST:
                windDirection.setText(R.string.main_activity_wind_direction_west);
                windDirection.setCompoundDrawablesWithIntrinsicBounds(0,
                        R.drawable.west_pic_36,0,0);
                break;
            case SOUTH:
                windDirection.setText(R.string.main_activity_wind_direction_south);
                windDirection.setCompoundDrawablesWithIntrinsicBounds(0,
                        R.drawable.south_pic_36,0,0);
                break;
            case NORTH_EAST:
                windDirection.setText(R.string.main_activity_wind_direction_north_east);
                windDirection.setCompoundDrawablesWithIntrinsicBounds(0,
                        R.drawable.north_east_pic_36,0,0);
                break;
            case NORTH_WEST:
                windDirection.setText(R.string.main_activity_wind_direction_north_west);
                windDirection.setCompoundDrawablesWithIntrinsicBounds(0,
                        R.drawable.north_west_pic_36,0,0);
                break;
            case SOUTH_EAST:
                windDirection.setText(R.string.main_activity_wind_direction_south_east);
                windDirection.setCompoundDrawablesWithIntrinsicBounds(0,
                        R.drawable.south_east_pic_36,0,0);
                break;
            case SOUTH_WEST:
                windDirection.setText(R.string.main_activity_wind_direction_south_west);
                windDirection.setCompoundDrawablesWithIntrinsicBounds(0,
                        R.drawable.south_west_pic_36,0,0);
                break;
            case NO_DIRECTION:
                break;
        }

    }

    /**
     * Метод очишает все поля, где показывается прогноз погоды
     */
    @Override
    public void resetWeatherForecastData() {

        temperatureText.setText("");
        weatherPicture.setImageDrawable(null);
        textDate.setText("");
        weatherPictureDescription.setText("");
        textHumidity.setText("");
        textPressure.setText("");
        textSunrise.setText("");
        windSpeed.setText("");
        windDirection.setText("");
        windDirection.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        textSunset.setText("");

    }

    /**
     * Метод проверяет, есть ли связь с интернетом
     */
    @Override
    public void isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()){
            mainPresenter.onIsOnline(true);
        }else {
            mainPresenter.onIsOnline(false);
        }
    }

    /**
     * Метод срабатывает, когда пользователь хочет отправить сообщение об ошибке, и предоставляет
     * пользователю на выбор почтовые приложения. После выбора пользователем приложения,
     * в графе кому, уже будет вставлен контактный email, команды разработки
     */
    @Override
    public void causeMailApp() {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + "strikalov.aleksandr@yandex.ru"));
        String chooserTitle = getString(R.string.chooser_title);
        Intent chooseIntent = Intent.createChooser(intent,chooserTitle);
        startActivity(chooseIntent);

    }

    /**
     * Метод стартует SettingsActivity и позволяет получить результат из этой активти
     * после завершения ее работы
     * @param requestCode
     */
    @Override
    public void startSettingsActivityForResult(int requestCode) {
        Intent intent = SettingsActivity.newIntent(this);
        startActivityForResult(intent, requestCode);
    }

    /**
     * Метод стартует CitySelectionActivity и позволяет получить результат из этой активти
     * после завершения ее работы
     * @param requestCode
     */
    @Override
    public void startCitySelectionActivityForResult(int requestCode) {
        Intent intent = CitySelectionActivity.newIntent(this, true);
        startActivityForResult(intent, requestCode);
    }

    /**
     * Метод срабатывает когда либо CitySelectionActivity либо SettingsActivity
     * завершают свою работы и передают результат в MainActivity
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        mainPresenter.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Метод завершает текущую активити
     */
    @Override
    public void finishActivity() {
        finish();
    }
}
