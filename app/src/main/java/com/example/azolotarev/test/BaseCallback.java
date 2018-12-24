package com.example.azolotarev.test;

import com.example.azolotarev.test.Model.DepartmentModel;

import java.util.List;

public interface BaseCallback {

    interface BaseLoadDepartmentsCallback{
        void onDepartmentsLoaded(List<DepartmentModel> departments);
        void notAvailable(String errorMessage);
    }

    interface BaseErrorCallback{
        void logOut(String errorMessage);
        void connectionError(String errorMessage);
    }
}
