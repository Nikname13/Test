package com.example.azolotarev.test.Model;

import android.support.annotation.NonNull;

import java.util.List;

public class DepartmentModel extends BaseModel {

    private List<EmployeeModel> mEmploeesList;
    private List<DepartmentModel> mDepartmentsList;

    public DepartmentModel(@NonNull int id,@NonNull String name) {
        super(id, name);
    }

}
