package com.ssdev.saeedsina.lostfound.MyFragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.backendless.geo.GeoPoint;
import com.ssdev.saeedsina.lostfound.MyActivities.MainActivity;
import com.ssdev.saeedsina.lostfound.MyClasses.MyApplication;
import com.ssdev.saeedsina.lostfound.MyClasses.MyHelper;
import com.ssdev.saeedsina.lostfound.MyClasses.MyQuery;
import com.ssdev.saeedsina.lostfound.MyInterfaces.OnMenuItemSelectedListener;
import com.ssdev.saeedsina.lostfound.MyViews.LoadingDialog;
import com.ssdev.saeedsina.lostfound.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PreferencesFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {


    private MyApplication mMyApplication;
    private OnMenuItemSelectedListener mListener;
    private MyHelper myHelper;
    private LoadingDialog loadingDialog;
    private Button btn_pref_search,btn_pref_locationselect;
    private EditText txt_pref_tags,txt_pref_desc;
    private RadioButton rd_pref_desc,rd_pref_tags,rd_pref_location;
    private GeoPoint location;
    private MyQuery myQuery;
    public PreferencesFragment() {
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
    public void onClick(View view) {
        switch (view.getTag().toString()){
            case "location":
                mListener.onMenuItemSelected("location","pref",null);
                break;
            case "search":
                saveQuery();
                mListener.onMenuItemSelected("adlist","pref",null);
                break;
        }
    }

    private void saveQuery() {
        if (validate()) {
            mMyApplication.setQuery(myQuery);
        }
    }

    private boolean validate() {
        myQuery = new MyQuery();
        if (rd_pref_desc.isChecked()) {
            if(txt_pref_desc.getText().toString().isEmpty()){
                myHelper.Toast("Please enter description or uncheck it");
                return false;
            }
           else{
                myQuery.setDesc(txt_pref_desc.getText().toString());
                myQuery.setType(1);
                return true;
            }

        } else if (rd_pref_location.isChecked()) {
            if(mMyApplication.getQueryLocation() == null){
                myHelper.Toast("Please select location or uncheck it");
                return false;
            }
            else{
                myQuery.setLocation(mMyApplication.getQueryLocation());
                myQuery.setType(3);
                return true;
            }

        } else if (rd_pref_tags.isChecked()) {
            if(txt_pref_tags.getText().toString().isEmpty()){
                myHelper.Toast("Please enter tags or uncheck it");
                return false;
            }
            else{
                myQuery.setTags(txt_pref_tags.getText().toString());
                myQuery.setType(2);
                return true;
            }
        }
        else{
            myQuery.setType(0);
            return true;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView =  inflater.inflate(R.layout.fragment_preferences, container, false);
        btn_pref_search = (Button) myView.findViewById(R.id.btn_pref_submit);
        btn_pref_search.setOnClickListener(this);
        btn_pref_search.setTag("search");

        btn_pref_locationselect = (Button) myView.findViewById(R.id.btn_pref_selectlocation);
        btn_pref_locationselect.setTag("location");
        btn_pref_locationselect.setOnClickListener(this);

        txt_pref_desc = (EditText) myView.findViewById(R.id.txt_pref_desc);
        txt_pref_tags = (EditText) myView.findViewById(R.id.txt_pref_tags);

        rd_pref_desc = (RadioButton) myView.findViewById(R.id.rd_pref_desc);
        rd_pref_tags = (RadioButton) myView.findViewById(R.id.rd_pref_tags);
        rd_pref_location = (RadioButton) myView.findViewById(R.id.rd_pref_location);

        rd_pref_desc.setOnCheckedChangeListener(this);
        rd_pref_desc.setTag("desc");
        ((MainActivity) getActivity()).setActionBarTitle("Search");

        rd_pref_tags.setOnCheckedChangeListener(this);
        rd_pref_tags.setTag("tags");

        rd_pref_location.setOnCheckedChangeListener(this);
        rd_pref_location.setTag("location");

        mMyApplication = (MyApplication)getActivity().getApplication();
        myHelper = new MyHelper(getContext());

        if(mMyApplication.getQueryLocation() != null){
            btn_pref_locationselect.setText(mMyApplication.getQueryLocation().getLatitude()+","+mMyApplication.getQueryLocation().getLongitude());
            rd_pref_location.setChecked(true);
        }

        return myView;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(b){
            switch (compoundButton.getTag().toString()){
                case "desc":
                    txt_pref_desc.setEnabled(b);
                    rd_pref_location.setChecked(false);
                    rd_pref_tags.setChecked(false);
                    break;
                case "tags":
                    txt_pref_tags.setEnabled(b);
                    rd_pref_location.setChecked(false);
                    rd_pref_desc.setChecked(false);
                    break;
                case "location":
                    btn_pref_locationselect.setEnabled(b);
                    rd_pref_desc.setChecked(false);
                    rd_pref_tags.setChecked(false);
                    break;
            }
        }
        else if(!b){
            switch (compoundButton.getTag().toString()){
                case "desc":
                    txt_pref_desc.setEnabled(b);
                    break;
                case "tags":
                    txt_pref_tags.setEnabled(b);
                    break;
                case "location":
                    btn_pref_locationselect.setEnabled(b);
                    break;
            }
        }

    }
}
