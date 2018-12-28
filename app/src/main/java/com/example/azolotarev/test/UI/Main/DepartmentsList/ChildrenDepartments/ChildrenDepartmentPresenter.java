package com.example.azolotarev.test.UI.Main.DepartmentsList.ChildrenDepartments;

import android.support.annotation.NonNull;
import com.example.azolotarev.test.Domain.DepartmentsList.DepartmentInteractorContract;
import com.example.azolotarev.test.Model.DepartmentModel;
import com.example.azolotarev.test.Model.EmployeeModel;

import java.util.ArrayList;
import java.util.List;

public class ChildrenDepartmentPresenter implements ChildrenDepartmentContract.Presenter {

    private final ChildrenDepartmentContract.View mView;
    private final DepartmentInteractorContract mInteractor;
    private static final int sDepartmentType =0;
    private static final int sEmployyeType =1;

    public ChildrenDepartmentPresenter(@NonNull ChildrenDepartmentContract.View view,@NonNull DepartmentInteractorContract interactor) {
        mView = view;
        mView.setPresenter(this);
        mInteractor = interactor;
        mInteractor.setProgressListener(this);
    }

    @Override
    public void start() {
        mView.showListModel(mView.getListModel(),mView.getViewType());
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void openDetail(@NonNull Object selectedModel, @NonNull int containerId) {
        if(selectedModel instanceof DepartmentModel){
            DepartmentModel department=(DepartmentModel)selectedModel;
            if(department.getEmploeeList()==null && department.getDepartmentsList()!=null) mView.showChildrenList(departmentToObject(department.getDepartmentsList()),containerId,sDepartmentType);
            if(department.getDepartmentsList()==null && department.getEmploeeList()!=null) mView.showChildrenList(employeeToObjcet(department.getEmploeeList()),containerId,sEmployyeType);
        }else{

        }
    }
    private List<Object> departmentToObject(List<DepartmentModel> departments){
        List<Object> list=new ArrayList<>();
        for(DepartmentModel model:departments){
            list.add(model);
        }
        return list;
    }
    private List<Object> employeeToObjcet(List<EmployeeModel> employee){
        List<Object> list=new ArrayList<>();
        for(EmployeeModel model:employee){
            list.add(model);
        }
        return list;
    }
}
