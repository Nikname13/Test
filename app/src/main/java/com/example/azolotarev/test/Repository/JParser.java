package com.example.azolotarev.test.Repository;

import android.support.annotation.NonNull;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

public class JParser implements JParserContract {

    @Override
    public boolean getSuccess(@NonNull String jsonString) {
        try {
            JSONObject json=new JSONObject(jsonString);
            return json.getBoolean("Success");
        } catch (JSONException e) {
            Log.e("TAG",e.getMessage());
            return false;
        }

    }
}
