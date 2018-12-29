package com.example.azolotarev.test.UI.Main.DepartmentsListRoot;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.example.azolotarev.test.Data.Local.PersistentStorage;
import com.example.azolotarev.test.Data.Net.Connect;
import com.example.azolotarev.test.Data.Net.Net;
import com.example.azolotarev.test.Domain.DepartmentsList.DepartmentInteractor;
import com.example.azolotarev.test.Model.BaseModel;
import com.example.azolotarev.test.Model.DepartmentModel;
import com.example.azolotarev.test.R;
import com.example.azolotarev.test.Repository.Repository;
import com.example.azolotarev.test.UI.Main.ChildrenElements.ChildrenFragment;
import com.example.azolotarev.test.UI.Main.ChildrenElements.ChildrenPresenter;
import com.example.azolotarev.test.UI.Main.RecyclerListAdapter;


import java.util.List;

public class DepartmentListFragment extends Fragment implements DepartmentListContract.View {

    private DepartmentListContract.Presenter mPresenter;
    private RecyclerView mRecyclerViewRoot;
    private RecyclerListAdapter mDepartmentsAdapter;
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
        View v=inflater.inflate(R.layout.fragment_list,container,false);
        mRecyclerViewRoot =(RecyclerView)v.findViewById(R.id.departments_recycler_view);
        mRecyclerViewRoot.setLayoutManager(new LinearLayoutManager(getActivity()));
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
    public void showDepartmentsList(@NonNull List<BaseModel> departmentList,@NonNull int viewType) {
        mDepartmentsAdapter=new RecyclerListAdapter(departmentList, getActivity(),this, viewType);
        mDepartmentsAdapter.setHasStableIds(true);
        mRecyclerViewRoot.setAdapter(mDepartmentsAdapter);
    }

    @Override
    public void showDepartmentChildren(@NonNull List<BaseModel> departmentList,@NonNull int containerId, @NonNull int viewType) {
        Log.e("TAG","departmentlistfragment showChildrenList container "+containerId);
         ChildrenFragment fragment = ChildrenFragment.newInstance(departmentList,viewType);
            new ChildrenPresenter(fragment, new DepartmentInteractor(new Repository(PersistentStorage.init(getActivity().getApplicationContext()),
                    new Net(new Connect(),
                            (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE)))));
            getActivity().getSupportFragmentManager().beginTransaction().replace(containerId, fragment, String.valueOf(containerId)).commit();
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
    public void onClickItem(@NonNull BaseModel department, @NonNull int containerId) {
        Log.e("TAG","departments list fragment onClickItem container id= "+containerId);
        for(Fragment fragment:getActivity().getSupportFragmentManager().getFragments()){
            Log.e("TAG","departments list fragment onClickItem fragment tag "+fragment.toString());
        }
        mPresenter.openDepartmentDetail((DepartmentModel) department,containerId);
        Snackbar snackbar=Snackbar.make(mRelativeLayout,department.getName(),Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @Override
    public void removeFragment(@NonNull int containerId) {
        Log.e("TAG","!!departments list fragment removeFragment container id= "+ getActivity().getSupportFragmentManager().findFragmentByTag(String.valueOf(containerId)));
        if(getActivity().getSupportFragmentManager().findFragmentByTag(String.valueOf(containerId))!=null){
            getActivity().getSupportFragmentManager().beginTransaction().remove(getActivity().getSupportFragmentManager().findFragmentByTag(String.valueOf(containerId))).commit();
            Log.e("TAG","!!!departments list fragment after remove container id= "+ getActivity().getSupportFragmentManager().findFragmentByTag(String.valueOf(containerId)));
        }
    }


}
