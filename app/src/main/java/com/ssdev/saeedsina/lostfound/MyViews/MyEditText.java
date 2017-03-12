package com.ssdev.saeedsina.lostfound.MyViews;

/**
 * Created by saeed on 22-Aug-16.
 */
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.EditText;


public class MyEditText extends EditText {
    public MyEditText(Context context) {
        super(context);
        setFont();
    }

    private void setFont() {

        Typeface normal = Typeface.createFromAsset(getContext().getAssets(),"fonts/bree.otf");
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
    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putString("text", this.getText().toString());
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            this.setText(bundle.getString("text"));
            super.onRestoreInstanceState(bundle.getParcelable("instanceState"));
            return;
        }
        super.onRestoreInstanceState(state);
    }
}
