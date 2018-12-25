package com.example.azolotarev.test.UI.DepartmentsList.RootDepartments;

import android.util.Log;
import com.example.azolotarev.test.Domain.DepartmentsList.DepartmentInteractorContract;
import com.example.azolotarev.test.Model.DepartmentModel;

import java.util.List;

public class DepartmentListPresenter implements DepartmentListContract.Presenter {

    private DepartmentListContract.View mDepartmentView;
    private DepartmentInteractorContract mInteractor;
    private boolean mFirstLoad=true;

    public DepartmentListPresenter(DepartmentListContract.View departmentView, DepartmentInteractorContract interactor) {
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
                mDepartmentView.showDepartmentsList(departments);
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

    @Override
    public void openDepartmentDetail(DepartmentModel selectedDepartment) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

}
