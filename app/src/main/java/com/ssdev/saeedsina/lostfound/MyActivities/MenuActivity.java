package com.ssdev.saeedsina.lostfound.MyActivities;

import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.backtory.androidsdk.internal.Backtory;
import com.backtory.androidsdk.internal.BacktoryFile;
import com.backtory.androidsdk.internal.BacktoryFileStorage;
import com.backtory.androidsdk.model.BacktoryUser;
import com.ogaclejapan.arclayout.ArcLayout;
import com.ssdev.saeedsina.lostfound.MyClasses.MyHelper;
import com.ssdev.saeedsina.lostfound.MyViews.LoadingDialog;
import com.ssdev.saeedsina.lostfound.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
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

    @ViewById(R.id.arc)
    ArcLayout myArc;

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
    @ViewById(R.id.btn_enter)
    Button btnEnter;
    @ViewById(R.id.btn_logout)
    Button btnLogout;

    @AfterViews
    void afterView(){
        if(BacktoryUser.getCurrentUser() != null){
            ((ViewGroup)btnEnter.getParent()).removeView(btnEnter);
        }
        else{
            ((ViewGroup)btnLogout.getParent()).removeView(btnLogout);
            ((ViewGroup)btnMyAccount.getParent()).removeView(btnMyAccount);
        }
    }

    @Click(R.id.btn_logout)
    void btnLogout_Clicked(){
        loadingDialog.show();
        logout();
    }

    @Background
    void logout() {
        BacktoryUser.getCurrentUser().logout();
        loadingDialog.hide();
        MainActivity_.intent(MenuActivity.this).start();
        finish();
    }

    @Click(R.id.btn_enter)
    void btnEnter_Clicked(){
        MainActivity_.intent(MenuActivity.this).start();
        finish();
    }


    @Click(R.id.btn_adlist)
    void btnAdList_Clicked(){
        if(BacktoryUser.getCurrentUser() == null){
            myHelper.toast(getApplicationContext().getString(R.string.pleaselogin));
            MainActivity_.intent(MenuActivity.this).start();
            finish();
        }
        else{
            //// TODO: Go to Ad list Page
        }
    }
    @Click(R.id.btn_ilost)
    void btnILost_Clicked(){
        if(BacktoryUser.getCurrentUser() == null){
            myHelper.toast(getApplicationContext().getString(R.string.pleaselogin));
            MainActivity_.intent(MenuActivity.this).start();
            finish();
        }
        else{
            SubmitLostActivity_.intent(MenuActivity.this).start();
        }
    }

}
