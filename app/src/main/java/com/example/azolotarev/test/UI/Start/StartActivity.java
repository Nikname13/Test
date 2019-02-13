package com.example.azolotarev.test.UI.Start;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MenuItem;
import com.example.azolotarev.test.Data.Local.PersistentStorage;
import com.example.azolotarev.test.Data.Net.Connect;
import com.example.azolotarev.test.Data.Net.Net;
import com.example.azolotarev.test.Domain.Authorization.AuthorizationInteractor;
import com.example.azolotarev.test.Domain.DepartmentsList.DepartmentInteractor;
import com.example.azolotarev.test.Repository.Repository;
import com.example.azolotarev.test.Service.PresenterManager;
import com.example.azolotarev.test.UI.GenericFragmentActivity;

public class StartActivity extends GenericFragmentActivity {

    private boolean firstLoadSuccess=false;

    @Override
    protected Fragment createFragment() {
        Log.d("TAG","createFragment start ");
       StartFragment fragment=StartFragment.newInstance(firstLoadSuccess);
       new StartPresenter(fragment,new AuthorizationInteractor(
               new Repository(PersistentStorage.get(),
                       new Net(new Connect()))),
               new DepartmentInteractor(new Repository(PersistentStorage.get(),
                       new Net(new Connect()))));
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("TAG","onPause start ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("TAG","onStop start ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("TAG","onDestroy start ");
    }
}
