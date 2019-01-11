package com.example.azolotarev.test.UI.Main.ChildrenElements;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.example.azolotarev.test.Domain.EmployeePage.EmployeeInteractor;
import com.example.azolotarev.test.Model.BaseModel;
import com.example.azolotarev.test.Model.EmployeeModel;
import com.example.azolotarev.test.R;
import com.example.azolotarev.test.Repository.Repository;
import com.example.azolotarev.test.Repository.RepositoryContract;
import com.example.azolotarev.test.UI.Main.EmployeePage.EmployeeFragment;
import com.example.azolotarev.test.UI.Main.EmployeePage.EmployeePresenter;
import com.example.azolotarev.test.UI.Main.RecyclerListAdapter;

import java.io.Serializable;
import java.util.List;

public class ChildrenFragment extends Fragment implements ChildrenContract.View {

    private ChildrenContract.Presenter mPresenter;
    private RecyclerView mRecyclerViewChildren;
    private RecyclerListAdapter mDepartmentsAdapterChildren;
    private static final String ARG_OBJECT ="base_model_object";
    private static final String ARG_VIEW_TYPE="view_type";


    public static ChildrenFragment newInstance(List<BaseModel> departments, int viewType){
        Bundle arg=new Bundle();
        arg.putSerializable(ARG_OBJECT, (Serializable) departments);
        arg.putInt(ARG_VIEW_TYPE,viewType);
        ChildrenFragment fragment=new ChildrenFragment();
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
        Log.d("TAG","CREATE CHILDREN FRAGMENT");
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
    public List<BaseModel> getListModel() {
        Log.e("TAG","children department fragment getListModel size ");
        return (List<BaseModel>) getArguments().getSerializable(ARG_OBJECT);
    }

    @Override
    public int getViewType() {
        return getArguments().getInt(ARG_VIEW_TYPE);
    }

    @Override
    public void showListModel(List<BaseModel> listModel, int viewType) {
        Log.e("TAG","children department fragment showListModel size " +listModel.size());
        mDepartmentsAdapterChildren=new RecyclerListAdapter(listModel, getActivity(), this, viewType);
        mRecyclerViewChildren.setAdapter(mDepartmentsAdapterChildren);
    }

    @Override
    public void showChildrenList(@NonNull List<BaseModel> departmentList, @NonNull int containerId, @NonNull int viewType) {
        ChildrenFragment fragment = ChildrenFragment.newInstance(departmentList,viewType);
        new ChildrenPresenter(fragment, new DepartmentInteractor(new Repository(PersistentStorage.init(getActivity().getApplicationContext()),
                new Net(new Connect(),
                        (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE)))));
        FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(containerId, fragment, String.valueOf(containerId)).commit();
    }

    @Override
    public void showChildrenDetail(@NonNull BaseModel model) {
        EmployeeFragment fragment=EmployeeFragment.newInstance((EmployeeModel) model);
        new EmployeePresenter(fragment,new EmployeeInteractor(
                new Repository(PersistentStorage.init(getActivity().getApplicationContext()),
                        new Net(new Connect(),(ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE)))));
        FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.fragmentContainer, fragment).commit();
    }


    @Override
    public void setPresenter(@NonNull ChildrenContract.Presenter presenter) {
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
        Log.e("TAG","onPause children");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("TAG","onStop children");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("TAG","onDestroy children");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("TAG","onDetach children");
    }
}
