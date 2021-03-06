package com.example.azolotarev.test.Service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ScaledBitmap {

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqW, int reqH){
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if(height > reqH || width > reqW){
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) >= reqH
                    && (halfWidth / inSampleSize) >= reqW) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static Bitmap decodeSampleBitmapFromByteArray(byte [] data, int reqW, int reqH){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeByteArray(data,0,data.length, options);
        options.inSampleSize=calculateInSampleSize(options, reqW, reqH);
        options.inJustDecodeBounds=false;
        Bitmap bitmap1=BitmapFactory.decodeByteArray(data,0,data.length, options);
        return bitmap1;
    }
}


