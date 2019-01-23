package com.example.azolotarev.test.UI.Main.EmployeeViewPager;

import android.support.annotation.NonNull;
import com.example.azolotarev.test.Domain.DepartmentsList.DepartmentInteractorContract;
import com.example.azolotarev.test.Domain.EmployeePage.EmployeeInteractorContract;
import com.example.azolotarev.test.Model.MapModel;
import com.example.azolotarev.test.UI.BaseView;

import java.util.List;

public class EmployeePagerPresenter implements EmployeePagerContract.Presenter {

    private EmployeeInteractorContract mInteractor;
    private EmployeePagerContract.View mView;

    public EmployeePagerPresenter(@NonNull EmployeeInteractorContract interactor) {
        mInteractor = interactor;
        mInteractor.setProgressListener(this);
    }

    @Override
    public void start(String position) {
        mInteractor.loadList(Integer.valueOf(position), new DepartmentInteractorContract.GetListCallback() {
            @Override
            public void onMapListLoaded(List<MapModel> list) {
                mView.initViewPager(list);
            }

            @Override
            public void notAvailable(String errorMessage) {

            }

            @Override
            public void logOut(String errorMessage) {

            }

            @Override
            public void connectionError(String errorMessage) {

            }
        });

    }
    @Override
    public void start() {

    }

    @Override
    public void bindView(@NonNull BaseView view) {
        mView= (EmployeePagerContract.View) view;
    }

    @Override
    public void unbindView() {
        mView=null;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

}
