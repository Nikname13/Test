package com.example.azolotarev.test;

import android.support.v4.app.Fragment;
import android.util.Log;
import com.example.azolotarev.test.Iteractor.Iteractor;
import com.example.azolotarev.test.Service.ErrorLab;
import com.example.azolotarev.test.Service.PersistentStorage;

public class MainActivity extends GenericFragmentActivity implements AuthorizationFragment.Callback {

    @Override
    protected Fragment createFragment() {
      return logIn();
    }

    @Override
    public void onAuthorization(String login, String password) {
        if(new Iteractor().isAuth(login, password)) {
            PersistentStorage.addCredentials(login, password);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,DepartmentListFragment.newInstance()).commit();
            //wellDone();
        }else{
            ErrorLab.errorMessage();
            //authorization();
        }
    }

    private Fragment logIn(){
        PersistentStorage.init(this);
        if(!PersistentStorage.getLOGIN().isEmpty()){
            Log.d("TAG", "login "+PersistentStorage.getLOGIN()+"password "+PersistentStorage.getPASSWORD());
            return new DepartmentListFragment();
        }else{
           return new AuthorizationFragment();
        }
    }
}
