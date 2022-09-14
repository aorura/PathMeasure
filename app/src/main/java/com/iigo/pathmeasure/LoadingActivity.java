package com.iigo.pathmeasure;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;


public class LoadingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int index = getIntent().getIntExtra("index", 0);
        setContentView(index == 0 ? new Loading1View(this) : new Loading2View(this));

    }
}
