package com.example.azolotarev.test.UI.Start;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import com.example.azolotarev.test.Domain.Authorization.AuthorizationInteractor;
import com.example.azolotarev.test.Repository.Repository;
import com.example.azolotarev.test.UI.GenericFragmentActivity;

public class StartActivity extends GenericFragmentActivity {

    @Override
    protected Fragment createFragment() {
       StartFragment fragment=StartFragment.newInstance();
       new StartPresenter(fragment,new AuthorizationInteractor(new Repository(getApplicationContext())));
       return fragment;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }
}
