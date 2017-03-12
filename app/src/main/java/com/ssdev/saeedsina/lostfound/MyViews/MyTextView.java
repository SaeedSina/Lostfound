package com.ssdev.saeedsina.lostfound.MyViews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.rey.material.widget.TextView;

/**
 * Created by saeed on 22-Aug-16.
 */
public class MyTextView extends TextView {
    public MyTextView(Context context) {
        super(context);
        setFont();
    }

    private void setFont() {
        Typeface normal = Typeface.createFromAsset(getContext().getAssets(),"fonts/bree.otf");
        setTypeface( normal, Typeface.NORMAL );
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont();
    }
}
