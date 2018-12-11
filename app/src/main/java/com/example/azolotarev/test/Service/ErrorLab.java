package com.example.azolotarev.test.Service;

import android.util.Log;
import android.widget.Toast;

public class ErrorLab {

    public static void errorMessage() {
        Log.e("TAG", "Какая то ошибка");
    }

    public static void errorMessage(String s) {
        Log.e("TAG", s);
    }
}
