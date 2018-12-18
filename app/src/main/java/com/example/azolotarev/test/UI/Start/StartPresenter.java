package com.example.azolotarev.test.UI.Start;

import android.support.annotation.NonNull;
import com.example.azolotarev.test.Domain.Authorization.AuthorizationInteractorContract;

public class StartPresenter implements StartContract.Presenter {

    private static final String EMPTY_LOGIN="";
    private static final String EMPTY_PASSWORD="";
    private StartContract.View mStartView;
    private final AuthorizationInteractorContract mAuthorizationInteractor;

    public StartPresenter(@NonNull StartContract.View view,@NonNull AuthorizationInteractorContract authorizationInteractor) {
        mStartView =view;
        mStartView.setPresenter(this);
        mAuthorizationInteractor = authorizationInteractor;
        mAuthorizationInteractor.setProgressListener(this);

    }

    private void logIn() {
        mAuthorizationInteractor.logIn(EMPTY_LOGIN, EMPTY_PASSWORD);
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
        if(mAuthorizationInteractor.getSuccess()){
            mStartView.showDepartmentsList();
        }else {
            mStartView.showSuccessError("Success error");
            mStartView.showAuthorization();
        }
    }

    @Override
    public void start() {
        logIn();
    }
}
