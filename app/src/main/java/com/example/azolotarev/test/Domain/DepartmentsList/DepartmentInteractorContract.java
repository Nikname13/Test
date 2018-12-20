package com.example.azolotarev.test.Domain.DepartmentsList;

import android.support.annotation.NonNull;
import com.example.azolotarev.test.Model.DepartmentModel;
import com.example.azolotarev.test.Service.ProgressContract;

import java.util.List;

public interface DepartmentInteractorContract {

    interface getDepartmentsCallback {
        void onDepartmentsLoaded(List<DepartmentModel> departments);
        void logOut(String errorMessage);
        void connectionError(String errorMessage);
        void notAvailable(String errorMessage);
    }
    void getDepartments(@NonNull final getDepartmentsCallback callback);
    void setProgressListener(@NonNull ProgressContract listener);
    void refreshDepartments();
}
