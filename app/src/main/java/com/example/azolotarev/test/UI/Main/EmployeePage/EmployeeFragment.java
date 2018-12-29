package com.example.azolotarev.test.UI.Main.EmployeePage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.example.azolotarev.test.Model.EmployeeModel;
import com.example.azolotarev.test.R;

public class EmployeeFragment extends Fragment implements EmployeePageContract.View {

    private EmployeePageContract.Presenter mPresenter;
    private static final String ARG_EMPLOYEE_OBJECT="employee_object";

    public static EmployeeFragment newInstance(EmployeeModel employeeModel){
        Bundle arg=new Bundle();
        arg.putSerializable(ARG_EMPLOYEE_OBJECT,employeeModel);
        EmployeeFragment fragment=new EmployeeFragment();
        fragment.setArguments(arg);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.employee_detail,container,false);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setPresenter(@NonNull EmployeePageContract.Presenter presenter) {
        mPresenter=presenter;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
