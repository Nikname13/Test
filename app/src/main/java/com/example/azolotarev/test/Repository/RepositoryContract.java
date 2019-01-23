package com.example.azolotarev.test.Repository;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.example.azolotarev.test.BaseCallback;
import com.example.azolotarev.test.Model.MapModel;

import java.util.List;

public interface RepositoryContract {

    interface LoadDepartmentsCallback extends BaseCallback.BaseLoadDepartmentsCallback, BaseCallback.BaseErrorCallback {
        void onMapListLoaded(@NonNull List<MapModel> list);
    }
    interface LoadSuccessCallback extends BaseCallback.BaseErrorCallback{
        void onSuccess(boolean success);
    }
    interface LoadPhotoCallback extends BaseCallback.BaseErrorCallback {
        void onResponse(@NonNull Bitmap photo);
    }
    interface LoadItemCallback extends BaseCallback.BaseGetItemCallback{
    }
    void isAuth(@NonNull final LoadSuccessCallback callback, @NonNull String login,@NonNull String password, @NonNull boolean firstLoad);
    void getDepartments(@NonNull final LoadDepartmentsCallback callback, boolean refreshCache, @NonNull boolean firstLoad);
    void getItem(@NonNull final LoadItemCallback callback, @NonNull String id );
    void getPhoto(@NonNull final LoadPhotoCallback callback, @NonNull String id);
    void refreshCache(@NonNull List<MapModel> mapList);
    void clearCredentials();
}
