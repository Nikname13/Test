package com.example.azolotarev.test.Model;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

public class DepartmentModel extends BaseModel implements Serializable {

    private List<EmployeeModel> mEmploeeList;
    private List<DepartmentModel> mDepartmentsList;

    public DepartmentModel(@NonNull int id,@NonNull String name) {
        super(id, name);
    }

    public void setEmploeeList(List<EmployeeModel> emploeeList) {
        mEmploeeList = emploeeList;
    }

    public void setDepartmentsList(List<DepartmentModel> departmentsList) {
        mDepartmentsList = departmentsList;
    }

    public List<EmployeeModel> getEmploeeList() {
        return mEmploeeList;
    }

    public List<DepartmentModel> getDepartmentsList() {
        return mDepartmentsList;
    }
}
