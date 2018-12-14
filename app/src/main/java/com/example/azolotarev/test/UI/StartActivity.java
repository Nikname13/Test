package com.example.azolotarev.test.UI;

import android.support.v4.app.Fragment;
import com.example.azolotarev.test.Domain.Authorization.AuthorizationInteractor;
import com.example.azolotarev.test.Domain.Authorization.AuthorizationInteractorContract;
import com.example.azolotarev.test.Repository.Repository;
import com.example.azolotarev.test.UI.Authorization.AuthorizationFragment;
import com.example.azolotarev.test.UI.Authorization.AuthorizationPresenter;
import com.example.azolotarev.test.UI.DepartmentsList.DepartmentListFragment;

public class StartActivity extends GenericFragmentActivity{

    @Override
    protected Fragment createFragment() {
      return logIn();
    }

    private Fragment logIn(){
        if(new Repository().isAuth(this)){
            return new DepartmentListFragment();
        }else{
            AuthorizationFragment fragment=AuthorizationFragment.newInstance();
            new AuthorizationPresenter(fragment, new AuthorizationInteractor());
           return fragment;
        }
    }
}
