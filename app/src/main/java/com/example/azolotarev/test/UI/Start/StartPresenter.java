package com.example.azolotarev.test.UI.Start;

import android.support.annotation.NonNull;
import android.util.Log;
import com.example.azolotarev.test.Domain.Authorization.AuthorizationInteractorContract;
import com.example.azolotarev.test.Domain.DepartmentsList.DepartmentInteractorContract;
import com.example.azolotarev.test.Model.DepartmentModel;

import java.util.List;

public class StartPresenter implements StartContract.Presenter {

    private static final String EMPTY_LOGIN="";
    private static final String EMPTY_PASSWORD="";
    private StartContract.View mStartView;
    private final AuthorizationInteractorContract mAuthorizationInteractor;
    private final DepartmentInteractorContract mDepartmentInteractor;
    private boolean mFirstLoad=true;

    public StartPresenter(@NonNull StartContract.View view,@NonNull AuthorizationInteractorContract authorizationInteractor, @NonNull DepartmentInteractorContract departmentInteractor) {
        mStartView =view;
        mStartView.setPresenter(this);
        mAuthorizationInteractor = authorizationInteractor;
        mAuthorizationInteractor.setProgressListener(this);
        mDepartmentInteractor=departmentInteractor;
        mDepartmentInteractor.setProgressListener(this);

    }

    private void logIn(boolean success) {

        Log.e("TAG", "start login "+success);
        if(!success) {
            mAuthorizationInteractor.logIn(new AuthorizationInteractorContract.getSuccessCallback() {
                                               @Override
                                               public void onSuccess(boolean success) {
                                                   Log.e("TAG", "start login on success " + success);
                                                   if (success) {
                                                       loadDepartment();
                                                   } else {
                                                       mStartView.showAuthorization();
                                                   }
                                               }

                                               @Override
                                               public void logOut(String errorMessage) {
                                                   Log.e("TAG", "start logOut");
                                                   mStartView.showSuccessError(errorMessage);
                                               }

                                               @Override
                                               public void connectionError(String errorMessage) {
                                                   Log.e("TAG", "start connectionError");
                                                   mStartView.showConnectionError(errorMessage);
                                               }
                                           },
                    EMPTY_LOGIN, EMPTY_PASSWORD, mFirstLoad);
        }else{
            Log.e("TAG", "start login "+success);
            loadDepartment();
        }
    }

    private void loadDepartment(){
        Log.e("TAG", "start load department");
        mDepartmentInteractor.getDepartments(new DepartmentInteractorContract.getDepartmentsCallback() {
                                       @Override
                                       public void onDepartmentsLoaded(List<DepartmentModel> departments) {
                                           mStartView.showDepartmentsList();
                                       }

                                       @Override
                                       public void notAvailable(String errorMessage) {

                                       }

                                       @Override
                                       public void logOut(String errorMessage) {
                                           mStartView.showSuccessError(errorMessage);
                                       }

                                       @Override
                                       public void connectionError(String errorMessage) {
                                           mStartView.showConnectionError(errorMessage);
                                       }
                                   },
                mFirstLoad);
    }

    @Override
    public void showConnectionError() {

    }

    @Override
    public void showProgress() {
        mStartView.showProgress();
    }

    @Override
    public void hideProgress() {
        mStartView.hideProgress();
    }

    @Override
    public void start(boolean success) {
        Log.e("TAG", "start");
        logIn(success);
    }
}
