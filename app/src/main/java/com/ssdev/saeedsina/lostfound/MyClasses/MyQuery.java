package com.ssdev.saeedsina.lostfound.MyClasses;

import com.backendless.geo.GeoPoint;

/**
 * Created by Saeedek on 22-Jan-17.
 */

public class MyQuery {
    String desc;
    String tags;
    GeoPoint location;
    int type;  //0 all,1 desc,2 tags,3 location

    public MyQuery() {
    }

    public MyQuery(String desc, String tags, GeoPoint location,int type) {
        this.desc = desc;
        this.tags = tags;
        this.location = location;
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
