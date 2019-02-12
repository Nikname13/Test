package com.example.azolotarev.test.Data.Net;

import android.graphics.Bitmap;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

public interface ConnectContract {

    interface GETCallback{
        void onResponse(String response);
        void connectionError(String errorMessage);
    }

    interface GETPhotoCallback{
        void onResponse(Bitmap response);
        void connectionError(String errorMessage);
    }
    void GET( @NonNull String url,@NonNull NetworkInfo networkInfo, @NonNull final GETCallback callback);
    void GETPhoto(int imageWidth, int imageHeight, @NonNull String url,@NonNull NetworkInfo networkInfo, @NonNull final GETPhotoCallback callback);
}
