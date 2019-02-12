package com.example.azolotarev.test.Data.Net;

import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.util.Log;
import com.example.azolotarev.test.Service.ErrorLab;
import com.example.azolotarev.test.Service.ScaledBitmap;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Connect implements ConnectContract {


    @Override
    public void GET(@NonNull String url,@NonNull NetworkInfo networkInfo, @NonNull final GETCallback callback) {
        if(checkNetwork(networkInfo)) getJSONString(callback,connection(url, "GET"));
        else callback.connectionError("Отсутствует подключение к интернету");
    }

    @Override
    public void GETPhoto(int imageWidth, int imageHeight, @NonNull String url, @NonNull NetworkInfo networkInfo, @NonNull final GETPhotoCallback callback) {
        if(checkNetwork(networkInfo)) getPhotoBitmap(callback,connection(url, "GET"), imageWidth, imageHeight);
        else callback.connectionError("Отсутствует подключение к интернету");
    }

    private HttpURLConnection connection(String uri, String method) {

        try {
            URL url = new URL(uri);
            HttpURLConnection connect = (HttpURLConnection) url.openConnection();
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
                br.close();
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

    private void getPhotoBitmap(@NonNull GETPhotoCallback callback, HttpURLConnection connect, int reqW, int reqH) {
        Log.e("TAG", "Connect getPhotoBitmap");
        try {
            int cod=connect.getResponseCode();
            Log.e("TAG", "connect response cod getPhotoBitmap"+connect.getResponseCode());
            if(cod==HttpURLConnection.HTTP_OK){
                InputStream inputStream=connect.getInputStream();
                ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
                try {
                    int n;
                    byte[] buffer=new byte[1024];
                    while ((n = inputStream.read(buffer))>0){
                        outputStream.write(buffer, 0, n);
                    }
                    callback.onResponse(ScaledBitmap.decodeSampleBitmapFromByteArray(outputStream.toByteArray(), reqW, reqH));
                }catch (IOException ex){
                    ex.printStackTrace();
                }finally {
                    try {
                        inputStream.close();
                        outputStream.close();
                    }catch (IOException ex){
                        ex.printStackTrace();
                    }
                }
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
