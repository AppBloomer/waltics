package com.walinns.walinnsinnovation.waltics_test;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.backendless.Backendless;

/**
 * Created by walinnsinnovation on 16/01/18.
 */

public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Backendless.initApp( getApplicationContext(), "1DE5325D-729C-F4E3-FF21-034A224D1500", "D5A81C2D-3AA0-CC8F-FFF1-7E8C8E92F400" );


    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
