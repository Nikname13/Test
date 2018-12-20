package com.example.azolotarev.test.UI.DepartmentsList;

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
    public void showConnectionError() {

    }

    @Override
    public void start() {
        loadDepartments(false);
    }

    @Override
    public void loadDepartments(boolean freshUpdate) {
        if(freshUpdate) mInteractor.refreshDepartments();
        mInteractor.getDepartments(new DepartmentInteractorContract.getDepartmentsCallback() {
            @Override
            public void onDepartmentsLoaded(List<DepartmentModel> departments) {

            }

            @Override
            public void logOut(String errorMessage) {
                mDepartmentView.showSuccessError(errorMessage);
                mDepartmentView.showAuthorization();
            }

            @Override
            public void connectionError(String errorMessage) {
                mDepartmentView.showConnectionError(errorMessage);
            }
        });
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

}
