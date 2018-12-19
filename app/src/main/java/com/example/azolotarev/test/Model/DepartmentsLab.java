package com.example.azolotarev.test.Model;

import java.util.List;

public class DepartmentsLab {

    private static DepartmentsLab mDepartmentsLab;
    private List<DepartmentModel> mDepartmentModelList;

    public static DepartmentsLab get(){
        if(mDepartmentsLab==null) mDepartmentsLab=new DepartmentsLab();
        return mDepartmentsLab;
    }

    private DepartmentsLab(){

    }

    public List<DepartmentModel> getDepartmentModelList() {
        return mDepartmentModelList;
    }
}
