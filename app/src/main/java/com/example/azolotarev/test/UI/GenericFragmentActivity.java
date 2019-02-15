package com.example.azolotarev.test.UI;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.example.azolotarev.test.R;
import com.example.azolotarev.test.Service.ContextManager;

public abstract class GenericFragmentActivity extends AppCompatActivity {
    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context =getApplicationContext();
        ContextManager.init(context);
     //   Log.d("TAG","onCreate start ");
        setContentView(R.layout.fragment_container);
        FragmentManager fm = getSupportFragmentManager();
    //    Log.d("TAG","start size "+fm.getFragments().size());
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }
    }
}
