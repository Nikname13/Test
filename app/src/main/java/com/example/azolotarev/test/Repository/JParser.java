package com.example.azolotarev.test.Repository;

import android.support.annotation.NonNull;
import android.util.Log;
import com.example.azolotarev.test.Model.DepartmentModel;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JParser implements JParserContract {

    @Override
    public void getDepartments(@NonNull ParsDepartmentsCallback callback, String jsonString) {
        callback.onDepartmentsLoaded(parsJson(jsonString));
    }

    @Override
    public void getSuccess(@NonNull ParsSuccessCallback callback, String jsonString) {
        try {
            JSONObject json=new JSONObject(jsonString);
            if(!json.getString("Message").equals("null")) callback.errorSuccess(json.getString("Message"));
            callback.onSuccess(json.getBoolean("Success"));
        } catch (JSONException e) {
            Log.e("TAG",e.getMessage());
            callback.onSuccess(false);
            callback.errorSuccess(e.getMessage());
        }
    }

    private List<DepartmentModel> parsJson(String jsonString) {
        List<DepartmentModel> list=new ArrayList<>();
        for(int i=0;i<10;i++){
            list.add(new DepartmentModel(i,"Отдел "+i));
        }
        return list;
    }
}
