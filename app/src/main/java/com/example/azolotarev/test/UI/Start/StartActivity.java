package com.example.azolotarev.test.UI.Start;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import com.example.azolotarev.test.Data.Local.PersistentStorage;
import com.example.azolotarev.test.Data.Net.Connect;
import com.example.azolotarev.test.Data.Net.Net;
import com.example.azolotarev.test.Domain.Authorization.AuthorizationInteractor;
import com.example.azolotarev.test.Domain.DepartmentsList.DepartmentInteractor;
import com.example.azolotarev.test.R;
import com.example.azolotarev.test.Repository.Repository;
import com.example.azolotarev.test.UI.GenericFragmentActivity;

public class StartActivity extends GenericFragmentActivity {

    private boolean firstLoadSuccess=false;

    @Override
    protected Fragment createFragment() {
       StartFragment fragment=StartFragment.newInstance(firstLoadSuccess);
       new StartPresenter(fragment,new AuthorizationInteractor(
               new Repository(PersistentStorage.init(getApplicationContext()),
                       new Net(new Connect(),
                               (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)))),
               new DepartmentInteractor(new Repository(PersistentStorage.init(getApplicationContext()),
                       new Net(new Connect(),
                               (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE)))));
       return fragment;
    }

    @Override
    public void onBackPressed() {
        int count=getSupportFragmentManager().getBackStackEntryCount();
        Log.e("TAG", "!!! fragment count "+count);
        if(count==0){
            super.onBackPressed();
        }else {

            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
