package com.example.azolotarev.test.UI.Start;

import android.support.v4.app.Fragment;
import android.util.Log;
import com.example.azolotarev.test.Data.Local.PersistentStorage;
import com.example.azolotarev.test.Data.Net.Connect;
import com.example.azolotarev.test.Data.Net.Net;
import com.example.azolotarev.test.Domain.Authorization.AuthorizationInteractor;
import com.example.azolotarev.test.Repository.Repository;
import com.example.azolotarev.test.Service.PresenterManager;
import com.example.azolotarev.test.UI.Authorization.AuthorizationFragment;
import com.example.azolotarev.test.UI.Authorization.AuthorizationPresenter;
import com.example.azolotarev.test.UI.GenericFragmentActivity;

public class StartActivity extends GenericFragmentActivity {

    @Override
    protected Fragment createFragment() {
        Log.d("TAG","createFragment start ");
        AuthorizationFragment fragment=AuthorizationFragment.newInstance();
        PresenterManager.addPresenter(new AuthorizationPresenter(new AuthorizationInteractor(new Repository(PersistentStorage.get(),new Net(new Connect())))),
                fragment.getClass().getName());
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
        Log.d("TAG", "!!! fragment size"+getSupportFragmentManager().getFragments().size());
        for(Fragment fragment:getSupportFragmentManager().getFragments()){
            Log.d("TAG", "!!! fragment "+fragment+" tag "+fragment.getTag());
        }
        PresenterManager.print();
    }
}
