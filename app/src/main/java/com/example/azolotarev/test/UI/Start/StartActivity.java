package com.example.azolotarev.test.UI.Start;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v4.app.Fragment;
import android.support.v4.net.ConnectivityManagerCompat;
import com.example.azolotarev.test.Data.Net.Connect;
import com.example.azolotarev.test.Data.Net.Net;
import com.example.azolotarev.test.Domain.Authorization.AuthorizationInteractor;
import com.example.azolotarev.test.Repository.Repository;
import com.example.azolotarev.test.UI.GenericFragmentActivity;

public class StartActivity extends GenericFragmentActivity {

    @SuppressLint("ServiceCast")
    @Override
    protected Fragment createFragment() {
       StartFragment fragment=StartFragment.newInstance();
       new StartPresenter(fragment,new AuthorizationInteractor(new Repository(getApplicationContext(),new Net(new Connect(), (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)))));
       return fragment;
    }
}
