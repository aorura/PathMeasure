package com.iigo.pathmeasure;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onStartTestView(View view) {
        startActivity(new Intent(this, TestViewActivity.class));
    }

    public void onStartLoading1(View view) {
        Intent intent = new Intent(this, LoadingActivity.class);
        intent.putExtra("index", 0);
        startActivity(intent);
    }

    public void onStartLoading2(View view) {
        Intent intent = new Intent(this, LoadingActivity.class);
        intent.putExtra("index", 1);
        startActivity(intent);
    }

    public void onStartCarTrack(View view) {
        startActivity(new Intent(this, CarTrackActivity.class));
    }

    public void onSpeedometer(View view) {
        startActivity(new Intent(this, SpeedometerActivity.class));
    }

    public void onCadillacEnergy(View view) {
        startActivity(new Intent(this, CadillacEnergyActivity.class));
    }
}
