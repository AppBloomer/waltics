package com.walinns.walinnsinnovation.waltics_test;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.walinns.walinnsapi.WalinnsAPI;

/**
 * Created by walinnsinnovation on 16/01/18.
 */

public class MyApplication extends Application{
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        WalinnsAPI.getInstance().initialize(this,"c2adf4e541be17c68474ad35a3c67f3e");

    }
}
