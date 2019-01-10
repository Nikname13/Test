package com.example.azolotarev.test.Model;

import android.support.annotation.NonNull;

public class BaseModel {

    @SerializedNameField(name = "ID")
   private int mId;
    @SerializedNameField(name = "Name")
   private String mName;

    public BaseModel(@NonNull int id,@NonNull String name) {
        mId = id;
        mName = name;
    }

    public BaseModel() {
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
