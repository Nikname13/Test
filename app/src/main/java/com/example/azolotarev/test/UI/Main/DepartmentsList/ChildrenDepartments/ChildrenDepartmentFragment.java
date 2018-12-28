package com.example.azolotarev.test.UI.Main.DepartmentsList.ChildrenDepartments;

import android.content.Context;
import android.net.ConnectivityManager;
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
import com.example.azolotarev.test.Data.Local.PersistentStorage;
import com.example.azolotarev.test.Data.Net.Connect;
import com.example.azolotarev.test.Data.Net.Net;
import com.example.azolotarev.test.Domain.DepartmentsList.DepartmentInteractor;
import com.example.azolotarev.test.Model.BaseModel;
import com.example.azolotarev.test.R;
import com.example.azolotarev.test.Repository.Repository;
import com.example.azolotarev.test.UI.Main.DepartmentsList.DepartmentsAdapter;

import java.io.Serializable;
import java.util.List;

public class ChildrenDepartmentFragment extends Fragment implements ChildrenDepartmentContract.View {

    private ChildrenDepartmentContract.Presenter mPresenter;
    private RecyclerView mRecyclerViewChildren;
    private DepartmentsAdapter mDepartmentsAdapterChildren;
    private static final String ARG_OBJECT ="department_object";
    private static final String ARG_VIEW_TYPE="view_type";


    public static ChildrenDepartmentFragment newInstance(List<Object> departments, int viewType){
        Bundle arg=new Bundle();
        arg.putSerializable(ARG_OBJECT, (Serializable) departments);
        arg.putInt(ARG_VIEW_TYPE,viewType);
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
        View v=inflater.inflate(R.layout.fragment_list, container,false);
        mRecyclerViewChildren=(RecyclerView)v.findViewById(R.id.departments_recycler_view);
        mRecyclerViewChildren.setLayoutManager(new LinearLayoutManager(getActivity()));
        return v;
    }

    @Override
    public List<Object> getListModel() {
        Log.e("TAG","children department fragment getListModel size ");
        return (List<Object>) getArguments().getSerializable(ARG_OBJECT);
    }

    @Override
    public int getViewType() {
        return getArguments().getInt(ARG_VIEW_TYPE);
    }

    @Override
    public void showListModel(List<Object> listModel, int viewType) {
        Log.e("TAG","children department fragment showListModel size " +listModel.size());
        mDepartmentsAdapterChildren=new DepartmentsAdapter(listModel, getActivity(), this, viewType);
        mRecyclerViewChildren.setAdapter(mDepartmentsAdapterChildren);
    }

    @Override
    public void showChildrenList(@NonNull List<Object> departmentList, @NonNull int containerId, @NonNull int viewType) {
        ChildrenDepartmentFragment fragment = ChildrenDepartmentFragment.newInstance(departmentList,viewType);
        new ChildrenDepartmentPresenter(fragment, new DepartmentInteractor(new Repository(PersistentStorage.init(getActivity().getApplicationContext()),
                new Net(new Connect(),
                        (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE)))));
        getActivity().getSupportFragmentManager().beginTransaction().replace(containerId, fragment, String.valueOf(containerId)).commit();
    }


    @Override
    public void setPresenter(@NonNull ChildrenDepartmentContract.Presenter presenter) {
        mPresenter=presenter;
    }

    @Override
    public void onClickItem(@NonNull BaseModel model, @NonNull int containerId) {
        Log.e("TAG","children department fragment click "+model.getName()+" id "+containerId);
        mPresenter.openDetail(model,containerId);
    }

    @Override
    public void removeFragment(@NonNull int containerId) {
        Log.e("TAG","!! children departments list fragment removeFragment container id= "+ getActivity().getSupportFragmentManager().findFragmentByTag(String.valueOf(containerId)));
        if(getActivity().getSupportFragmentManager().findFragmentByTag(String.valueOf(containerId))!=null){
            getActivity().getSupportFragmentManager().beginTransaction().remove(getActivity().getSupportFragmentManager().findFragmentByTag(String.valueOf(containerId))).commit();
            Log.e("TAG","!!!children departments list fragment after remove container id= "+ getActivity().getSupportFragmentManager().findFragmentByTag(String.valueOf(containerId)));
        }
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
