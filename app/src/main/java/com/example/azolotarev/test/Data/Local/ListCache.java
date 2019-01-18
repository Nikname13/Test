package com.example.azolotarev.test.Data.Local;

import android.util.Log;
import com.example.azolotarev.test.Model.RecyclerModel;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ListCache {
    static Map<String,RecyclerModel> mCachedList;

    public static Map<String, RecyclerModel> getCachedList() {
        return mCachedList;
    }

    public static void setCachedList(List<RecyclerModel> mapList) {
        if(mCachedList ==null){
            mCachedList=new LinkedHashMap<>();
        }
        mCachedList.clear();
        for(RecyclerModel model:mapList) {
            mCachedList.put(model.getId(), model);
        }
        Log.d("TAG","mCachedList size"+mCachedList.size());
    }

}
