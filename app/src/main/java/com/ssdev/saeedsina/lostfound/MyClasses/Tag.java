package com.ssdev.saeedsina.lostfound.MyClasses;

/**
 * Created by Saeedek on 21-Jan-17.
 */

public class Tag {
    private String tagText,objectId;

    public Tag() {
    }

    public Tag(String tagText){
        this.tagText = tagText;
    }

    public String getTagText() {
        return tagText;
    }

    public void setTagText(String tagText) {
        this.tagText = tagText;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
