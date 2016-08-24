package com.ssdev.saeedsina.lostfound.MyActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.ssdev.saeedsina.lostfound.MyClasses.MyHelper;
import com.ssdev.saeedsina.lostfound.MyViews.LoadingDialog;
import com.ssdev.saeedsina.lostfound.R;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WindowFeature;


@EActivity(R.layout.activity_menu)
@WindowFeature(Window.FEATURE_NO_TITLE)
public class MenuActivity extends AppCompatActivity {
    private static final String TAG = "MenuActivity";

    @Bean
    MyHelper myHelper;

    @Bean
    LoadingDialog loadingDialog;

    @ViewById(R.id.btn_ilost)
    Button btnILost;
    @ViewById(R.id.btn_ifound)
    Button btnIFound;
    @ViewById(R.id.btn_adlist)
    Button btnAdList;
    @ViewById(R.id.btn_myaccount)
    Button btnMyAccount;
    @ViewById(R.id.btn_support)
    Button btnSupport;

    @Click(R.id.btn_ilost)
    void btnILost_Clicked(){
        //TODO go to submit page
    }
}
