package com.ssdev.saeedsina.lostfound.MyActivities;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.widget.EditText;

import com.backtory.androidsdk.HttpStatusCode;
import com.backtory.androidsdk.internal.BacktoryCallBack;
import com.backtory.androidsdk.model.BacktoryResponse;
import com.backtory.androidsdk.model.BacktoryUser;
import com.ssdev.saeedsina.lostfound.MyClasses.MyHelper;
import com.ssdev.saeedsina.lostfound.MyViews.LoadingDialog;
import com.ssdev.saeedsina.lostfound.R;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WindowFeature;

@EActivity(R.layout.activity_register)
@WindowFeature(Window.FEATURE_NO_TITLE)
public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";

    @Bean
    MyHelper myHelper;

    @Bean
    LoadingDialog loadingDialog;

    @ViewById(R.id.etext_registration_name)
    EditText txt_name;

    @ViewById(R.id.etext_registration_fname)
    EditText txt_fname;

    @ViewById(R.id.etext_registration_username)
    EditText txt_username;

    @ViewById(R.id.etext_registration_email)
    EditText txt_email;

    @ViewById(R.id.etext_registration_password)
    EditText txt_password;

    @Click(R.id.btn_register)
    void btnRegister_Clicked(){
        validate();
    }

    @Click(R.id.txt_logrequest)
    void txtLogRequest_Clicked(){
        MainActivity_.intent(RegisterActivity.this).start();
    }

    private void validate() {
        if(!txt_name.getText().toString().isEmpty()){
            if(!txt_fname.getText().toString().isEmpty()){
                if(!txt_username.getText().toString().isEmpty()){
                    if(!txt_email.getText().toString().isEmpty()){
                        if(!txt_password.getText().toString().isEmpty()){
                            register();
                        }else { myHelper.toast(getApplicationContext().getString(R.string.enter_password)); }
                    }else { myHelper.toast(getApplicationContext().getString(R.string.enter_email)); }
                }else { myHelper.toast(getApplicationContext().getString(R.string.enter_username)); }
            }else { myHelper.toast(getApplicationContext().getString(R.string.enter_fname)); }
        }else{ myHelper.toast(getApplicationContext().getString(R.string.enter_name)); }
    }

    private void register() {
        loadingDialog.show();
        BacktoryUser newUser = new BacktoryUser.
                Builder().
                setFirstName(txt_name.getText().toString()).
                setLastName(txt_fname.getText().toString()).
                setUsername(txt_username.getText().toString()).
                setEmail(txt_email.getText().toString()).
                setPassword(txt_password.getText().toString())
                .build();
        newUser.registerInBackground(new BacktoryCallBack<BacktoryUser>() {

            // Register operation done (fail or success), handling it:
            @Override
            public void onResponse(BacktoryResponse<BacktoryUser> response) {
                // Checking result of operation
                if (response.isSuccessful()) {
                    // Successful
                    Log.d(TAG, "Register Success: new username is " + response.body().getUsername());
                    myHelper.toast(getApplicationContext().getString(R.string.succregister));
                    MainActivity_.intent(RegisterActivity.this).start();
                    loadingDialog.hide();
                    finish();
                } else if (response.code() == HttpStatusCode.Conflict.code()) {
                    // Username is invalid
                    Log.d(TAG, "Bad username: a user with this username already exist");
                    myHelper.toast(getApplicationContext().getString(R.string.userconflict));
                    loadingDialog.hide();
                } else {
                    // General failure
                    Log.d(TAG, "Registeration failed, for network or some other reasons");
                    myHelper.toast(getApplicationContext().getString(R.string.networkerror));
                    loadingDialog.hide();
                }
            }
        });
    }
}
