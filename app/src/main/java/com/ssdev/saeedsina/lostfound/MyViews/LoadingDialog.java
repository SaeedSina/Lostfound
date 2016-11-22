package com.ssdev.saeedsina.lostfound.MyViews;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.ssdev.saeedsina.lostfound.R;




public class LoadingDialog {
    Dialog dialog;


    Context context;


    void afterInject() {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.view_loading);
    }


    public void show() {
        dialog.show();
    }


    public void hide() {
        dialog.dismiss();
    }
}