package com.example.azolotarev.test.presentation;

import android.support.v4.app.Fragment;
import com.example.azolotarev.test.R;
import com.example.azolotarev.test.Repository.Repository;
import com.example.azolotarev.test.Service.ErrorLab;
import com.example.azolotarev.test.presentation.Authorization.AuthorizationContract;
import com.example.azolotarev.test.presentation.Authorization.AuthorizationFragment;
import com.example.azolotarev.test.presentation.Authorization.AuthorizationPresenter;
import com.example.azolotarev.test.presentation.DepartmentsList.DepartmentListFragment;

public class StartActivity extends GenericFragmentActivity implements AuthorizationFragment.Callback {

    @Override
    protected Fragment createFragment() {
      return logIn();
    }

    @Override
    public void onAuthorization(String login, String password) {

    }

    private Fragment logIn(){
        if(new Repository().isAuth(this)){
            return new DepartmentListFragment();
        }else{
            AuthorizationFragment fragment=AuthorizationFragment.newInstance();
            new AuthorizationPresenter(fragment);
           return fragment;
        }
    }
}
