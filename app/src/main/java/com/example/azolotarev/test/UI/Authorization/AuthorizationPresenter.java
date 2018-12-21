package com.example.azolotarev.test.UI.Authorization;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
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
        ,login, password);
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
    }

}
