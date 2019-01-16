package com.example.azolotarev.test.Repository;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.example.azolotarev.test.BaseCallback;
import com.example.azolotarev.test.Model.DepartmentModel;
import com.example.azolotarev.test.Model.RecyclerModel;

import java.util.List;

public interface RepositoryContract {

    interface LoadDepartmentsCallback extends BaseCallback.BaseLoadDepartmentsCallback, BaseCallback.BaseErrorCallback {
        void onMapListLoaded(List<RecyclerModel> list);
    }
    interface LoadSuccessCallback extends BaseCallback.BaseErrorCallback{
        void onSuccess(boolean success);
    }
    interface LoadPhotoCallback extends BaseCallback.BaseErrorCallback {
        void onResponse(Bitmap photo);
    }
    interface LoadItemCallback extends BaseCallback.BaseGetItemCallback{
    }
    void isAuth(@NonNull final LoadSuccessCallback callback, @NonNull String login,@NonNull String password, @NonNull boolean firstLoad);
    void getDepartments(@NonNull final LoadDepartmentsCallback callback, boolean refreshCache, @NonNull boolean firstLoad);
    void getItem(@NonNull final LoadItemCallback callback, @NonNull int id );
    void getPhoto(@NonNull final LoadPhotoCallback callback, @NonNull int id);
    void refreshCache(@NonNull List<RecyclerModel> mapList);
}
