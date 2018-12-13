package com.example.azolotarev.test.presentation.DepartmentsList;

import android.support.v4.app.Fragment;
import com.example.azolotarev.test.presentation.GenericFragmentActivity;

public class DepartmentListActivity extends GenericFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new DepartmentListFragment();
    }
}
