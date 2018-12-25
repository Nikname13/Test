package com.example.azolotarev.test.UI.DepartmentsList.RootDepartments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import com.example.azolotarev.test.Model.DepartmentModel;
import com.example.azolotarev.test.R;
import com.example.azolotarev.test.UI.DepartmentsList.DepartmentsAdapter;

import java.util.ArrayList;
import java.util.List;

public class DepartmentListFragment extends Fragment implements DepartmentListContract.View {

    private DepartmentListContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private DepartmentsAdapter mDepartmentsAdapter;
    private RelativeLayout mRelativeLayout;

    public static DepartmentListFragment newInstance() {
        return new DepartmentListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_departments_list,container,false);
        mRecyclerView=(RecyclerView)v.findViewById(R.id.departments_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRelativeLayout=(RelativeLayout)v.findViewById(R.id.departments_layout);
        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
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
        mDepartmentsAdapter=new DepartmentsAdapter(departmentList, getActivity(),this);
        mRecyclerView.setAdapter(mDepartmentsAdapter);
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

    @Override
    public void onClickItem(DepartmentModel department) {
        mPresenter.openDepartmentDetail(department);
        Snackbar snackbar=Snackbar.make(mRelativeLayout,department.getName(),Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}
