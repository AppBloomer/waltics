package com.walinns.walinnsinnovation.waltics;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

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
        ActivityLifecycleCallback.register(this);
        super.onCreate();
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
