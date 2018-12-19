package com.example.azolotarev.test.Service;

import android.content.Context;
import android.content.SharedPreferences;

public class PersistentStorage {
    public static final String CREDENTIALS="credentials";
    public static final String LOGIN="login";
    public static final String PASSWORD="password";

    private static SharedPreferences sSettings=null;
    private static SharedPreferences.Editor sEditor=null;
    private static Context sContext =null;

    public static void init(Context context){
        sContext =context;
    }

    private static void init(){
        sSettings=sContext.getSharedPreferences(CREDENTIALS,Context.MODE_PRIVATE);
        sEditor=sSettings.edit();
    }

    public static void addCredentials(String login, String password){
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

    private static String getCredentials(String key) {
        if (sSettings == null) init();
        String s = sSettings.getString(key, "");
        if (s.isEmpty()) {
            ErrorLab.errorMessage();
            return "";
        }
        return s;
    }

    public static void clearCredentials(){
        if(sSettings==null) init();
        sEditor.clear();
        sEditor.commit();
    }
}
