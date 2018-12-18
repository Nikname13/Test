package com.example.azolotarev.test.UI.Authorization;

import android.content.Context;
import android.support.annotation.NonNull;
import com.example.azolotarev.test.Domain.Authorization.AuthorizationInteractorContract;

public class AuthorizationPresenter implements AuthorizationContract.Presenter {

    private final AuthorizationContract.View mAuthorizationView;
    private final AuthorizationInteractorContract mAuthorizationInteractor;

    public AuthorizationPresenter(@NonNull AuthorizationContract.View authorizationView, @NonNull AuthorizationInteractorContract authorizationInteractor) {
        mAuthorizationView = authorizationView;
        mAuthorizationInteractor = authorizationInteractor;
        mAuthorizationView.setPresenter(this);
        mAuthorizationInteractor.setProgressListener(this);
    }


    @Override
    public void showConnectionError() {

    }

    @Override
    public void logIn(String login, String password) {
        mAuthorizationInteractor.logIn(login,password);
    }

    @Override
    public void start() {

    }


    @Override
    public void showProgress() {
        mAuthorizationView.showProgress();
    }

    @Override
    public void hideProgress() {
        mAuthorizationView.hideProgress();
        if(mAuthorizationInteractor.getSuccess()){
            mAuthorizationView.showDepartmentsList();
        }else {
            mAuthorizationView.showSuccessError("Success error");
        }
    }

}
