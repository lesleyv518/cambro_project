package com.cambro.app.model;

import android.graphics.Bitmap;

import com.parse.ParseFile;

public class PersonalizeCategory {

    String category;
    String kind;
    String dimen;
    String size;
    String objectId;

    public PersonalizeCategory(){}

    public PersonalizeCategory(String kind, String cat)
    {
        this.kind = kind;
        this.category = cat;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getDimen() {
        return dimen;
    }

    public void setDimen(String dimen) {
        this.dimen = dimen;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
