package com.example.azolotarev.test.Domain.DepartmentsList;

import android.support.annotation.NonNull;
import com.example.azolotarev.test.Model.DepartmentModel;
import com.example.azolotarev.test.Repository.RepositoryContract;
import com.example.azolotarev.test.UI.ProgressContract;

import java.util.List;

public class DepartmentInteractor implements DepartmentInteractorContract {

    private RepositoryContract mRepository;
    private ProgressContract mProgress;
    private boolean mRefreshCache=true;

    public DepartmentInteractor(RepositoryContract repository) {
        mRepository = repository;
    }

    @Override
    public void getDepartments(@NonNull final getDepartmentsCallback callback) {
        mRepository.getDepartments(new RepositoryContract.LoadDepartmentsCallback() {
                                       @Override
                                       public void onDepartmentsLoaded(List<DepartmentModel> departments) {

                                       }

                                       @Override
                                       public void logOut(String errorMessage) {
                                           callback.logOut(errorMessage);
                                       }

                                       @Override
                                       public void connectionError(String errorMessage) {
                                           callback.connectionError(errorMessage);
                                       }

                                       @Override
                                       public void notAvailable(String errorMessage) {
                                           callback.notAvailable(errorMessage);
                                       }
                                   },
                mRefreshCache);
    }

    @Override
    public void setProgressListener(@NonNull ProgressContract listener) {
        mProgress=listener;
    }

    @Override
    public void refreshDepartments() {
        mRefreshCache=true;
    }
}
