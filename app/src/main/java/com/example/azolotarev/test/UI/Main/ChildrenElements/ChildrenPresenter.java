package com.example.azolotarev.test.UI.Main.ChildrenElements;

import android.support.annotation.NonNull;
import com.example.azolotarev.test.Domain.DepartmentsList.DepartmentInteractorContract;
import com.example.azolotarev.test.Model.BaseModel;
import com.example.azolotarev.test.Model.DepartmentModel;
import com.example.azolotarev.test.Model.EmployeeModel;

import java.util.ArrayList;
import java.util.List;

public class ChildrenPresenter implements ChildrenContract.Presenter {

    private final ChildrenContract.View mView;
    private final DepartmentInteractorContract mInteractor;
    private static final int sDepartmentType =0;
    private static final int sEmployyeType =1;

    public ChildrenPresenter(@NonNull ChildrenContract.View view, @NonNull DepartmentInteractorContract interactor) {
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
    public void openDetail(@NonNull BaseModel selectedModel, @NonNull int containerId) {
        if(selectedModel instanceof DepartmentModel){
            DepartmentModel department=(DepartmentModel)selectedModel;
            if(department.getEmployeeList()==null && department.getDepartmentsList()!=null) mView.showChildrenList(departmentToObject(department.getDepartmentsList()),containerId,sDepartmentType);
            if(department.getDepartmentsList()==null && department.getEmployeeList()!=null) mView.showChildrenList(employeeToObjcet(department.getEmployeeList()),containerId,sEmployyeType);
        }else{
            mView.showChildrenDetail(selectedModel);
        }
    }
    private List<BaseModel> departmentToObject(List<DepartmentModel> departments){
        List<BaseModel> list=new ArrayList<>();
        for(DepartmentModel model:departments){
            list.add(model);
        }
        return list;
    }
    private List<BaseModel> employeeToObjcet(List<EmployeeModel> employee){
        List<BaseModel> list=new ArrayList<>();
        for(EmployeeModel model:employee){
            list.add(model);
        }
        return list;
    }
}
