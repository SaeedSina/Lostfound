package com.ssdev.saeedsina.lostfound.MyActivities;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.backtory.androidsdk.HttpStatusCode;
import com.backtory.androidsdk.internal.BacktoryCallBack;
import com.backtory.androidsdk.model.BacktoryResponse;
import com.backtory.androidsdk.model.BacktoryUser;
import com.backtory.androidsdk.model.LoginResponse;
import com.rey.material.widget.TextView;
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

@EActivity(R.layout.activity_main)
@WindowFeature(Window.FEATURE_NO_TITLE)
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @ViewById(R.id.etext_login_username)
    EditText txt_username;

    @ViewById(R.id.etext_login_password)
    EditText txt_password;


    @ViewById(R.id.txt_regrequest)
    TextView txt_regrequest;

    @ViewById(R.id.btn_login)
    Button btn_login;

    @Bean
    MyHelper myHelper;

    @Bean
    LoadingDialog loadingDialog;


    @AfterViews
    void afterViews() {
        if (BacktoryUser.getCurrentUser() != null) {
            MenuActivity_.intent(MainActivity.this).start();
            finish();
        }
    }

    @Click(R.id.txt_regrequest)
    void txtregrequest_Clicked(){
        RegisterActivity_.intent(MainActivity.this).start();
    }

    @Click(R.id.btn_login)
    void btnLogin_Clicked(){
        validete();
    }

    private void validete() {
        if(!txt_username.getText().toString().isEmpty()){
            if(!txt_password.getText().toString().isEmpty()){
                login();
            }else{
                myHelper.toast(getApplicationContext().getString(R.string.enter_password));
            }
        }else{
            myHelper.toast(getApplicationContext().getString(R.string.enter_username));
        }
    }

    @Background
    void login() {
        String username = txt_username.getText().toString();
        String password = txt_password.getText().toString();
        loadingDialog.show();
        BacktoryUser.loginInBackground(txt_username.getText().toString(), txt_password.getText().toString(),
                new BacktoryCallBack<LoginResponse>() {

                    // Login operation done (fail or success), handling it:
                    @Override
                    public void onResponse(BacktoryResponse<LoginResponse> response) {
                        // Checking result of operation
                        if (response.isSuccessful()) {
                            // Login successfull
                            Log.d(TAG, "Wellcome " + txt_username.getText().toString());
                            myHelper.toast(getApplicationContext().getString(R.string.succlogin));
                            loadingDialog.hide();
                            MenuActivity_.intent(MainActivity.this).start();
                            finish();
                        } else if (response.code() == HttpStatusCode.Unauthorized.code()) {
                            Log.d(TAG, "Either username or password is wrong");
                            myHelper.toast(getApplicationContext().getString(R.string.userpasswrong));
                            loadingDialog.hide();
                        } else {
                            // Operation generally failed, maybe internet connection issue
                            Log.d(TAG, "Login failed for other reasons like network issues");
                            myHelper.toast(getApplicationContext().getString(R.string.networkerror));
                            loadingDialog.hide();
                        }
                    }

                });
        loadingDialog.hide();

    }

}
