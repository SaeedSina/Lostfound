package com.ssdev.saeedsina.lostfound.MyActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.ssdev.saeedsina.lostfound.R;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.WindowFeature;


@EActivity(R.layout.activity_menu)
@WindowFeature(Window.FEATURE_NO_TITLE)
public class MenuActivity extends AppCompatActivity {
    private static final String TAG = "MenuActivity";
}
