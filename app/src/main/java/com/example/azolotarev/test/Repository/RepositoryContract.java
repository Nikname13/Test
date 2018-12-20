package com.example.azolotarev.test.Repository;

import android.support.annotation.NonNull;
import com.example.azolotarev.test.Model.DepartmentModel;

import java.util.List;

public interface RepositoryContract {

    interface LoadDepartmentsCallback{
        void onDepartmentsLoaded(List<DepartmentModel> departments);
        void logOut(String errorMessage);
        void connectionError(String errorMessage);
        void notAvailable(String errorMessage);
    }

    boolean isAuth(@NonNull String login,@NonNull String password);
    void getDepartments(@NonNull final LoadDepartmentsCallback callback, boolean refreshCache);
}
