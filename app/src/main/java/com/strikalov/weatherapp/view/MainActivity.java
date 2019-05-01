package com.strikalov.weatherapp.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.strikalov.weatherapp.R;

public class MainActivity extends AppCompatActivity {

    public static final String MAIN_ACTIVITY_EXTRA = "EXTRA_MAIN_ACTIVITY";

    public static Intent newIntent(Context context, boolean isOnlineConnected){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_EXTRA, isOnlineConnected);

        return intent;
    }

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.main_text_view);

        if(getIntent()!= null){

            boolean isOnline = getIntent().getBooleanExtra(MAIN_ACTIVITY_EXTRA, false);

            String text = "MainActivity" + Boolean.toString(isOnline);

            textView.setText(text);

        }
    }

}
