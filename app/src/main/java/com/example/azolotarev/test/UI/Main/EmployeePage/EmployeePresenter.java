package com.example.azolotarev.test.UI.Main.EmployeePage;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.example.azolotarev.test.Domain.EmployeePage.EmployeeInteractorContract;
import com.example.azolotarev.test.Model.EmployeeModel;
import com.example.azolotarev.test.Model.MapModel;
import com.example.azolotarev.test.UI.BaseView;

public class EmployeePresenter implements EmployeeContract.Presenter {

    private EmployeeContract.View mView;
    private final EmployeeInteractorContract mInteractor;
    private boolean mLoadPhoto,mLoadEmployee;
    private String mPhotoId, mIdMapModel;

    public EmployeePresenter(@NonNull EmployeeInteractorContract interactorContract) {
        mInteractor = interactorContract;
        mInteractor.setProgressListener(this);
    }

    @Override
    public void start() {
        loadMapEmployee();
    }

    private void loadMapEmployee() {
        if (mIdMapModel != null && !mIdMapModel.isEmpty()) {
            mLoadEmployee=true;
            mInteractor.getItem(mIdMapModel, new EmployeeInteractorContract.GetItemCallback() {
                @Override
                public void logOut(String errorMessage) {

                }

                @Override
                public void connectionError(String errorMessage) {

                }

                @Override
                public void onItemLoaded(@NonNull MapModel item) {
                     setDataEmployee((EmployeeModel) item.getModel());
                }

                @Override
                public void notAvailable(String errorMessage) {

                }
            });
        }
    }

    private void setDataEmployee(@NonNull EmployeeModel model){
        if (model.getTitle() != null) mView.setTitle(model.getTitle() + model.getParent().getName());
        if (model.getName() != null) mView.setName(model.getName());
        if (model.getPhone() != null) mView.setPhone(model.getPhone());
        else mView.hidePhone();
        if(model.getEmail()!=null)mView.setEmail(model.getEmail());
        else mView.hideEmail();

    }

    @Override
    public void bindView(@NonNull BaseView view) {
        mView= (EmployeeContract.View) view;
    }

    @Override
    public void unbindView() {
        mView=null;

    }

    @Override
    public void showProgress() {
/*        if(mLoadEmployee)Log.d("TAG","loadEmloyee");
        if(mLoadPhoto)Log.d("TAG","load photo");*/
    }

    @Override
    public void hideProgress() {
/*        if(mLoadEmployee){
            Log.d("TAG","loadEmloyee stop");
            mLoadEmployee=false;
        }
        if(mLoadPhoto){
            Log.d("TAG","load photo stop");
            mLoadPhoto=false;
        }*/
    }

    @Override
    public void callNumber(String number) {
        mView.callNumber(number);
    }

    @Override
    public void sendEmail(String email) {
        mView.sendEmail(email);
    }

    @Override
    public void showLargeImage() {
        mView.showLargeImage(mPhotoId);
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
                        mView.setAvatarView(photo);
                        mLoadPhoto=true;
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
    public void setPhotoId(@NonNull String id) {
        mPhotoId=id;
    }

    @Override
    public void setItemId(@NonNull String id) {
        mIdMapModel=id;
    }

}
