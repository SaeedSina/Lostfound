package com.ssdev.saeedsina.lostfound.MyActivities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import com.ssdev.saeedsina.lostfound.MyClasses.MyHelper;
import com.ssdev.saeedsina.lostfound.MyViews.LoadingDialog;
import com.ssdev.saeedsina.lostfound.MyViews.MapDialog;
import com.ssdev.saeedsina.lostfound.R;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WindowFeature;

import java.io.IOException;

@EActivity(R.layout.activity_submit_lost)
@WindowFeature(Window.FEATURE_NO_TITLE)
public class SubmitLostActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private static final String TAG = "SubmitLostActivity";
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedImageUri;
    private String selectedImagePath;
    private Bitmap scaled;

    @ViewById(R.id.etext_lostsubmit_date)
    EditText txtDate;


    @ViewById(R.id.btn_datepicker)
    Button btnDatePicker;

    @Bean
    MapDialog mapDialog;

    @Click(R.id.btn_datepicker)
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

    @ViewById(R.id.btn_locationpicker)
    Button btnLocationPicker;

    @Click(R.id.btn_locationpicker)
    void btnLocationPicker_Clicked(){
        mapDialog.show();
    }

    @Bean
    MyHelper myHelper;

    @Bean
    LoadingDialog loadingDialog;

    @ViewById(R.id.btn_lostbrowse)
    Button btnBrowse;

    @ViewById(R.id.btn_lostsubmit)
    Button btnLostSubmit;

    @ViewById(R.id.img)
    ImageView img;

    @Click(R.id.btn_lostsubmit)
    void btnLostSubmit_Clicked(){

    }

    @Click(R.id.btn_lostbrowse)
    void btnBrowse_Clicked(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @OnActivityResult(PICK_IMAGE_REQUEST)
    void onResult(int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            myHelper.toast(selectedImageUri.getPath());
            //img.setImageURI(selectedImageUri);
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            int nh = (int) ( bitmap.getHeight() * (512.0 / bitmap.getWidth()) );
            scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
            img.setImageBitmap(scaled);
        }
    }

    private String getPath(Uri uri) {
        if( uri == null ) {
            myHelper.toast("Bad Picture");
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
        myHelper.toast(date);
        date = new StringBuilder(dayOfMonth+"/"+(monthOfYear+1)+"/"+year).reverse().toString();
        txtDate.setText(date);

    }
}
