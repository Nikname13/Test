package com.example.azolotarev.test.Data.Net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v4.net.ConnectivityManagerCompat;
import android.util.Log;
import com.example.azolotarev.test.Service.ErrorLab;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Connect implements ConnectContract {


    @Override
    public void GET(@NonNull GETCallback callback, @NonNull String url, @NonNull NetworkInfo networkInfo) {
        if(checkNetwork(networkInfo)) getJSONString(callback,connection(url, "GET"));
        else callback.connectionError("Отсутствует подключение к интернету");
    }

    @Override
    public void GETPhoto(@NonNull GETPhotoCallback callback, @NonNull String url, @NonNull NetworkInfo networkInfo) {
        if(checkNetwork(networkInfo)) getPhotoBitmap(callback,connection(url, "GET"));
        else callback.connectionError("Отсутствует подключение к интернету");
    }

    private HttpURLConnection connection(String uri, String method) {

        try {
            URL url = new URL(uri);
            HttpURLConnection connect = (HttpURLConnection) url.openConnection();
/*            connect.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connect.setUseCaches(true);
            connect.setAllowUserInteraction(true);
            connect.setDoOutput(true);
            connect.setDoInput(true);
            connect.setRequestMethod(method);*/

            return connect;
        } catch (IOException ex) {
            Log.e("TAG", "Connect exception connection"+ex);
            ErrorLab.errorMessage();
        }
        return null;
    }

    private void getJSONString(@NonNull GETCallback callback, HttpURLConnection connect) {
        Log.e("TAG", "Connect getJSON");
        try {
            int cod=connect.getResponseCode();
            Log.e("TAG", "connect response cod getJSON"+connect.getResponseCode());
            if(cod==HttpURLConnection.HTTP_OK){
                //InputStream in=new BufferedInputStream(connect.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(connect.getInputStream(), StandardCharsets.UTF_8));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                ErrorLab.errorMessage(sb.toString());
                connect.disconnect();
                callback.onResponse(sb.toString());
            }else{
                Log.e("TAG", "connect ошибка подключения getJSON"+connect.getResponseCode());
                callback.connectionError("Ошибка подключения "+connect.getResponseCode());
            }
        } catch (IOException ex) {
            Log.e("TAG", "Connect exception connection getJSON"+ex);
            ErrorLab.errorMessage(ex.toString());
        }
    }

    private void getPhotoBitmap(@NonNull GETPhotoCallback callback, HttpURLConnection connect) {
        Log.e("TAG", "Connect getPhotoBitmap");
        try {
            int cod=connect.getResponseCode();
            Log.e("TAG", "connect response cod getPhotoBitmap"+connect.getResponseCode());
            if(cod==HttpURLConnection.HTTP_OK){
                InputStream inputStream=connect.getInputStream();
                callback.onResponse(BitmapFactory.decodeStream(inputStream));
            }else{
                Log.e("TAG", "connect ошибка подключения getPhotoBitmap"+connect.getResponseCode());
                callback.connectionError("Ошибка подключения "+connect.getResponseCode());
            }
        } catch (IOException ex) {
            Log.e("TAG", "Connect exception connection getJSON"+ex);
            ErrorLab.errorMessage(ex.toString());
        }
    }

    private boolean checkNetwork( @NonNull NetworkInfo networkInfo){
        if (networkInfo!=null && networkInfo.isConnected()) {
            return true;
        }else {
            Log.e("TAG", "Connect GET connect=null");
            return false;
        }
    }
}
