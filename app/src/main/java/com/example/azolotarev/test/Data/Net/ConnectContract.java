package com.example.azolotarev.test.Data.Net;

import android.net.NetworkInfo;
import android.support.annotation.NonNull;

public interface ConnectContract {

    interface GETCallback{
        void onResponse(String response);
        void connectionError(String errorMessage);
    }
    void GET(@NonNull final GETCallback callback, @NonNull String url,@NonNull NetworkInfo networkInfo);
}
