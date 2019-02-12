package com.example.azolotarev.test.Data.Net;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.example.azolotarev.test.BaseCallback;

public interface NetContract {

    interface LoadDepartmentsCallback extends LoadSuccessCallback{
    }
    interface LoadSuccessCallback {
        void onResponse(String response);

        void connectionError(String errorMessage);
    }
    interface  LoadPhotoCallback{
        void onResponse(Bitmap response);
        void connectionError(String errorMessage);
    }
    void isAuth(@NonNull final LoadSuccessCallback callback);
    void getDepartments(@NonNull final LoadDepartmentsCallback callback, boolean refreshCache);
    void getPhoto(@NonNull String id, int imageWidth, int imageHeight, @NonNull final LoadPhotoCallback callback);
    void setCredentials(@NonNull String login, @NonNull String password);
}
