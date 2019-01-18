package com.example.azolotarev.test.Model;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

public class DepartmentModel extends BaseModel implements Serializable {

    private List<EmployeeModel> mEmployeeList;
    private List<DepartmentModel> mDepartmentsList;


    public DepartmentModel(@NonNull String id,@NonNull String name) {
        super(id, name);
    }

    public DepartmentModel() {
    }

    public void setEmployeeList(List<EmployeeModel> employeeList) {
        mEmployeeList = employeeList;
    }

    public void setDepartmentsList(List<DepartmentModel> departmentsList) {
        mDepartmentsList = departmentsList;
    }

    public List<EmployeeModel> getEmployeeList() {
        return mEmployeeList;
    }

    public List<DepartmentModel> getDepartmentsList() {
        return mDepartmentsList;
    }

}
