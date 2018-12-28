package com.example.azolotarev.test.UI.Main.DepartmentsList.RootDepartments;

import android.support.annotation.NonNull;
import android.util.Log;
import com.example.azolotarev.test.Domain.DepartmentsList.DepartmentInteractorContract;
import com.example.azolotarev.test.Model.DepartmentModel;
import com.example.azolotarev.test.Model.EmployeeModel;

import java.util.ArrayList;
import java.util.List;

public class DepartmentListPresenter implements DepartmentListContract.Presenter {

    private final DepartmentListContract.View mDepartmentView;
    private final DepartmentInteractorContract mInteractor;
    private boolean mFirstLoad=true;
    private static final int sDepartmentType =0;
    private static final int sEmployyeType =1;

    public DepartmentListPresenter(@NonNull DepartmentListContract.View departmentView,@NonNull DepartmentInteractorContract interactor) {
        mDepartmentView = departmentView;
        mDepartmentView.setPresenter(this);
        mInteractor = interactor;
        mInteractor.setProgressListener(this);
    }

    @Override
    public void start() {
        Log.e("TAG", "start department");
        loadDepartments(false, false);
    }

    @Override
    public void loadDepartments(boolean freshUpdate, boolean firstLoad) {
        if(freshUpdate) mInteractor.refreshDepartments();
        mInteractor.getDepartments(new DepartmentInteractorContract.getDepartmentsCallback() {
            @Override
            public void onDepartmentsLoaded(List<DepartmentModel> departments) {
                mDepartmentView.showDepartmentsList(getObjectList(departments),sDepartmentType);
            }

            @Override
            public void notAvailable(String errorMessage) {

            }

            @Override
            public void logOut(String errorMessage) {
                mDepartmentView.showSuccessError(errorMessage);
            }

            @Override
            public void connectionError(String errorMessage) {
                mDepartmentView.showConnectionError(errorMessage);
            }
        },
        firstLoad);
    }

    private List<Object> getObjectList(List<DepartmentModel> departments){
        List<Object> list=new ArrayList<>();
        for(DepartmentModel model:departments){
            list.add(model);
        }
        return list;
    }
    private List<Object> getEObjectList(List<EmployeeModel> departments){
        List<Object> list=new ArrayList<>();
        for(EmployeeModel model:departments){
            list.add(model);
        }
        return list;
    }

    @Override
    public void openDepartmentDetail(@NonNull DepartmentModel selectedDepartment, @NonNull int containerId) {
        Log.e("TAG","departments list presenter open department detail ");
        if(selectedDepartment.getEmploeeList()==null && selectedDepartment.getDepartmentsList()!=null)
            mDepartmentView.showDepartmentChildren(getObjectList(selectedDepartment.getDepartmentsList()),containerId,sDepartmentType);
        if(selectedDepartment.getDepartmentsList()==null && selectedDepartment.getEmploeeList()!=null)
            mDepartmentView.showDepartmentChildren(getEObjectList(selectedDepartment.getEmploeeList()),containerId,sEmployyeType);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

}
