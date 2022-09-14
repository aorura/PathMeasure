package com.iigo.pathmeasure;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class SpeedometerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(new SpeedometerView(this));
    }
}
