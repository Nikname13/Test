package com.example.azolotarev.test.Domain.DepartmentsList;

import android.support.annotation.NonNull;
import com.example.azolotarev.test.BaseCallback;
import com.example.azolotarev.test.Model.DepartmentModel;
import com.example.azolotarev.test.Model.RecyclerModel;
import com.example.azolotarev.test.UI.ProgressContract;

import java.util.List;

public interface DepartmentInteractorContract {

    interface getDepartmentsCallback extends  BaseCallback.BaseErrorCallback {
        void onDepartmentsLoaded(List<RecyclerModel> list);
        void notAvailable(String errorMessage);
    }

    void getDepartments(@NonNull final getDepartmentsCallback callback, @NonNull boolean firstLoad);
    void setProgressListener(@NonNull ProgressContract listener);
    void refreshDepartments();
}
