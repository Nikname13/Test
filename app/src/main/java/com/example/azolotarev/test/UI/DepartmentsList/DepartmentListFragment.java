package com.example.azolotarev.test.UI.DepartmentsList;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.azolotarev.test.Model.DepartmentModel;
import com.example.azolotarev.test.R;
import com.example.azolotarev.test.UI.Start.StartContract;

import java.util.List;

public class DepartmentListFragment extends Fragment implements DepartmentListContract.View {

    private DepartmentListContract.Presenter mPresenter;
    private TextView mTextView;

    public static DepartmentListFragment newInstance() {
        return new DepartmentListFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_departments_list,container,false);
        mTextView=(TextView)v.findViewById(R.id.textViewDepartments);
        return v;
    }

    @Override
    public void showConnectionError(String errorMessage) {

    }

    @Override
    public void showSuccessError(String errorMessage) {

    }

    @Override
    public void showAuthorization() {

    }

    @Override
    public void showDepartmentsList(List<DepartmentModel> departmentList) {
        String list="";
        for(DepartmentModel departmentModel:departmentList){
            list.concat("id "+departmentModel.getId()+" "+departmentModel.getName()+"\n");
        }
        mTextView.setText(list);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void setPresenter(@NonNull DepartmentListContract.Presenter presenter) {
        mPresenter=presenter;
    }
}
