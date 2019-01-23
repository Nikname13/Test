package com.example.azolotarev.test.Model;

import android.support.annotation.NonNull;
/*
@mGroupPosition стартовая позиция уровня, в которой находится элемент
* */
public class MapModel {

    private BaseModel mModel;
    private int mLevel;
    private boolean mVisible;
    private boolean mSelected;
    private String mId;
    private int mGroupPosition;

    public MapModel(@NonNull BaseModel model, int level, @NonNull boolean visible, int groupPosition){
        mModel=model;
        mLevel=level;
        mVisible=visible;
        mSelected=false;
        mGroupPosition=groupPosition;
        mId=String.valueOf(this.hashCode());
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

    public String getId() {
        return mId;
    }

    public int getGroupPosition() {
        return mGroupPosition;
    }
}
