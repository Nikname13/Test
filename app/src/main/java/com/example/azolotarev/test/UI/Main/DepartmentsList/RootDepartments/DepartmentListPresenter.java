package com.example.azolotarev.test.UI.Main.DepartmentsList.RootDepartments;

import android.support.annotation.NonNull;
import android.util.Log;
import com.example.azolotarev.test.Domain.DepartmentsList.DepartmentInteractorContract;
import com.example.azolotarev.test.Model.DepartmentModel;

import java.util.List;

public class DepartmentListPresenter implements DepartmentListContract.Presenter {

    private final DepartmentListContract.View mDepartmentView;
    private final DepartmentInteractorContract mInteractor;
    private boolean mFirstLoad=true;

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
    public void openDepartmentDetail(@NonNull DepartmentModel selectedDepartment, @NonNull int containerId) {
        Log.e("TAG","departments list presenter open department detail ");
        if(selectedDepartment.getEmploeeList()==null) mDepartmentView.showDepartmentChildren(selectedDepartment.getDepartmentsList(),containerId);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

}
