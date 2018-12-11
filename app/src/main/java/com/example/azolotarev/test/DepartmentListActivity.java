package com.example.azolotarev.test;

import android.support.v4.app.Fragment;

public class DepartmentListActivity extends GenericFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new DepartmentListFragment();
    }
}
