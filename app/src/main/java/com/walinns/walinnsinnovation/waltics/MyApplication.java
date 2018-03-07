package com.walinns.walinnsinnovation.waltics;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.clevertap.android.sdk.ActivityLifecycleCallback;

/**
 * Created by walinnsinnovation on 16/01/18.
 */

public class MyApplication extends Application{

    @Override
    public void onCreate() {
        ActivityLifecycleCallback.register(this);
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
