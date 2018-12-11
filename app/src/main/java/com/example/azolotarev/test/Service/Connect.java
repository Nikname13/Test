package com.example.azolotarev.test.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Connect {

    private static HttpURLConnection connection(String uri, String method) {

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
            ErrorLab.errorMessage();
        }
        return null;
    }

    private static void getJSON(HttpURLConnection connect) {
        try {
            int cod=connect.getResponseCode();
            ErrorLab.errorMessage(String.valueOf(cod));
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
            }
        } catch (IOException ex) {
            ErrorLab.errorMessage(ex.toString());
        }
    }

    public static void get(String uri) {
        HttpURLConnection connect = connection(uri, "GET");
        if (connect != null) {
            ErrorLab.errorMessage(connect.toString());
            getJSON(connect);

        }

    }
}
