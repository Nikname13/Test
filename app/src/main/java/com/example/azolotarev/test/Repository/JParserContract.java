package com.example.azolotarev.test.Repository;

import android.support.annotation.NonNull;
import com.example.azolotarev.test.Model.DepartmentModel;

import java.util.List;

public  interface JParserContract {

    interface ParsDepartmentsCallback{
        void onDepartmentsLoaded(@NonNull List<DepartmentModel> departments);
        void notAvailable(String errorMessage);
    }
    interface ParsSuccessCallback{
        void onSuccess(boolean success);
        void errorSuccess(String errorMessage);
    }
    void parsDepartments( String jsonString, @NonNull final ParsDepartmentsCallback callback);
    void parsSuccess(@NonNull  String jsonString, @NonNull final ParsSuccessCallback callback);
}
