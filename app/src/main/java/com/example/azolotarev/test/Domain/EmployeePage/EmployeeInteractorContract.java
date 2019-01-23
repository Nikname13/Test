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
    void loadList(int position, @NonNull final DepartmentInteractorContract.GetListCallback callback);
    interface GetItemCallback extends BaseCallback.BaseGetItemCallback, BaseCallback.BaseErrorCallback{}
    void getItem(@NonNull String id,@NonNull final GetItemCallback callback );
    void loadPhoto(@NonNull final PhotoCallback callback, @NonNull String id);
}
