package com.ssdev.saeedsina.lostfound.MyClasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.google.gson.Gson;



public class MyHelper {

    Context context;

    public MyHelper(Context context){
        this.context=context;
    }

    public void Toast(String toast) {
        Toast.makeText(context, toast, Toast.LENGTH_LONG).show();
    }

    public void saveString(String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String loadString(String key) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key, "");
    }

    public boolean saveInt(String name, int value) {
        SharedPreferences prefs = context.getSharedPreferences(
                context.getPackageName(), 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(name, value);
        return editor.commit();
    }

    public int loadInt(String name) {
        SharedPreferences prefs = context.getSharedPreferences(
                context.getPackageName(), 0);
        return prefs.getInt(name, 0);
    }

    public boolean saveLong(String name, long value) {
        SharedPreferences prefs = context.getSharedPreferences(
                context.getPackageName(), 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(name, value);
        return editor.commit();
    }

    public long loadLong(String name) {
        SharedPreferences prefs = context.getSharedPreferences(
                context.getPackageName(), 0);
        return prefs.getLong(name, 0);
    }

    public boolean saveBoolean(String name, boolean value) {
        SharedPreferences prefs = context.getSharedPreferences(
                context.getPackageName(), 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(name, value);
        return editor.commit();
    }

    public boolean loadBoolean(String name) {
        SharedPreferences prefs = context.getSharedPreferences(
                context.getPackageName(), 0);
        return prefs.getBoolean(name, false);
    }

    public String getDeviceId() {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    public String getImei() {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    public boolean saveObject(String name, Object value) {
        SharedPreferences prefs = context.getSharedPreferences(
                context.getPackageName(), 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(name, (new Gson()).toJson(value));
        return editor.commit();
    }

    public <T> T loadObject(String name, Class<T> cls) {
        SharedPreferences prefs = context.getSharedPreferences(
                context.getPackageName(), 0);
        String str = prefs.getString(name, null);
        if (str == null)
            return null;
        return (new Gson()).fromJson(str, cls);
    }
}
