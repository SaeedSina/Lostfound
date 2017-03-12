package com.ssdev.saeedsina.lostfound.MyFragments;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.files.BackendlessFile;
import com.backendless.geo.GeoPoint;
import com.squareup.picasso.Picasso;
import com.ssdev.saeedsina.lostfound.MyActivities.MainActivity;
import com.ssdev.saeedsina.lostfound.MyClasses.Item;
import com.ssdev.saeedsina.lostfound.MyClasses.MyApplication;
import com.ssdev.saeedsina.lostfound.MyClasses.MyHelper;
import com.ssdev.saeedsina.lostfound.MyClasses.Tag;
import com.ssdev.saeedsina.lostfound.MyInterfaces.OnMenuItemSelectedListener;
import com.ssdev.saeedsina.lostfound.MyViews.LoadingDialog;
import com.ssdev.saeedsina.lostfound.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;


public class SubmitFoundFragment extends Fragment implements View.OnClickListener {

    private static final int READ_REQUEST_CODE = 42;
    LoadingDialog loadingDialog;

    private static final String TAG = "FOUND_FRAGMENT_FRAGMENT";
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 2;

    private Button btn_browse,btn_locationpicker,btn_found_submit;
    private View myView;
    private EditText txt_date,txt_name,txt_desc,txt_tags,txt_contact;
    private ImageView img;
    private MyApplication mMyApplication;

    OnMenuItemSelectedListener mListener;
    private MyHelper myHelper;

    private Uri selectedImageUri;
    private Bitmap scaled;
    private Bitmap bitmap;
    private GeoPoint selectedLocation;
    private Item draft,toSend;


    Calendar dateSelected = Calendar.getInstance();
    SimpleDateFormat sdf;
    private DatePickerDialog datePickerDialog;

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
    public SubmitFoundFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        myView = inflater.inflate(R.layout.fragment_submit_found, container, false);

        txt_date = (EditText) myView.findViewById(R.id.txt_found_date);
        txt_name = (EditText) myView.findViewById(R.id.txt_found_name);
        txt_desc = (EditText) myView.findViewById(R.id.txt_found_desc);
        txt_tags = (EditText) myView.findViewById(R.id.txt_found_tags);
        txt_contact = (EditText) myView.findViewById(R.id.txt_found_contact);
        btn_browse = (Button) myView.findViewById(R.id.btn_found_imgpicker);
        btn_found_submit = (Button) myView.findViewById(R.id.btn_found_submit);
        ((MainActivity) getActivity()).setActionBarTitle("Submit New");

        btn_found_submit.setTag("submit");
        btn_found_submit.setOnClickListener(this);
        btn_browse.setTag("browse");
        loadingDialog=new LoadingDialog(getContext());
        btn_browse.setOnClickListener(this);
        mMyApplication = (MyApplication)getActivity().getApplication();
        img = (ImageView) myView.findViewById(R.id.img);
        myHelper = new MyHelper(getContext());
        txt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDateTimeField() ;
            }
        });

        btn_locationpicker = (Button) myView.findViewById(R.id.btn_found_locationpicker);
        btn_locationpicker.setTag("location");

        selectedLocation = mMyApplication.getSelectedFoundLocation();
        selectedImageUri = mMyApplication.getPicFoundPath();
        draft = mMyApplication.getFoundDraft();
        populateViews();
        btn_locationpicker.setOnClickListener(this);

        return myView;
    }

    private void populateViews() {
        if(draft != null){
            txt_name.setText(draft.getName());
            txt_desc.setText(draft.getDescription());
            txt_date.setText(draft.getLostDate());
            txt_contact.setText(draft.getContact());
            txt_tags.setText(mMyApplication.getFoundTags());
            if(selectedLocation != null){
                btn_locationpicker.setText(selectedLocation.getLatitude()+","+selectedLocation.getLongitude());
            }
            try {
                Picasso.with(getContext()).load(mMyApplication.getPicFoundPath()).into(img);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void setDateTimeField() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        Calendar newCalendar = dateSelected;
        datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateSelected.set(year, monthOfYear, dayOfMonth, 0, 0);
                txt_date.setText(sdf.format(dateSelected.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getTag().toString()){
            case "browse":
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

                // Filter to only show results that can be "opened", such as a
                // file (as opposed to a list of contacts or timezones)
                intent.addCategory(Intent.CATEGORY_OPENABLE);

                // Filter to show only images, using the image MIME data type.
                // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
                // To search for all documents available via installed storage providers,
                // it would be "*/*".
                intent.setType("image/*");

                startActivityForResult(intent, READ_REQUEST_CODE);

                break;
            case "location":
                Item draft=new Item(txt_date.getText().toString(),txt_desc.getText().toString(),selectedLocation,
                        false,txt_name.getText().toString(),"","",txt_contact.getText().toString());
                mMyApplication.setFoundDraft(draft);
                mMyApplication.setFoundTags(txt_tags.getText().toString());
                mListener.onMenuItemSelected("location","found",null);
                break;
            case "submit":
                Upload_Submit();
                break;
        }
    }


    public void Upload_Submit() {
        if (Validate()) {
            String name = txt_name.getText().toString();
            String desc = txt_desc.getText().toString();
            String date = txt_date.getText().toString();
            String contact = txt_contact.getText().toString();
            Item item2Send = new Item(date, desc, selectedLocation, false, name, "", "",contact);
            String[] tagtext = txt_tags.getText().toString().split(",");
            List<Tag> tags=new ArrayList<>(0);
            for(String s : tagtext){
                Tag d= new Tag(s);
                tags.add(new Tag(s));
            }
            new SubmitFoundFragment.SaveOperation(item2Send,tags).execute();
        }
    }
    private class SaveOperation extends AsyncTask<Void, Void, String> {

        Item item;
        List<Tag> tags;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingDialog.show();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result.equals("OK")){
                loadingDialog.hide();
                myHelper.Toast("Item Saved");
                mListener.onMenuItemSelected("menu","found",null);
            }else if(result.equals("NO")){
                loadingDialog.hide();
                myHelper.Toast("Something went wrong");
            }
        }

        public SaveOperation(Item item, List<Tag> tagstext) {
            this.item = item;
            this.tags = tagstext;
        }

        @Override
        protected String doInBackground(Void... voids) {
            loadingDialog.show();
            String fileName = generateFileName();
            try {
                BackendlessFile f=Backendless.Files.Android.upload(bitmap, Bitmap.CompressFormat.JPEG,100,fileName,"/pics/");
                Bitmap resized = ThumbnailUtils.extractThumbnail(bitmap, 125,125);
                if(f.getFileURL().isEmpty()){
                    return "NO";
                }
                else{
                    List<HashMap> listtags=new ArrayList<>(0);
                    for(Tag t:tags){
                        HashMap tt = new HashMap();
                        tt.put( "___class", "Tag" );
                        tt.put("tagText",t.getTagText());
                        listtags.add(tt);
                    }
                    HashMap item2send = new HashMap();
                    item2send.put( "___class", "Item" );
                    item2send.put( "name", item.getName() );
                    item2send.put( "description", item.getDescription() );
                    item2send.put( "contact", item.getContact() );
                    item2send.put( "picPath", f.getFileURL() );
                    item2send.put( "lostDate", item.getLostDate() );
                    item2send.put( "lost", false );
                    item2send.put( "location", item.getLocation() );
                    item2send.put( "tags",listtags );
                    Object b=Backendless.Persistence.of("Item").save(item2send);
                    if(b.equals(null)){
                        return "NO";
                    }
                    else{
                        return "OK";
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                return "NO";
            }
        }
    }
    private boolean Validate() {
        String name = txt_name.getText().toString();
        String desc = txt_desc.getText().toString();
        String date = txt_date.getText().toString();
        String contact = txt_contact.getText().toString();
        if (!contact.isEmpty()) {
            if (!name.isEmpty()) {
                if (!desc.isEmpty()) {
                    if (!date.isEmpty()) {
                        if (!selectedLocation.equals(null)) {
                            if (selectedImageUri != null) {
                                return true;
                            } else {
                                myHelper.Toast("Please select your Ad Photo");
                            }
                        } else {
                            myHelper.Toast("Please select your Ad Location");
                        }
                    } else {
                        myHelper.Toast("Please enter your Ad Date");
                    }
                } else {
                    myHelper.Toast("Please enter your Ad Description");
                }
            } else {
                myHelper.Toast("Please enter your Ad Name");
            }
        } else {
            myHelper.Toast("Please enter your Ad Contact");
        }
        return false;
    }

    private String generateFileName() {
        String result = Backendless.UserService.loggedInUser();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("-ddMMyyyy-hhmmss");
        result+=simpleDateFormat.format(new Date());
        result+=".jpg";
        return result;

    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this.getContext())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    Upload_Submit();
                } else {
                    // Permission Denied
                    Toast.makeText(this.getContext(), "Storage Access denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == READ_REQUEST_CODE){
            if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                selectedImageUri = data.getData();
                Picasso.with(getContext()).load(selectedImageUri).into(img);
            }
        }
    }
}
