package com.example.azolotarev.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class DepartmentListFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static DepartmentListFragment newInstance() {
        return new DepartmentListFragment();
    }
}
