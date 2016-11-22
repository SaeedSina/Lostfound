package com.ssdev.saeedsina.lostfound.MyActivities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;

import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;


public class SubmitLostActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    void btnDatePicker_Clicked(){
        PersianCalendar persianCalendar = new PersianCalendar();
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                SubmitLostActivity.this,
                persianCalendar.getPersianYear(),
                persianCalendar.getPersianMonth(),
                persianCalendar.getPersianDay()
        );
        datePickerDialog.setThemeDark(true);
        datePickerDialog.show(getFragmentManager(), "Datepickerdialog");
    }


    void btnBrowse_Clicked(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    void onResult(int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null && data.getData() != null) {

        }
    }

    private String getPath(Uri uri) {
        if( uri == null ) {
            //myHelper.toast("Bad Picture");
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // this is our fallback here
        return uri.getPath();
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int monthOfYear, int dayOfMonth) {
        String date="You picked the following date: "+dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
       // myHelper.toast(date);
        date = new StringBuilder(dayOfMonth+"/"+(monthOfYear+1)+"/"+year).reverse().toString();
        //txtDate.setText(date);

    }
}
