package com.example.azolotarev.test.UI.Start;

import android.support.annotation.NonNull;
import android.util.Log;
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
        Log.e("TAG", "start login");
        mAuthorizationInteractor.logIn(new AuthorizationInteractorContract.getSuccessCallback() {
            @Override
            public void onSuccess(boolean success) {
                Log.e("TAG", "start login on success "+success);
                if(success){
                    mStartView.showDepartmentsList();
                }else{
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
                EMPTY_LOGIN, EMPTY_PASSWORD);
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
    public void start() {
        Log.e("TAG", "start");
        logIn();
    }
}
