package com.example.azolotarev.test.Data.Net;

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

    @Override
    public void GET(@NonNull GETCallback callback, @NonNull String url, @NonNull NetworkInfo networkInfo) {
        HttpURLConnection connect = connection(url, "GET");
        if (networkInfo!=null && networkInfo.isConnected()) {
            getJSONString(callback,connect);
        }else {
            Log.e("TAG", "Connect GET connect=null");
            callback.connectionError("Отсутствует подключение к интернету");
        }
    }
}