package com.example.azolotarev.test.Data.Local;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.example.azolotarev.test.Service.ContextManager;

public class PersistentStorage {
    public static final String CREDENTIALS="credentials";
    public static final String LOGIN="login";
    public static final String PASSWORD="password";
    public static final String FIRST_LAUNCH="first_launch";

    private static SharedPreferences sSettings=null;
    private static SharedPreferences.Editor sEditor=null;
    private static Context sContext=null;
    private static PersistentStorage mPersistentStorage;

    public static PersistentStorage get(){
        if(mPersistentStorage==null) mPersistentStorage=new PersistentStorage();
        sContext = ContextManager.getContext();
        return mPersistentStorage;
    }

    private PersistentStorage(){}

    private static void init(){
        sSettings=sContext.getSharedPreferences(CREDENTIALS,Context.MODE_PRIVATE);
        sEditor=sSettings.edit();
    }

    public static void addCredentials(String login, String password){
    //    Log.e("TAG", "PersistentStorage addCredentials "+login+" "+password);
        if(sSettings==null) init();
        sEditor.putString(LOGIN, login);
        sEditor.putString(PASSWORD, password);
        sEditor.commit();
    }

    public static String getLOGIN() {
        return getCredentials(LOGIN);
    }

    public static String getPASSWORD() {
        return getCredentials(PASSWORD);
    }

    public static Boolean isFirstLaunch(){
        return getFirstLaunch(FIRST_LAUNCH);
    }

    public static void setFirstLaunch(){
        if(sSettings==null)init();
        sEditor.putBoolean(FIRST_LAUNCH,false);
    }

    private static Boolean getFirstLaunch(String key) {
        if(sSettings == null) init();
        return sSettings.getBoolean(key,true);
    }

    private static String getCredentials(String key) {
        if (sSettings == null) init();
        String s = sSettings.getString(key, "");
        if (s.isEmpty()) {
       //     Log.e("TAG", "PersistentStorage getString is empty");
            return "";
        }
        return s;
    }

    public static void clearCredentials(){
        if(sSettings==null) init();
        sEditor.remove(LOGIN);
        sEditor.remove(PASSWORD);
        sEditor.commit();
    }
}
