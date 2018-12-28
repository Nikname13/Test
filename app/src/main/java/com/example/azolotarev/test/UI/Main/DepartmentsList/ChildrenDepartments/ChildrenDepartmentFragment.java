package com.example.azolotarev.test.UI.Main.DepartmentsList.ChildrenDepartments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.azolotarev.test.Domain.DepartmentsList.DepartmentInteractorContract;
import com.example.azolotarev.test.Model.DepartmentModel;
import com.example.azolotarev.test.R;
import com.example.azolotarev.test.UI.Main.DepartmentsList.DepartmentsAdapter;

import java.io.Serializable;
import java.util.List;

public class ChildrenDepartmentFragment extends Fragment implements ChildrenDepartmentContract.View {

    private ChildrenDepartmentContract.Presenter mPresenter;
    private RecyclerView mRecyclerViewChildren;
    private DepartmentsAdapter mDepartmentsAdapterChildren;
    private static final String ARG_DEPARTMENT_OBJECT="department_object";


    public static ChildrenDepartmentFragment newInstance(List<DepartmentModel> departments){
        Bundle arg=new Bundle();
        arg.putSerializable(ARG_DEPARTMENT_OBJECT, (Serializable) departments);
        ChildrenDepartmentFragment fragment=new ChildrenDepartmentFragment();
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("TAG","onResume");
        mPresenter.start();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_departments_list, container,false);
        mRecyclerViewChildren=(RecyclerView)v.findViewById(R.id.departments_recycler_view);
        mRecyclerViewChildren.setLayoutManager(new LinearLayoutManager(getActivity()));
        return v;
    }

    @Override
    public void setPresenter(@NonNull ChildrenDepartmentContract.Presenter presenter) {
        mPresenter=presenter;
    }

    @Override
    public void onClickItem(@NonNull DepartmentModel department,@NonNull int containerId) {
        Log.e("TAG","children department fragment click "+department.getName()+" id "+containerId);
    }

    @Override
    public List<DepartmentModel> getDepartments() {
        Log.e("TAG","children department fragment getDepartments size ");
        return (List<DepartmentModel>) getArguments().getSerializable(ARG_DEPARTMENT_OBJECT);
    }

    @Override
    public void showDepartmentsList(List<DepartmentModel> departmentList) {
        Log.e("TAG","children department fragment showDepartmentsList size " +departmentList.size());
        mDepartmentsAdapterChildren=new DepartmentsAdapter(departmentList, getActivity(), this);
        mRecyclerViewChildren.setAdapter(mDepartmentsAdapterChildren);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("TAG","onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("TAG","onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("TAG","onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("TAG","onDetach");
    }
}
