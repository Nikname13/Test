package com.example.azolotarev.test.Iteractor;

import android.os.AsyncTask;
import android.util.Log;
import com.example.azolotarev.test.Service.Connect;
import com.example.azolotarev.test.Service.URLBuilder;

public  class Iteractor {
    public static final String URI_HELLO="Hello";
    public static final String URI_GETALL="GetAll";
    public static final String URI_PHOTO="GetWPhoto";

    public boolean isAuth(String login, String password){
    new AsyncRequest().execute(login, password);
     if(login.equals("user_test")) return true;
     return false;
    }

    class AsyncRequest extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... arg) {
            Connect.get(new URLBuilder(URI_HELLO).withParam("login", arg[0]).withParam("password", arg[1]).build());
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
