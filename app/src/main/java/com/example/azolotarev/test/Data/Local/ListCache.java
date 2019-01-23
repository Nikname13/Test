package com.example.azolotarev.test.Data.Local;

import android.util.Log;
import com.example.azolotarev.test.Model.MapModel;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ListCache {
    static Map<String,MapModel> mCachedList;

    public static Map<String, MapModel> getCachedList() {
        return mCachedList;
    }

    public static void setCachedList(List<MapModel> mapList) {
        if(mCachedList ==null){
            mCachedList=new LinkedHashMap<>();
        }
        mCachedList.clear();
        for(MapModel model:mapList) {
            mCachedList.put(model.getId(), model);
        }
        Log.d("TAG","mCachedList size"+mCachedList.size());
    }

}
