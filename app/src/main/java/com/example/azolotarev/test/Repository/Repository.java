package com.example.azolotarev.test.Repository;

import android.content.Context;
import android.os.AsyncTask;
import com.example.azolotarev.test.Service.Connect;
import com.example.azolotarev.test.Service.PersistentStorage;
import com.example.azolotarev.test.Service.URLBuilder;

public  class Repository {
    public static final String URI_HELLO="Hello";
    public static final String URI_GETALL="GetAll";
    public static final String URI_PHOTO="GetWPhoto";
    public static final String PARAM_LOGIN="login";
    public static final String PARAM_PASSWORD="password";
    public static final String PARAM_ID ="id";

    public boolean isAuth(String login, String password){
     new AsyncRequest().execute(new URLBuilder(URI_HELLO).withParam(PARAM_LOGIN, login).withParam(PARAM_PASSWORD, password).build());
     return true;
    }

    public boolean isAuth(Context context){
        PersistentStorage.init(context);
        if(PersistentStorage.getLOGIN().isEmpty()) {
            return false;
        }else{
            return isAuth(PersistentStorage.getLOGIN(), PersistentStorage.getPASSWORD());
        }
    }

    class AsyncRequest extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
           return Connect.get(strings[0]);
        }
    }
}
