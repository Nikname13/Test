package com.example.azolotarev.test.Data.Net;

import android.support.annotation.NonNull;
import com.example.azolotarev.test.BaseCallback;

public interface NetContract {

    interface LoadDepartmentsCallback extends BaseCallback.BaseLoadDepartmentsCallback {}

    interface LoadSuccessCallback{
        void onResponse(String response);
        void connectionError(String errorMessage);
    }
    void isAuth(@NonNull final LoadSuccessCallback callback, @NonNull String login, @NonNull String password);
    void getDepartments(@NonNull final LoadDepartmentsCallback callback, boolean refreshCache);
}
