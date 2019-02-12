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
    void isAuth(@NonNull String login,@NonNull String password, @NonNull boolean firstLoad,@NonNull final LoadSuccessCallback callback);
    void loadDepartments(boolean refreshCache, @NonNull boolean firstLoad, @NonNull final LoadDepartmentsCallback callback);
    void loadItem(@NonNull String id, @NonNull final LoadItemCallback callback);
    void loadPhoto(@NonNull String id, int imageWidth, int imageHeight, @NonNull final LoadPhotoCallback callback);
    void refreshCache(@NonNull List<MapModel> mapList);
    void clearCredentials();
}
