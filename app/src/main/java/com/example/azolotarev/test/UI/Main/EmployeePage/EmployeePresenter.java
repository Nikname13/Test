package com.example.azolotarev.test.UI.Main.EmployeePage;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.example.azolotarev.test.Domain.EmployeePage.EmployeeInteractorContract;
import com.example.azolotarev.test.Model.EmployeeModel;

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
        if (mView.getEmployee() != null) {
            EmployeeModel model = mView.getEmployee();
            if (model.getTitle() != null) mView.setTitle(model.getTitle());
            if (model.getName() != null) mView.setName(model.getName());
            if (model.getPhone() != null) mView.setPhone(model.getPhone());
            else mView.hidePhone();
            if(model.getEmail()!=null)mView.setEmail(model.getEmail());
            else mView.hideEmail();
            mInteractorContract.loadPhoto(new EmployeeInteractorContract.getPhotoCallback() {
                                              @Override
                                              public void onPhoto(Bitmap photo) {
                                                  mView.setAvatar(photo);
                                              }

                                              @Override
                                              public void logOut(String errorMessage) {

                                              }

                                              @Override
                                              public void connectionError(String errorMessage) {

                                              }
                                          },
                    model.getId());

        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
