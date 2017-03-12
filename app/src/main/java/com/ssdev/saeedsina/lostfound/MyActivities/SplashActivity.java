package com.ssdev.saeedsina.lostfound.MyActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.ssdev.saeedsina.lostfound.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {


    private Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);
        RelativeLayout v = (RelativeLayout) findViewById(R.id.activity_splash);

        timer = new Timer();
        timer.schedule(new TimerTask(){
            @Override
            public void run() {
                // TODO Auto-generated method stub
                Intent home_page = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(home_page);
                finish();
            }}, 5000);
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent home_page = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(home_page);
                timer.cancel();
                finish();
                return false;
            }
        });
    }
}
