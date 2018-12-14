package com.example.azolotarev.test.UI.DepartmentsList;

import android.support.v4.app.Fragment;
import com.example.azolotarev.test.UI.GenericFragmentActivity;

public class DepartmentListActivity extends GenericFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new DepartmentListFragment();
    }
}
