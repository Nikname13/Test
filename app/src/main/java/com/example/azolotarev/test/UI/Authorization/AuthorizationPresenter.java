package com.example.azolotarev.test.UI.Authorization;

import android.support.annotation.NonNull;
import com.example.azolotarev.test.Domain.Authorization.AuthorizationInteractorContract;
import com.example.azolotarev.test.Repository.Repository;
import com.example.azolotarev.test.Service.ErrorLab;

public class AuthorizationPresenter implements AuthorizationContract.Presenter {

    private final AuthorizationContract.View mAuthorizationView;
    private final AuthorizationInteractorContract mAuthorizationInteractor;

    public AuthorizationPresenter(@NonNull AuthorizationContract.View authorizationView, @NonNull AuthorizationInteractorContract authorizationInteractor) {
        mAuthorizationView = authorizationView;
        mAuthorizationInteractor = authorizationInteractor;
        mAuthorizationView.setPresenter(this);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showSuccessError(String errorMessage) {

    }

    @Override
    public void showDepartmentsList() {
        mAuthorizationView.showDepartmentsList();
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

}
