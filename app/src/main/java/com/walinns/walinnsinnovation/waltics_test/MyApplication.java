package com.walinns.walinnsinnovation.waltics_test;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by walinnsinnovation on 16/01/18.
 */

public class MyApplication extends Application{
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
