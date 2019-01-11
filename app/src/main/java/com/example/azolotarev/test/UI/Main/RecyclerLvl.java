package com.example.azolotarev.test.UI.Main;

import android.util.Log;
import com.example.azolotarev.test.Model.BaseModel;

import java.util.ArrayList;
import java.util.List;

public class RecyclerLvl {
    private static List<BaseModel> mLvlList=new ArrayList<>();

    public static void setRecyclerLvl(BaseModel model){
        Log.d("TAG","set lvl");
        mLvlList.clear();
        setLvl(model);
        print();
    }

    private static void setLvl(BaseModel model) {
        if(model.getParent()!=null){
            mLvlList.add(model);
            setLvl(model.getParent());
        }
    }

    public static void removeLvl(BaseModel model){
        Log.d("TAG","remove lvl");
        if(mLvlList.contains(model)) {
            for (BaseModel baseModel : mLvlList) {
                if (baseModel.getId() != model.getId()) mLvlList.remove(baseModel);
                else {
                    mLvlList.remove(baseModel);
                    print();
                    return;
                }
            }
        }
        print();
    }

    private static void print(){
        Log.d("TAG","recycler lvl print list size = "+mLvlList.size());
        for(BaseModel model:mLvlList) {
            Log.d("TAG","recycler lvl print = "+String.valueOf(model.getId()));
        }
    }
}
