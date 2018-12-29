package com.example.azolotarev.test.Domain.EmployeePage;

import android.support.annotation.NonNull;
import com.example.azolotarev.test.Repository.RepositoryContract;
import com.example.azolotarev.test.UI.ProgressContract;

public class EmployeeInteractor implements EmployeeInteractorContract {
    private final RepositoryContract mRepository;

    public EmployeeInteractor(RepositoryContract repository) {
        mRepository = repository;
    }

    @Override
    public void loadPhoto(@NonNull getPhotoCallback callback) {

    }

    @Override
    public void setProgressListener(@NonNull ProgressContract listener) {

    }
}
