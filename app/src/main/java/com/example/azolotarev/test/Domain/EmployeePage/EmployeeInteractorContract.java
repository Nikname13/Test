package com.example.azolotarev.test.Domain.EmployeePage;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.example.azolotarev.test.BaseCallback;
import com.example.azolotarev.test.UI.ProgressContract;

public interface EmployeeInteractorContract {

    interface getPhotoCallback extends BaseCallback.BaseErrorCallback{
        void onPhoto(Bitmap photo);
    }
    interface GetItemCallback extends BaseCallback.BaseGetItemCallback, BaseCallback.BaseErrorCallback{}
    void getItem(@NonNull String id,@NonNull final GetItemCallback callback );
    void loadPhoto(@NonNull final getPhotoCallback callback, @NonNull String id);
    void setProgressListener(@NonNull ProgressContract listener);
    void clearCredentials();
}
