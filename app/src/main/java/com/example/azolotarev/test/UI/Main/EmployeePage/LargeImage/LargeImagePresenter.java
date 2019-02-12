package com.example.azolotarev.test.UI.Main.EmployeePage.LargeImage;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.example.azolotarev.test.Domain.EmployeePage.EmployeeInteractorContract;
import com.example.azolotarev.test.Service.PresenterManager;
import com.example.azolotarev.test.UI.BaseView;

public class LargeImagePresenter implements LargeImageContract.Presenter {

    private LargeImageContract.View mView;
    private final EmployeeInteractorContract mInteractor;
    private Bitmap mPhoto;
    private String mPhotoId;

    public LargeImagePresenter(EmployeeInteractorContract interactor) {
        mInteractor = interactor;
        mInteractor.setProgressListener(this);
    }

    @Override
    public void start() {
    }

    @Override
    public void bindView(@NonNull BaseView view) {
        mView= (LargeImageContract.View) view;
    }

    @Override
    public void unbindView() {
        PresenterManager.removePresenter(mView.getClass().getName());
        mView=null;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void setPhotoId(@NonNull String id) {
        mPhotoId=id;
    }

    @Override
    public void loadPhoto(int width, int height) {
        mInteractor.loadPhoto(
                mPhotoId,
                width,
                height,
                new EmployeeInteractorContract.PhotoCallback() {
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
                });
    }
}
