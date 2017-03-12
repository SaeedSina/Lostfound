package com.ssdev.saeedsina.lostfound.MyClasses;

import android.app.Application;
import android.net.Uri;

import com.backendless.Backendless;
import com.backendless.geo.GeoPoint;
import com.google.android.gms.maps.model.LatLng;


/**
 * Created by saeed on 22-Aug-16.
 */

public class MyApplication extends Application {

    private GeoPoint selectedLostLocation,selectedFoundLocation,queryLocation;
    private Item lostDraft,foundDraft,forDetail;
    private Uri picLostPath,picFoundPath;
    private String lostTags,foundTags;
    private MyQuery query;
    public void onCreate() {
        super.onCreate();
        // Initializing backtory
        Backendless.initApp( this, "BC9120DC-3E6F-DE66-FF91-199819BBAD00", "B45AC1A2-B82A-BF02-FFE5-A1BAB4D0DA00", "v1" );
    }

    public GeoPoint getQueryLocation() {
        return queryLocation;
    }

    public void setQueryLocation(GeoPoint queryLocation) {
        this.queryLocation = queryLocation;
    }

    public MyQuery getQuery() {
        return query;
    }

    public void setQuery(MyQuery query) {
        this.query = query;
    }

    public Item getFoundDraft() {
        return foundDraft;
    }

    public void setFoundDraft(Item foundDraft) {
        this.foundDraft = foundDraft;
    }

    public Item getLostDraft() {
        return lostDraft;
    }

    public void setLostDraft(Item lostDraft) {
        this.lostDraft = lostDraft;
    }

    public Uri getPicFoundPath() {
        return picFoundPath;
    }

    public void setPicFoundPath(Uri picFoundPath) {
        this.picFoundPath = picFoundPath;
    }

    public Uri getPicLostPath() {
        return picLostPath;
    }

    public void setPicLostPath(Uri picLostPath) {
        this.picLostPath = picLostPath;
    }

    public GeoPoint getSelectedFoundLocation() {
        return selectedFoundLocation;
    }

    public void setSelectedFoundLocation(GeoPoint selectedFoundLocation) {
        this.selectedFoundLocation = selectedFoundLocation;
    }

    public GeoPoint getSelectedLostLocation() {
        return selectedLostLocation;
    }

    public void setSelectedLostLocation(GeoPoint selectedLostLocation) {
        this.selectedLostLocation = selectedLostLocation;
    }

    public String getLostTags() {
        return lostTags;
    }

    public void setLostTags(String lostTags) {
        this.lostTags = lostTags;
    }

    public String getFoundTags() {
        return foundTags;
    }

    public void setFoundTags(String foundTags) {
        this.foundTags = foundTags;
    }

    public Item getForDetail() {
        return forDetail;
    }

    public void setForDetail(Item forDetail) {
        this.forDetail = forDetail;
    }
}
