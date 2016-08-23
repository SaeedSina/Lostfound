package com.ssdev.saeedsina.lostfound.MyViews;

/**
 * Created by saeed on 22-Aug-16.
 */
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;


public class MyEditText extends EditText {
    public MyEditText(Context context) {
        super(context);
        setFont();
    }

    private void setFont() {

        Typeface normal = Typeface.createFromAsset(getContext().getAssets(),"fonts/iransans.ttf");
        setTypeface( normal, Typeface.NORMAL );

    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFont();
    }
}
