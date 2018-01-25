package com.walinns.walinnsinnovation.waltics_test;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ProgressBar;



public class Splashscreen extends AppCompatActivity {
    LinearLayout linear_g_plus,linear_fb;
    ProgressBar progress;
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


    }

}
