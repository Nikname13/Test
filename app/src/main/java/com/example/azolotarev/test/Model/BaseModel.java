package com.example.azolotarev.test.Model;

import android.support.annotation.NonNull;

public class BaseModel {

    private String mId;
    private String mName;
    private DepartmentModel mParent;


    public BaseModel(@NonNull String id,@NonNull String name) {
        mId = id;
        mName = name;
    }

    public BaseModel() {
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public DepartmentModel getParent() {
        return mParent;
    }

    public void setParent(DepartmentModel parent) {
        mParent = parent;
    }
}
