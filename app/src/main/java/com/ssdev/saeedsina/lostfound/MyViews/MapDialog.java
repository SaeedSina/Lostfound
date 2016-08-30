package com.ssdev.saeedsina.lostfound.MyViews;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.view.Window;

import com.google.android.gms.maps.MapFragment;
import com.ssdev.saeedsina.lostfound.R;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;

@EBean
public class MapDialog  {
    Dialog dialog;

    @RootContext
    Context context;

    @AfterInject
    void afterInject() {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.activity_map);
        MapFragment fragment = new MapFragment();
        FragmentTransaction transaction = dialog.getOwnerActivity().getFragmentManager().beginTransaction();
        transaction.add(R.id.mapcontainer, fragment).commit();
    }

    @UiThread
    public void show() {
        dialog.show();
    }

    @UiThread
    public void hide() {
        dialog.dismiss();
    }
}
