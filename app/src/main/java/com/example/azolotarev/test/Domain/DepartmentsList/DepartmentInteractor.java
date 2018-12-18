package com.example.azolotarev.test.Domain.DepartmentsList;

import android.support.annotation.NonNull;
import com.example.azolotarev.test.Repository.RepositoryContract;
import com.example.azolotarev.test.Service.ProgressContract;

public class DepartmentInteractor implements DepartmentInteractorContract {

    private RepositoryContract mRepository;
    private ProgressContract mProgress;

    public DepartmentInteractor(RepositoryContract repository) {
        mRepository = repository;
    }

    @Override
    public void setProgressListener(@NonNull ProgressContract listener) {
        mProgress=listener;
    }
}
