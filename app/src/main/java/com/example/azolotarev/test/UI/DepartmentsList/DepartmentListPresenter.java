package com.example.azolotarev.test.UI.DepartmentsList;

import com.example.azolotarev.test.Domain.DepartmentsList.DepartmentInteractorContract;

public class DepartmentListPresenter implements DepartmentListContract.Presenter {

    private DepartmentListContract.View mDepartmentView;
    private DepartmentInteractorContract mInteractor;

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
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void start() {

    }
}
