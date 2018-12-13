package com.example.azolotarev.test.presentation.Authorization;

import android.support.annotation.NonNull;
import com.example.azolotarev.test.R;
import com.example.azolotarev.test.Repository.Repository;
import com.example.azolotarev.test.Service.ErrorLab;
import com.example.azolotarev.test.presentation.DepartmentsList.DepartmentListFragment;

public class AuthorizationPresenter implements AuthorizationContract.Presenter {

    private final AuthorizationContract.View mAuthorizationView;

    public AuthorizationPresenter(@NonNull AuthorizationContract.View authorizationView) {
        mAuthorizationView = authorizationView;
        mAuthorizationView.setPresenter(this);
    }

    @Override
    public void logIn(String login, String password) {
        if(new Repository().isAuth(login, password)) {
            mAuthorizationView.showDepartmentsList();
        }else{
            mAuthorizationView.showSuccess();
            ErrorLab.errorMessage();
            //authorization();
        }
    }

    @Override
    public void start() {

    }
}
