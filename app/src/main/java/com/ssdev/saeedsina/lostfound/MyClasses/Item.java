package com.ssdev.saeedsina.lostfound.MyClasses;

import com.backendless.Backendless;
import com.backendless.geo.GeoPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saeedek on 16-Jan-17.
 */

public class Item {
    private String name,description,picPath,reward, lostDate,objectId,contact,ownerId;
    private GeoPoint location;
    private boolean lost;
    private List<Tag> tags;

    public Item() {
    }

    public Item(String date, String description, GeoPoint location, boolean lost, String name, String picPath, String reward,String contact) {
        this.lostDate = date;
        this.description = description;
        this.location = location;
        this.lost = lost;
        this.name = name;
        this.picPath = picPath;
        this.reward = reward;
        this.contact = contact;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getLostDate() {
        return lostDate;
    }

    public void setLostDate(String lostDate) {
        this.lostDate = lostDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public boolean isLost() {
        return lost;
    }

    public void setLost(boolean lost) {
        this.lost = lost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(ArrayList<Tag> tags) {
        this.tags = tags;
    }

    public void addTag( Tag tag )
    {
        if( tags == null )
            tags = new ArrayList<Tag>();
        tags.add( tag );
    }
     public void addAllTag(List<Tag> tagList){
         if(tags == null)
             tags = new ArrayList<Tag>();
         tags.addAll(tagList);
     }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
}
