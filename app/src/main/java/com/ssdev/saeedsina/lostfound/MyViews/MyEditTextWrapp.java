package com.ssdev.saeedsina.lostfound.MyViews;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import com.wrapp.floatlabelededittext.FloatLabeledEditText;

/**
 * Created by saeed on 22-Aug-16.
 */
public class MyEditTextWrapp extends FloatLabeledEditText {
    public MyEditTextWrapp(Context context) {
        super(context);
        setFont();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setFont() {
        TextView tvHint = (TextView) getChildAt(0);

        Typeface normal = Typeface.createFromAsset(getContext().getAssets(),"fonts/iransans.ttf");
        tvHint.setTypeface( normal, Typeface.NORMAL );

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 20, 0);
        tvHint.setLayoutParams(layoutParams);
        tvHint.setGravity(Gravity.RIGHT);
    }

    public MyEditTextWrapp(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public MyEditTextWrapp(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFont();
    }
}
