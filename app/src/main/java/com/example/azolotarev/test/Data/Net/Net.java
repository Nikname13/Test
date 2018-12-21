package com.example.azolotarev.test.Data.Net;

import android.support.annotation.NonNull;
import android.util.Log;

public class Net implements NetContract {

    private static final String URI_HELLO="Hello";
    private static final String URI_GETALL="GetAll";
    private static final String URI_PHOTO="GetWPhoto";
    private static final String PARAM_LOGIN="login";
    private static final String PARAM_PASSWORD="password";
    private static final String PARAM_ID ="id";
    private ConnectContract mConnect;

    public Net(ConnectContract connect) {
        mConnect = connect;
    }

    @Override
    public void isAuth(@NonNull final LoadSuccessCallback callback, @NonNull String login, @NonNull String password) {
        Log.e("TAG", "Net isAuth");
        mConnect.GET(new ConnectContract.GETCallback() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "Net GET onResponse");
                callback.onResponse(response);
            }

            @Override
            public void connectionError(String errorMessage) {
                Log.e("TAG", "Net GET connectionError");
                callback.connectionError(errorMessage);
            }
        }, new URLBuilder(URI_HELLO).withParam(PARAM_LOGIN, login).withParam(PARAM_PASSWORD, password).build());
    }

    @Override
    public void getDepartments(@NonNull LoadDepartmentsCallback callback, boolean refreshCache) {

    }
}
