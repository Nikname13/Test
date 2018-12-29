package com.example.azolotarev.test.Domain.EmployeePage;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.example.azolotarev.test.BaseCallback;
import com.example.azolotarev.test.UI.ProgressContract;

public interface EmployeeInteractorContract {

    interface getPhotoCallback extends BaseCallback.BaseErrorCallback{
        void onPhoto(Bitmap photo);
    }
    void loadPhoto(@NonNull final getPhotoCallback callback);
    void setProgressListener(@NonNull ProgressContract listener);
}
