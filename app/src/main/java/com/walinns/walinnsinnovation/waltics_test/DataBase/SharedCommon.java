package com.walinns.walinnsinnovation.waltics_test.DataBase;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by walinnsinnovation on 12/01/18.
 */

public class SharedCommon {
    static SharedPreferences sharedPreferences;
    static SharedPreferences.Editor editor;
    public static final String email = "email";
    public static final String access_token = "access_token";

    public SharedCommon(Context context) {
        sharedPreferences = context.getSharedPreferences("my_pref", 0);
        editor = sharedPreferences.edit();
    }

    public void save(String key, String value) {
        editor.putString(key, value).apply();
        editor.commit();
    }

    public String getValue(String key) {
        String result = sharedPreferences.getString(key, "");
        return result;
    }
}
