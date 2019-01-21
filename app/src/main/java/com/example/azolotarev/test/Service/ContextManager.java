package com.example.azolotarev.test.Service;

import android.content.Context;
import android.net.ConnectivityManager;

public class ContextManager {

    private static Context sContext;
    private static ConnectivityManager sConnectivityManager;

    public static void init(Context context){
        sContext=context;
        sConnectivityManager= (ConnectivityManager) sContext.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public static Context getContext() {
        return sContext;
    }
    public static ConnectivityManager getConnectivityManager() {
        return sConnectivityManager;
    }
}
