package com.example.azolotarev.test.Domain.DepartmentsList;

import android.support.annotation.NonNull;
import com.example.azolotarev.test.BaseCallback;
import com.example.azolotarev.test.UI.ProgressContract;

public interface DepartmentInteractorContract {

    interface getDepartmentsCallback extends BaseCallback.BaseLoadDepartmentsCallback, BaseCallback.BaseErrorCallback {
    }

    void getDepartments(@NonNull final getDepartmentsCallback callback, @NonNull boolean firstLoad);
    void setProgressListener(@NonNull ProgressContract listener);
    void refreshDepartments();
}
