package com.iigo.pathmeasure;

import android.os.Bundle;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import com.lge.vs.gaugebarlibrary.CadillacEnergyRangeGaugeBarView;

public class CadillacEnergyActivity extends AppCompatActivity {
    private SeekBar seek;
    private CadillacEnergyRangeGaugeBarView cadillacView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.cadillac_activity);
        seek = (SeekBar) findViewById(R.id.seek);
        seek.setOnSeekBarChangeListener(new SeekListener());
        cadillacView = findViewById(R.id.energy_range_gaugebar);

    }

    private class SeekListener implements SeekBar.OnSeekBarChangeListener {

        @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            cadillacView.setProgress(progress);
        }

        @Override public void onStartTrackingTouch(SeekBar seekBar) {
            //Empty
        }

        @Override public void onStopTrackingTouch(SeekBar seekBar) {
            //Empty
        }
    }
}
