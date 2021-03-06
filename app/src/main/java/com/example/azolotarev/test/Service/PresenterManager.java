package com.example.azolotarev.test.Service;

import android.util.Log;
import com.example.azolotarev.test.UI.BasePresenter;

import java.util.HashMap;
import java.util.Map;

public class PresenterManager {

    private static Map<String,BasePresenter> sPresenterMap;

    public static void addPresenter(BasePresenter presenter,String key){
        if(sPresenterMap==null)sPresenterMap=new HashMap<>();
        sPresenterMap.put(key,presenter);
    }

    public static BasePresenter getPresenter(String key){
        if(sPresenterMap!=null) return sPresenterMap.get(key);
        return null;
    }

    public static void removePresenter(String key){
        sPresenterMap.remove(key);
    }

    public static void print() {
        Log.i("TAG","presenters size " +sPresenterMap.size());
        for(Map.Entry<String,BasePresenter> map :sPresenterMap.entrySet()){
            Log.i("TAG","-!!!- presenter" + map.getKey());
        }
    }
}
