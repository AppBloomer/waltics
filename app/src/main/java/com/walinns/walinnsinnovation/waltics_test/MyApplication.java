package com.walinns.walinnsinnovation.waltics_test;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.backendless.Backendless;
import com.clevertap.android.sdk.ActivityLifecycleCallback;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by walinnsinnovation on 16/01/18.
 */

public class MyApplication extends Application{
    private static GoogleAnalytics sAnalytics;
    private static Tracker sTracker;
    @Override
    public void onCreate() {
        super.onCreate();
        ActivityLifecycleCallback.register(this);
        Backendless.initApp( getApplicationContext(), "1DE5325D-729C-F4E3-FF21-034A224D1500", "D5A81C2D-3AA0-CC8F-FFF1-7E8C8E92F400" );
        sAnalytics = GoogleAnalytics.getInstance(this);


    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    synchronized public Tracker getDefaultTracker() {
        // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
        if (sTracker == null) {
            sTracker = sAnalytics.newTracker(R.xml.global_tracker);
        }

        return sTracker;
    }
}
