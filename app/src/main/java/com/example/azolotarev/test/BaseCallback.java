package com.example.azolotarev.test;

import android.support.annotation.NonNull;
import com.example.azolotarev.test.Model.DepartmentModel;
import com.example.azolotarev.test.Model.MapModel;

import java.util.List;

public interface BaseCallback {

    interface BaseLoadDepartmentsCallback{
        void onDepartmentsLoaded(@NonNull List<DepartmentModel> departments);
        void notAvailable(String errorMessage);
    }

    interface BaseGetItemCallback{
        void onItemLoaded(@NonNull MapModel item);
        void notAvailable(String errorMessage);
    }

    interface BaseErrorCallback{
        void logOut(String errorMessage);
        void connectionError(String errorMessage);
    }
}
