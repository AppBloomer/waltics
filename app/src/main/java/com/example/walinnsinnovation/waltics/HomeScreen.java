package com.example.walinnsinnovation.waltics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
    }
}
