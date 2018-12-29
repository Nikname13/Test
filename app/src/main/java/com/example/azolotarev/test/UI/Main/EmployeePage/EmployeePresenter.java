package com.example.azolotarev.test.UI.Main.EmployeePage;

import android.support.annotation.NonNull;
import com.example.azolotarev.test.Domain.EmployeePage.EmployeeInteractorContract;

public class EmployeePresenter implements EmployeePageContract.Presenter {

    private final EmployeePageContract.View mView;
    private final EmployeeInteractorContract mInteractorContract;

    public EmployeePresenter(@NonNull EmployeePageContract.View view,@NonNull EmployeeInteractorContract interactorContract) {
        mView = view;
        mView.setPresenter(this);
        mInteractorContract = interactorContract;
        mInteractorContract.setProgressListener(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
