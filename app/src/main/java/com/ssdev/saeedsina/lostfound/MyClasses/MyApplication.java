package com.ssdev.saeedsina.lostfound.MyClasses;

import android.app.Application;

import com.backtory.androidsdk.Storage;
import com.backtory.androidsdk.internal.Backtory;
import com.backtory.androidsdk.internal.Config;

import org.androidannotations.annotations.EApplication;

/**
 * Created by saeed on 22-Aug-16.
 */
@EApplication
public class MyApplication extends Application {

    
    public void onCreate() {
        super.onCreate();
        // Initializing backtory
        Backtory.init(this, Config.newBuilder().
                // Setting shared preferences as default storage for backtory
                        storage(new Storage.SharedPreferencesStorage(this)).
                // Enabling User Services
                        initAuth("57b5a76de4b0ba5a69c03a9f", "57b5a76de4b006242335757d").
                // Finilizing sdk
                        build());
    }
}
