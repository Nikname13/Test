package com.example.azolotarev.test.Repository;

import android.support.annotation.NonNull;
import com.example.azolotarev.test.BaseCallback;
import com.example.azolotarev.test.Model.DepartmentModel;

import java.util.List;

public interface RepositoryContract {

    interface LoadDepartmentsCallback extends BaseCallback.BaseLoadDepartmentsCallback, BaseCallback.BaseErrorCallback {}
    interface LoadSuccessCallback extends BaseCallback.BaseErrorCallback{
        void onSuccess(boolean success);
    }
    void isAuth(@NonNull final LoadSuccessCallback callback, @NonNull String login,@NonNull String password, @NonNull boolean firstLoad);
    void getDepartments(@NonNull final LoadDepartmentsCallback callback, boolean refreshCache, @NonNull boolean firstLoad);
}
