package com.example.azolotarev.test.Data.Net;

import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.util.Log;
import com.example.azolotarev.test.Service.ContextManager;

public class Net implements NetContract {

    private static final String URI_HELLO="Hello";
    private static final String URI_GET_ALL ="GetAll";
    private static final String URI_PHOTO="GetWPhoto";
    private static final String PARAM_LOGIN="login";
    private static final String PARAM_PASSWORD="password";
    private static final String PARAM_ID ="id";
    private ConnectContract mConnect;
    private ConnectivityManager mConnectivityManager;
    private String mLogin,mPassword;

    public Net(ConnectContract connect) {
        mConnect = connect;
        mConnectivityManager=ContextManager.getConnectivityManager();
    }

    @Override
    public void isAuth(@NonNull final LoadSuccessCallback callback) {
        Log.e("TAG", "Net isAuth");
        mConnect.GET(
                new URLBuilder(URI_HELLO).withParam(PARAM_LOGIN, mLogin).withParam(PARAM_PASSWORD, mPassword).build(),
                mConnectivityManager.getActiveNetworkInfo(),
                new ConnectContract.GETCallback() {
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
        });
    }

    @Override
    public void getDepartments(@NonNull final LoadDepartmentsCallback callback, boolean refreshCache) {
        Log.e("TAG", "Net isAuth");
        mConnect.GET(
                new URLBuilder(URI_GET_ALL).withParam(PARAM_LOGIN, mLogin).withParam(PARAM_PASSWORD, mPassword).build(),
                mConnectivityManager.getActiveNetworkInfo(),
                new ConnectContract.GETCallback() {
            @Override
            public void onResponse(String response) {
                callback.onResponse(response);
            }

            @Override
            public void connectionError(String errorMessage) {
                callback.connectionError(errorMessage);
            }
        });
    }

    @Override
    public void getPhoto(@NonNull String id, int imageWidth, int imageHeight, @NonNull final LoadPhotoCallback callback) {
        Log.e("TAG", "Net loadPhoto");
        mConnect.GETPhoto(
                imageWidth,
                imageHeight,
                new URLBuilder(URI_PHOTO)
                        .withParam(PARAM_LOGIN, mLogin)
                        .withParam(PARAM_PASSWORD, mPassword)
                        .withParam(PARAM_ID, id)
                        .build(),
                mConnectivityManager.getActiveNetworkInfo(),
                new ConnectContract.GETPhotoCallback() {
            @Override
            public void onResponse(Bitmap response) {
                callback.onResponse(response);
            }

            @Override
            public void connectionError(String errorMessage) {

            }
        });
    }

    @Override
    public void setCredentials(@NonNull String login, @NonNull String password) {
        mLogin=login;
        mPassword=password;
    }
}
