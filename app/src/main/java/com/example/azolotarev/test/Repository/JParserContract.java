package com.example.azolotarev.test.Repository;

import android.support.annotation.NonNull;
import com.example.azolotarev.test.Model.DepartmentModel;

import java.util.List;

public  interface JParserContract {

    interface ParsDepartmentsCallback {
        void onDepartmentsLoaded(List<DepartmentModel> departments);
    }
    void getDepartments(@NonNull final ParsDepartmentsCallback callback, String jsonString);
    boolean getSuccess(@NonNull String jsonString);

}
