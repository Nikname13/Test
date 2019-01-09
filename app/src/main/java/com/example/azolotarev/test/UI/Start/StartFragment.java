package com.example.azolotarev.test.UI.Start;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.example.azolotarev.test.Data.Local.PersistentStorage;
import com.example.azolotarev.test.Data.Net.Connect;
import com.example.azolotarev.test.Data.Net.Net;
import com.example.azolotarev.test.Domain.Authorization.AuthorizationInteractor;
import com.example.azolotarev.test.Domain.DepartmentsList.DepartmentInteractor;
import com.example.azolotarev.test.R;
import com.example.azolotarev.test.Repository.Repository;
import com.example.azolotarev.test.UI.Authorization.AuthorizationFragment;
import com.example.azolotarev.test.UI.Authorization.AuthorizationPresenter;
import com.example.azolotarev.test.UI.Main.DepartmentsListRoot.DepartmentListFragment;
import com.example.azolotarev.test.UI.Main.DepartmentsListRoot.DepartmentListPresenter;

public class StartFragment extends Fragment implements StartContract.View {

    private StartContract.Presenter mPresenter;
    private FrameLayout mFrameLayout;
    private CardView mProgress;
    private static final String ARG_SUCCESS="success";
    private boolean mSuccess;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSuccess=getArguments().getBoolean(ARG_SUCCESS,false);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start(mSuccess);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_main, container, false);
        mFrameLayout=(FrameLayout)v.findViewById(R.id.frameLayout);
        mProgress=(CardView)v.findViewById(R.id.progress_card);
        return v;
    }

    public static StartFragment newInstance(@NonNull boolean success) {
        Bundle arg=new Bundle();
        arg.putBoolean(ARG_SUCCESS,success);
        StartFragment fragment=new StartFragment();
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    public void showConnectionError(String errorMessage) {
        Snackbar snackbar=Snackbar.make(mFrameLayout,errorMessage,Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void showSuccessError(String errorMessage) {
        Snackbar snackbar=Snackbar.make(mFrameLayout,errorMessage,Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void showDepartmentsList() {
        DepartmentListFragment fragment=DepartmentListFragment.newInstance();
        new DepartmentListPresenter(fragment, new DepartmentInteractor(new Repository(PersistentStorage.init(getActivity().getApplicationContext()),new Net(new Connect(),(ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE)))));
        FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
       // transaction.addToBackStack(null);
        transaction.replace(R.id.fragmentContainer,fragment).commit();
    }

    @Override
    public void showAuthorization() {
        AuthorizationFragment fragment=AuthorizationFragment.newInstance();
        new AuthorizationPresenter(fragment,new AuthorizationInteractor(new Repository(PersistentStorage.init(getActivity().getApplicationContext()),new Net(new Connect(),(ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE)))));
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
    }

    @Override
    public void showProgress() {
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgress.setVisibility(View.GONE);
    }

    @Override
    public void setPresenter(@NonNull StartContract.Presenter presenter) {
        mPresenter=presenter;
    }
}
