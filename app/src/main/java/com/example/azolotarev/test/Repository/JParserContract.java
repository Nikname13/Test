package com.example.azolotarev.test.Repository;

import android.support.annotation.NonNull;
import com.example.azolotarev.test.Model.DepartmentModel;

import java.util.List;

public  interface JParserContract {

    interface ParsDepartmentsCallback{
        void onDepartmentsLoaded(List<DepartmentModel> departments);
        void notAvailable(String errorMessage);
    }
    interface ParsSuccessCallback{
        void onSuccess(boolean success);
        void errorSuccess(String errorMessage);
    }
    void getDepartments(@NonNull final ParsDepartmentsCallback callback, String jsonString);
    void getSuccess(@NonNull final ParsSuccessCallback callback, String jsonString);

}
