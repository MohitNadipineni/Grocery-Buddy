package com.asquare.grocerybuddy.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreference {
    private static AppPreference appPreference;
    private SharedPreferences sharedPreferences;

    private AppPreference(Context context) {
        sharedPreferences =context.getSharedPreferences(AppConstants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static AppPreference getAppPreference(Context context) {
        if (appPreference == null) {
            appPreference = new AppPreference(context);
        }
        return appPreference;
    }

    public void save(String key, Object object) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (object.getClass().getName()) {
            case "java.lang.String":
                editor.putString(key, String.valueOf(object));
                break;
            case "java.lang.Integer":
                editor.putInt(key, Integer.valueOf(String.valueOf(object)));
                break;
            case "java.lang.Boolean":
                editor.putBoolean(key, Boolean.parseBoolean(String.valueOf(object)));
                break;
            case "java.lang.Float":
                editor.putFloat(key, Float.parseFloat(String.valueOf(object)));
                break;
            case "java.lang.Long":
                editor.putLong(key, Long.parseLong(String.valueOf(object)));
                break;
        }
        editor.apply();
    }

    public String getValue(String key, String value) {
        return sharedPreferences.getString(key, value);
    }

    public int getValue(String key, int value) {
        return sharedPreferences.getInt(key, value);
    }

    public boolean getValue(String key, boolean value) {
        return sharedPreferences.getBoolean(key, value);
    }

    public float getValue(String key, float value) {
        return sharedPreferences.getFloat(key, value);
    }

    public long getValue(String key, long value) {
        return sharedPreferences.getLong(key, value);
    }

}
