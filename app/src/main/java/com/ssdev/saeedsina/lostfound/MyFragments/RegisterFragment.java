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
import com.backendless.async.callback.BackendlessCallback;
import com.backendless.exceptions.BackendlessException;
import com.backendless.exceptions.BackendlessFault;
import com.ssdev.saeedsina.lostfound.MyActivities.MainActivity;
import com.ssdev.saeedsina.lostfound.MyClasses.MyHelper;
import com.ssdev.saeedsina.lostfound.MyInterfaces.OnMenuItemSelectedListener;
import com.ssdev.saeedsina.lostfound.MyViews.LoadingDialog;
import com.ssdev.saeedsina.lostfound.R;

import java.util.HashMap;
import java.util.Map;


public class RegisterFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "REGISTER_FRAGMENT";

    TextView loginRequestTextView;
    Button btn_register;
    EditText txt_password,txt_name,txt_email;
    View myView;
    OnMenuItemSelectedListener mListener;
    MyHelper myHelper;
    LoadingDialog loadingDialog;


    public RegisterFragment() {
        // Required empty public constructor
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView= inflater.inflate(R.layout.fragment_register, container, false);
        loginRequestTextView=(TextView)myView.findViewById(R.id.txt_logrequest);
        loginRequestTextView.setTag("login");
        loginRequestTextView.setOnClickListener(this);
        btn_register=(Button)myView.findViewById(R.id.btn_register);
        btn_register.setTag("register");
        btn_register.setOnClickListener(this);
        txt_email=(EditText)myView.findViewById(R.id.txt_register_email);
        txt_name=(EditText)myView.findViewById(R.id.txt_register_name);
        txt_password=(EditText)myView.findViewById(R.id.txt_register_password);
        myHelper=new MyHelper(getContext());
        ((MainActivity) getActivity()).setActionBarTitle("Register");

        loadingDialog=new LoadingDialog(getContext());
        return myView;
    }

    @Override
    public void onClick(View view) {
       switch (view.getTag().toString()){
           case "register":
               Validate();
               break;
           case "login":
               mListener.onMenuItemSelected("login","register",null);
       }
    }

    private void Validate() {
        if(!txt_name.getText().toString().isEmpty()){
                    if(!txt_email.getText().toString().isEmpty()){
                        if(!txt_password.getText().toString().isEmpty()){
                            Register();
                        }else { myHelper.Toast(getContext().getString(R.string.enter_password)); }
                    }else { myHelper.Toast(getContext().getString(R.string.enter_email)); }
        }else{ myHelper.Toast(getContext().getString(R.string.enter_name)); }
    }

    private void Register() {
        loadingDialog.show();
        BackendlessUser user = new BackendlessUser();
        user.setEmail( txt_email.getText().toString() );
        user.setPassword( txt_password.getEditableText().toString() );
        Map<String,Object> prop = new HashMap<String,Object>();
        prop.put("name",txt_name.getText().toString());
        user.putProperties(prop);
        Backendless.UserService.register( user, new BackendlessCallback<BackendlessUser>()
        {
            @Override
            public void handleResponse( BackendlessUser backendlessUser )
            {
                Toast.makeText(getContext(),"User Created Successfully", Toast.LENGTH_SHORT).show();
                mListener.onMenuItemSelected("login","register",null);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                super.handleFault(fault);
                if(fault.getCode().equals(3033)){
                    myHelper.Toast("User Already Exists");
                }
                else if(fault.getCode().equals(3040)){
                    myHelper.Toast("Check the email syntax");
                }
                else{
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        } );
        loadingDialog.hide();
    }
}