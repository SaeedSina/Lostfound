package com.ssdev.saeedsina.lostfound.MyViews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.rey.material.widget.Button;

/**
 * Created by saeed on 19-Aug-16.
 */
public class MyButton extends Button {

    public MyButton(Context context) {
        super(context);
        setFont();
    }

    private void setFont() {
        Typeface normal = Typeface.createFromAsset(getContext().getAssets(),"fonts/iransans.ttf");
        setTypeface( normal, Typeface.NORMAL );
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont();
    }
}
