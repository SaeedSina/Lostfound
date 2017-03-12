package com.ssdev.saeedsina.lostfound.MyFragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ssdev.saeedsina.lostfound.MyActivities.MainActivity;
import com.ssdev.saeedsina.lostfound.MyClasses.Item;
import com.ssdev.saeedsina.lostfound.MyClasses.MyApplication;
import com.ssdev.saeedsina.lostfound.MyClasses.MyHelper;
import com.ssdev.saeedsina.lostfound.MyInterfaces.OnMenuItemSelectedListener;
import com.ssdev.saeedsina.lostfound.MyViews.LoadingDialog;
import com.ssdev.saeedsina.lostfound.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment implements View.OnClickListener{

    private LoadingDialog loadingDialog;
    private View myView;
    private MyApplication mMyApplication;
    private OnMenuItemSelectedListener mListener;
    private MyHelper myHelper;

    private ImageView imgView;
    private TextView txt_detail_name,txt_detail_desc,txt_detail_date,txt_detail_reward,txt_detail_contact;

    private Item item;

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

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView =  inflater.inflate(R.layout.fragment_detail, container, false);
        loadingDialog=new LoadingDialog(getContext());
        mMyApplication = (MyApplication)getActivity().getApplication();
        myHelper = new MyHelper(getContext());

        item = mMyApplication.getForDetail();

        imgView = (ImageView) myView.findViewById(R.id.img_detail);
        txt_detail_name = (TextView) myView.findViewById(R.id.txt_detail_name);
        txt_detail_desc = (TextView) myView.findViewById(R.id.txt_detail_desc);
        txt_detail_date = (TextView) myView.findViewById(R.id.txt_detail_date);
        txt_detail_reward = (TextView) myView.findViewById(R.id.txt_detail_reward);
        txt_detail_contact = (TextView) myView.findViewById(R.id.txt_detail_contact);
        ((MainActivity) getActivity()).setActionBarTitle("Details");

        populateViews();

        return myView;
    }

    private void populateViews() {
        Picasso.with(getContext()).load(item.getPicPath()).into(imgView);
        txt_detail_name.setText(item.getName());
        txt_detail_desc.setText(item.getDescription());
        txt_detail_date.setText(item.getLostDate());
        txt_detail_reward.setText(item.getReward());
        txt_detail_contact.setText(item.getContact());
    }

    @Override
    public void onClick(View view) {

    }

}
