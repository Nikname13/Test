package com.example.azolotarev.test.Model;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

public class DepartmentModel extends BaseModel implements Serializable {

    @SerializedNameField(name = "Employees")
    private List<EmployeeModel> mEmployeeList;
    @SerializedNameField(name = "Departments")
    private List<DepartmentModel> mDepartmentsList;

    public DepartmentModel(@NonNull int id,@NonNull String name) {
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
