package com.example.azolotarev.test.UI.Main.DepartmentsList.ChildrenDepartments;

import android.support.annotation.NonNull;
import com.example.azolotarev.test.Domain.DepartmentsList.DepartmentInteractorContract;

public class ChildrenDepartmentPresenter implements ChildrenDepartmentContract.Presenter {

    private final ChildrenDepartmentContract.View mView;
    private final DepartmentInteractorContract mInteractor;

    public ChildrenDepartmentPresenter(@NonNull ChildrenDepartmentContract.View view,@NonNull DepartmentInteractorContract interactor) {
        mView = view;
        mView.setPresenter(this);
        mInteractor = interactor;
        mInteractor.setProgressListener(this);
    }

    @Override
    public void start() {
        mView.showDepartmentsList(mView.getDepartments());
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
