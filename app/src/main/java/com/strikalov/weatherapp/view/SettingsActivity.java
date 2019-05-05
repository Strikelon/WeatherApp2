package com.strikalov.weatherapp.view;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.strikalov.weatherapp.App;
import com.strikalov.weatherapp.R;
import com.strikalov.weatherapp.common.MeasureSettingsPreferences;
import com.strikalov.weatherapp.presenter.SettingsPresenter;

import javax.inject.Inject;

public class SettingsActivity extends MvpAppCompatActivity implements SettingsView{

    /**
     * Инжектим презентер с помощью dagger
     */
    @Inject
    @InjectPresenter
    public SettingsPresenter settingsPresenter;

    @ProvidePresenter
    public SettingsPresenter provideSettingsPresenter(){
        return settingsPresenter;
    }

    {
        App.getInstance().getAppComponent().injectSettingsActivity(this);
    }

    /**
     * Создание групп радио кнопок
     */
    private RadioGroup radioGroupWindSpeedGroup;
    private RadioGroup radioGroupTemperatureGroup;
    private RadioGroup radioGroupPressureGroup;

    /**
     * Создание радиокнопок
     */
    private RadioButton radioButtonMeters;
    private RadioButton radioButtonKilometers;
    private RadioButton radioButtonCelsius;
    private RadioButton radioButtonFahrenheit;
    private RadioButton radioButtonMm;
    private RadioButton radioButtonHpa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbar_settings);
        setSupportActionBar(toolbar);

        initWidgets();

    }

    /**
     * Инициализируем радионопки и группы радиокнопок,
     * для последующей работы с ними
     */
    private void initWidgets(){
        radioGroupWindSpeedGroup = findViewById(R.id.wind_speed_group);
        radioGroupTemperatureGroup = findViewById(R.id.temperature_group);
        radioGroupPressureGroup = findViewById(R.id.pressure_group);

        radioButtonMeters = findViewById(R.id.radio_meters);
        radioButtonKilometers = findViewById(R.id.radio_kilometers);
        radioButtonCelsius = findViewById(R.id.radio_celsius);
        radioButtonFahrenheit = findViewById(R.id.radio_fahrenheit);
        radioButtonMm = findViewById(R.id.radio_mm);
        radioButtonHpa = findViewById(R.id.radio_hpa);
    }

    /**
     * В методе запрашиваются текущие настройки единиц измерения погоды
     * из соответствующего файла preferences и радиокнопки устанавливают в
     * соответствующии значения
     */
    @Override
    public void initMeasureSettings() {

        int windSettings = MeasureSettingsPreferences.getWindMeasure(this);
        int temperatureSettings = MeasureSettingsPreferences.getTemperatureMeasure(this);
        int pressureSettings = MeasureSettingsPreferences.getPressureMeasure(this);

        switch (windSettings){
            case MeasureSettingsPreferences.METERS_PER_SECOND:
                radioButtonMeters.setChecked(true);
                radioButtonKilometers.setChecked(false);
                break;
            case MeasureSettingsPreferences.KILOMETERS_PER_HOUR:
                radioButtonKilometers.setChecked(true);
                radioButtonMeters.setChecked(false);
                break;
            default:
                break;
        }

        switch (temperatureSettings){
            case MeasureSettingsPreferences.DEGREES_CELSIUS:
                radioButtonCelsius.setChecked(true);
                radioButtonFahrenheit.setChecked(false);
                break;
            case MeasureSettingsPreferences.DEGREES_FAHRENHEIT:
                radioButtonFahrenheit.setChecked(true);
                radioButtonCelsius.setChecked(false);
                break;
            default:
                break;
        }

        switch (pressureSettings){
            case MeasureSettingsPreferences.MILLIMETERS:
                radioButtonMm.setChecked(true);
                radioButtonHpa.setChecked(false);
                break;
            case MeasureSettingsPreferences.HPA:
                radioButtonHpa.setChecked(true);
                radioButtonMm.setChecked(false);
                break;
            default:
                break;
        }

    }

    /**
     * При нажатии на одну из радиокнопок группы WindSpeedGroup
     * сообщаем об этом презентеру
     * @param view
     */
    public void onRadioButtonWindSpeedGroupClicked(View view){
        settingsPresenter.onRadioButtonWindSpeedGroupClicked();
    }

    /**
     * При нажатии на одну из радиокнопок группы TemperatureGroup
     * сообщаем об этом презентеру
     * @param view
     */
    public void onRadioButtonTemperatureGroupClicked(View view){
        settingsPresenter.onRadioButtonTemperatureGroupClicked();
    }

    /**
     * При нажатии на одну из радиокнопок группы PressureGroup
     * сообщаем об этом презентеру
     * @param view
     */
    public void onRadioButtonPressureGroupClicked(View view){
        settingsPresenter.onRadioButtonPressureGroupClicked();
    }

    /**
     * При нажатии на плавающую кнопку done сообщаем об этом презентеру
     * @param view
     */
    public void onFloatingActionButtonDoneClicked(View view){
        settingsPresenter.onFloatingActionButtonDoneClicked();
    }

    /**
     * В файл настроек preferences сохраняются новые настройки для
     * единиц измерения скорости ветра
     */
    @Override
    public void changeWindSpeedSettings() {
        int id = radioGroupWindSpeedGroup.getCheckedRadioButtonId();
        switch (id){
            case R.id.radio_meters:
                MeasureSettingsPreferences.setWindMeasure(this,
                        MeasureSettingsPreferences.METERS_PER_SECOND);
                break;
            case R.id.radio_kilometers:
                MeasureSettingsPreferences.setWindMeasure(this,
                        MeasureSettingsPreferences.KILOMETERS_PER_HOUR);
                break;
            default:
                break;
        }
    }

    /**
     * В файл настроек preferences сохраняются новые настройки для
     * единиц измерения температуры
     */
    @Override
    public void changeTemperatureSettings() {
        int id  = radioGroupTemperatureGroup.getCheckedRadioButtonId();
        switch (id){
            case R.id.radio_celsius:
                MeasureSettingsPreferences.setTemperatureMeasure(this,
                        MeasureSettingsPreferences.DEGREES_CELSIUS);
                break;
            case R.id.radio_fahrenheit:
                MeasureSettingsPreferences.setTemperatureMeasure(this,
                        MeasureSettingsPreferences.DEGREES_FAHRENHEIT);
                break;
            default:
                break;
        }
    }

    /**
     * В файл настроек preferences сохраняются новые настройки для
     * единиц измерения давления
     */
    @Override
    public void changePressureSettings() {
        int id = radioGroupPressureGroup.getCheckedRadioButtonId();
        switch (id){
            case R.id.radio_mm:
                MeasureSettingsPreferences.setPressureMeasure(this,
                        MeasureSettingsPreferences.MILLIMETERS);
                break;
            case R.id.radio_hpa:
                MeasureSettingsPreferences.setPressureMeasure(this,
                        MeasureSettingsPreferences.HPA);
                break;
            default:
                break;
        }
    }

    /**
     * Метод закрывает активити
     */
    @Override
    public void finishView() {
        finish();
    }
}
