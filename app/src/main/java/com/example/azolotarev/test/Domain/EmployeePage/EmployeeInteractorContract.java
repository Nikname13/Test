package com.example.azolotarev.test.Domain.EmployeePage;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.example.azolotarev.test.BaseCallback;
import com.example.azolotarev.test.Domain.DepartmentsList.DepartmentInteractorContract;
import com.example.azolotarev.test.UI.ProgressContract;

public interface EmployeeInteractorContract extends DepartmentInteractorContract {

    interface PhotoCallback extends BaseCallback.BaseErrorCallback{
        void onPhoto(Bitmap photo);
    }
    interface GetItemCallback extends BaseCallback.BaseGetItemCallback, BaseCallback.BaseErrorCallback{}
    void loadList(int position, @NonNull final DepartmentInteractorContract.GetListCallback callback);
    void getItem(@NonNull String id,@NonNull final GetItemCallback callback );
    void loadPhoto( @NonNull String id, int imageWidth, int imageHeight, boolean large, @NonNull final PhotoCallback callback);
}
