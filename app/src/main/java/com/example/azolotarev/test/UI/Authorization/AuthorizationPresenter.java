package com.example.azolotarev.test.UI.Authorization;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import com.example.azolotarev.test.Domain.Authorization.AuthorizationInteractorContract;
import com.example.azolotarev.test.Service.PresenterManager;
import com.example.azolotarev.test.UI.BaseView;

public class AuthorizationPresenter implements AuthorizationContract.Presenter {

    private AuthorizationContract.View mAuthorizationView;
    private AuthorizationInteractorContract mAuthorizationInteractor;

    public AuthorizationPresenter(@NonNull AuthorizationInteractorContract authorizationInteractor) {
        Log.e("TAG", "AuthorizationPresenter");
        mAuthorizationInteractor = authorizationInteractor;
        mAuthorizationInteractor.setProgressListener(this);
    }

    @Override
    public void logIn(String login, String password) {
        Log.e("TAG", "auth presenter logIn");
        mAuthorizationInteractor.logIn(new AuthorizationInteractorContract.getSuccessCallback() {
            @Override
            public void onSuccess(boolean success) {
                Log.e("TAG", "auth presenter login on success "+success);
                if(success){
                    mAuthorizationView.showDepartmentsList();
                }
            }

            @Override
            public void logOut(String errorMessage) {
                Log.e("TAG", "auth presenter login logout ");
                mAuthorizationView.showSuccessError(errorMessage);
            }

            @Override
            public void connectionError(String errorMessage) {
                Log.e("TAG", "auth presenter login logout");
                mAuthorizationView.showConnectionError(errorMessage);
            }
        }
        ,login, password, true);
    }

    @Override
    public void destroy() {
        PresenterManager.removePresenter(mAuthorizationView.getClass().getName());
        unbindView();
    }

    @Override
    public void start() {
        mAuthorizationView.hideProgress();
    }

    @Override
    public void bindView(@NonNull BaseView view) {
        mAuthorizationView = (AuthorizationContract.View) view;
    }

    @Override
    public void unbindView() {
        mAuthorizationView=null;
    }


    @Override
    public void showProgress() {
        mAuthorizationView.showProgress();
    }

    @Override
    public void hideProgress() {
        mAuthorizationView.hideProgress();
    }

}
