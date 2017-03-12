package com.ssdev.saeedsina.lostfound.MyFragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.ogaclejapan.arclayout.ArcLayout;
import com.ssdev.saeedsina.lostfound.MyActivities.MainActivity;
import com.ssdev.saeedsina.lostfound.MyClasses.Item;
import com.ssdev.saeedsina.lostfound.MyClasses.MyApplication;
import com.ssdev.saeedsina.lostfound.MyClasses.MyHelper;
import com.ssdev.saeedsina.lostfound.MyInterfaces.OnMenuItemSelectedListener;
import com.ssdev.saeedsina.lostfound.MyViews.LoadingDialog;
import com.ssdev.saeedsina.lostfound.R;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "MENU_FRAGMENT";

    Button btn_logout,btn_ilost,btn_ifound,btn_enter,btn_adlist,btn_about,btn_myaccount,btn_support;
    ArcLayout myarc;
    View myView;
    private TextView lostcount,foundcount;
    MyHelper myHelper;
    LoadingDialog loadingDialog;
    OnMenuItemSelectedListener mListener;
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

    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView= inflater.inflate(R.layout.fragment_menu, container, false);
        btn_logout=(Button)myView.findViewById(R.id.btn_logout);
        btn_logout.setTag("logout");
        btn_logout.setOnClickListener(this);
        btn_ilost=(Button)myView.findViewById(R.id.btn_ilost);
        btn_ilost.setTag("lost");
        btn_ilost.setOnClickListener(this);
        btn_ifound=(Button)myView.findViewById(R.id.btn_ifound);
        btn_ifound.setTag("found");
        btn_ifound.setOnClickListener(this);
        btn_support=(Button)myView.findViewById(R.id.btn_support);
        btn_support.setTag("support");
        btn_support.setOnClickListener(this);
        btn_about=(Button)myView.findViewById(R.id.btn_about);
        btn_about.setTag("about");
        btn_about.setOnClickListener(this);
        btn_myaccount=(Button)myView.findViewById(R.id.btn_myaccount);
        btn_myaccount.setTag("account");
        btn_myaccount.setOnClickListener(this);
        btn_adlist=(Button)myView.findViewById(R.id.btn_adlist);
        btn_adlist.setTag("pref");
        btn_adlist.setOnClickListener(this);
        btn_enter=(Button)myView.findViewById(R.id.btn_enter);
        btn_enter.setTag("enter");
        btn_enter.setOnClickListener(this);
        myarc=(ArcLayout)myView.findViewById(R.id.arc);
        myarc.removeAllViews();
        myarc.addView(btn_support);
        myarc.addView(btn_adlist);
        myarc.addView(btn_about);
        lostcount = (TextView) myView.findViewById(R.id.lostcount);
        foundcount = (TextView) myView.findViewById(R.id.foundcount);
        lostcount.setText("...");
        foundcount.setText("...");
        ((MainActivity) getActivity()).setActionBarTitle("Main Menu");
        Backendless.Persistence.of(Item.class).find(new AsyncCallback<BackendlessCollection<Item>>() {
            @Override
            public void handleResponse(BackendlessCollection<Item> response) {
               update_counts(response.getData());
            }

            @Override
            public void handleFault(BackendlessFault fault){
                int b = 2;
            }
        });

        if(Backendless.UserService.loggedInUser() != null){
            myarc.addView(btn_ilost);
            myarc.addView(btn_ifound);
            myarc.addView(btn_logout);
            myarc.addView(btn_myaccount);
        }
        else{
            myarc.addView(btn_enter);
        }
        myHelper=new MyHelper(getContext());
        loadingDialog=new LoadingDialog(getContext());
        return myView;
    }

    private void update_counts(List<Item> data) {
        List<Item> myItems = new ArrayList<>(0);
        myItems.addAll(data);
        int lcount=0;
        int fcount=0;
        for(Item t: myItems){
            if(t.isLost()){
                lcount++;
            }
            else{
                fcount++;
            }
        }
        lostcount.setText(lcount+"");
        foundcount.setText(fcount+"");
    }

    @Override
    public void onClick(View view) {
        switch (view.getTag().toString()){
            case "logout":
                if(Backendless.UserService.loggedInUser() != null){
                    loadingDialog.show();
                    Backendless.UserService.logout(new AsyncCallback<Void>() {
                        @Override
                        public void handleResponse(Void response) {
                            myHelper.Toast(getString(R.string.succlogout));
                            mListener.onMenuItemSelected("login","menu",null);
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                    loadingDialog.hide();
                }
                break;
            case "pref":
                mListener.onMenuItemSelected("pref","menu",null);
                break;
            case "lost":
                mListener.onMenuItemSelected("lost","menu",null);
                break;
            case "found":
                mListener.onMenuItemSelected("found","menu",null);
                break;
        }
    }
}
