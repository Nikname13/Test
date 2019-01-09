package com.example.azolotarev.test.Data.Local;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

public class AvatarCache extends LruCache<String,Bitmap> {
    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    private static AvatarCache mAvatarCache;

    public static AvatarCache get(){
        if(mAvatarCache==null) mAvatarCache=new AvatarCache(1024*20124/8);
        return mAvatarCache;
    }

    private AvatarCache(int maxSize) {
        super(maxSize);
    }

    public Bitmap getBitmapFromMemory(String key){
        Log.e("TAG","AvatarCache getBitmapFromMemory");
        return this.get(key);
    }

    public void setBitmapToMemory(String key, Bitmap bitmap){
        if(getBitmapFromMemory(key)==null){
            this.put(key,bitmap);
            Log.e("TAG","AvatarCache setBitmapToMemory");
        }
    }
}
