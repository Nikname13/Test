package com.example.azolotarev.test.Model;

import android.support.annotation.NonNull;

public class BaseModel {

   private int mId;
   private String mName;

    public BaseModel(@NonNull int id,@NonNull String name) {
        mId = id;
        mName = name;
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
