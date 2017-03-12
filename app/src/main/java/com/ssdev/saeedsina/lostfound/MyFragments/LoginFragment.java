package com.ssdev.saeedsina.lostfound.MyFragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.ssdev.saeedsina.lostfound.MyActivities.MainActivity;
import com.ssdev.saeedsina.lostfound.MyClasses.MyHelper;
import com.ssdev.saeedsina.lostfound.MyInterfaces.OnMenuItemSelectedListener;
import com.ssdev.saeedsina.lostfound.MyViews.LoadingDialog;
import com.ssdev.saeedsina.lostfound.R;


public class LoginFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "LOGIN_FRAGMENT";

    TextView registerRequestTextView;
    Button btn_login;
    EditText txt_username,txt_password;
    View myView;
    OnMenuItemSelectedListener mListener;
    MyHelper myHelper;
    LoadingDialog loadingDialog;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_login, container, false);
        registerRequestTextView = (TextView)myView.findViewById(R.id.txt_regrequest);
        registerRequestTextView.setTag("register");
        registerRequestTextView.setOnClickListener(this);
        btn_login=(Button)myView.findViewById(R.id.btn_login);
        btn_login.setTag("login");
        btn_login.setOnClickListener(this);
        txt_username=(EditText)myView.findViewById(R.id.txt_login_username);
        txt_password=(EditText)myView.findViewById(R.id.txt_login_password);
        myHelper=new MyHelper(getContext());
        loadingDialog=new LoadingDialog(getContext());
        ((MainActivity) getActivity()).setActionBarTitle("Login");

        return myView;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        try {
            mListener = (OnMenuItemSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnMenuItemSelectedListener");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getTag().toString()){
            case "register":
                mListener.onMenuItemSelected("register","login",null);
                break;
            case "login":
                Validate();
        }
    }

    private void Validate() {
        if(!txt_username.getText().toString().isEmpty()){
            if(!txt_password.getText().toString().isEmpty()){
                Login();
            }else{
                myHelper.Toast(getContext().getString(R.string.enter_password));
            }
        }else{
            myHelper.Toast(getContext().getString(R.string.enter_username));
        }
    }

    private void Login() {
        loadingDialog.show();
        Backendless.UserService.login(txt_username.getText().toString(), txt_password.getText().toString(), new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {
                Toast.makeText(getContext(), "Logged in Successfull", Toast.LENGTH_SHORT).show();
                mListener.onMenuItemSelected("menu","login",null);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                if(fault.getCode().equals(3003)){
                    myHelper.Toast("Invalid username password");
                }
                else{
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        },true);
        loadingDialog.hide();
    }
}
