package com.strikalov.weatherapp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.strikalov.weatherapp.R;

public class CitySelectionActivity extends AppCompatActivity {

    public static Intent newIntent(Context context){
        return new Intent(context, CitySelectionActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_selection);
    }
}
