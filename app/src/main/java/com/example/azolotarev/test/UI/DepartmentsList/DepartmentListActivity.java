package com.example.azolotarev.test.UI.DepartmentsList;

import android.support.v4.app.Fragment;
import com.example.azolotarev.test.Domain.Authorization.AuthorizationInteractor;
import com.example.azolotarev.test.Domain.DepartmentsList.DepartmentInteractor;
import com.example.azolotarev.test.Repository.Repository;
import com.example.azolotarev.test.UI.Authorization.AuthorizationFragment;
import com.example.azolotarev.test.UI.Authorization.AuthorizationPresenter;
import com.example.azolotarev.test.UI.GenericFragmentActivity;

public class DepartmentListActivity extends GenericFragmentActivity {

    public static final String EXTRA_SUCCESS = "SUCCESS";

    @Override
    protected Fragment createFragment() {
        return logIn();
    }

    private Fragment logIn() {
        if(getIntent().getBooleanExtra(EXTRA_SUCCESS, false)){
            DepartmentListFragment fragment=DepartmentListFragment.newInstance();
            new DepartmentListPresenter(fragment, new DepartmentInteractor(new Repository(getApplicationContext())));
            return fragment;
        }else{
            AuthorizationFragment fragment=AuthorizationFragment.newInstance();
            new AuthorizationPresenter(fragment,new AuthorizationInteractor(new Repository(getApplicationContext())));
            return fragment;
        }
    }

}
