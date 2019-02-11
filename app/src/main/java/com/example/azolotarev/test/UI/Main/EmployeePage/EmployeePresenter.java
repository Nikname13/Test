package com.example.azolotarev.test.UI.Main.EmployeePage;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;
import com.example.azolotarev.test.Domain.EmployeePage.EmployeeInteractorContract;
import com.example.azolotarev.test.Model.EmployeeModel;
import com.example.azolotarev.test.Model.MapModel;
import com.example.azolotarev.test.UI.BaseView;
import com.example.azolotarev.test.UI.Main.EmployeeViewPager.PageTitle;

public class EmployeePresenter implements EmployeeContract.Presenter {

    private EmployeeContract.View mView;
    private PageTitle mPageListenr;
    private final EmployeeInteractorContract mInteractor;
    private boolean mLoadPhoto,mLoadEmployee;
    private String mIdModel;

    public EmployeePresenter(@NonNull EmployeeInteractorContract interactorContract, @NonNull PageTitle pageListenr) {
        mInteractor = interactorContract;
        mInteractor.setProgressListener(this);
        mPageListenr=pageListenr;
    }

    @Override
    public void start(String id, int position) {
        loadMapEmployee(id,position);
    }

    private void loadMapEmployee(final String id, final int position) {
        if (id != null && !id.isEmpty()) {
            mLoadEmployee=true;
            mInteractor.getItem(id, new EmployeeInteractorContract.GetItemCallback() {
                @Override
                public void logOut(String errorMessage) {

                }

                @Override
                public void connectionError(String errorMessage) {

                }

                @Override
                public void onItemLoaded(@NonNull MapModel item) {
                     setDataEmployee((EmployeeModel) item.getModel(), position);
                }

                @Override
                public void notAvailable(String errorMessage) {

                }
            });
        }
    }

    private void setDataEmployee(@NonNull EmployeeModel model, int position){
        mIdModel=model.getId();
        if(model.getParent().getName() !=null) mPageListenr.setPageTitle(model.getParent().getName(),position);
        if (model.getTitle() != null) mView.setTitle(model.getTitle() + model.getParent().getName());
        if (model.getName() != null) mView.setName(model.getName());
        if (model.getPhone() != null) mView.setPhone(model.getPhone());
        else mView.hidePhone();
        if(model.getEmail()!=null)mView.setEmail(model.getEmail());
        else mView.hideEmail();
        mLoadPhoto=true;
        mInteractor.loadPhoto(new EmployeeInteractorContract.PhotoCallback() {
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
                mIdModel);
    }

    @Override
    public void start() {

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
        if(mLoadEmployee)Log.d("TAG","loadEmloyee");
        if(mLoadPhoto)Log.d("TAG","load photo");
    }

    @Override
    public void hideProgress() {
        if(mLoadEmployee){
            Log.d("TAG","loadEmloyee stop");
            mLoadEmployee=false;
        }
        if(mLoadPhoto){
            Log.d("TAG","load photo stop");
            mLoadPhoto=false;
        }
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
        mView.showLargeImage(mIdModel);
    }

}
