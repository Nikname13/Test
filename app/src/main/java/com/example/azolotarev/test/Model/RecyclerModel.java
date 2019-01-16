package com.example.azolotarev.test.Model;

import android.support.annotation.NonNull;

public class RecyclerModel {

    private BaseModel mModel;
    private int mLevel;
    private boolean mVisible;
    private boolean mSelected;

    public RecyclerModel(@NonNull BaseModel model, @NonNull int level, @NonNull boolean visible){
        mModel=model;
        mLevel=level;
        mVisible=visible;
        mSelected=false;
    }

    public BaseModel getModel() {
        return mModel;
    }

    public void setModel(BaseModel model) {
        mModel = model;
    }

    public int getLevel() {
        return mLevel;
    }

    public void setLevel(int level) {
        mLevel = level;
    }

    public boolean isVisible() {
        return mVisible;
    }

    public void setVisible(boolean visible) {
        mVisible = visible;
    }

    public boolean isSelected() {
        return mSelected;
    }

    public void setSelected(boolean selected) {
        mSelected = selected;
    }
}
