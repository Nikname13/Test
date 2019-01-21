package com.example.azolotarev.test.UI.Authorization;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import com.example.azolotarev.test.Data.Local.PersistentStorage;
import com.example.azolotarev.test.Data.Net.Connect;
import com.example.azolotarev.test.Data.Net.Net;
import com.example.azolotarev.test.Domain.DepartmentsList.DepartmentInteractor;
import com.example.azolotarev.test.R;
import com.example.azolotarev.test.Repository.Repository;
import com.example.azolotarev.test.Service.PresenterManager;
import com.example.azolotarev.test.UI.Main.DepartmentsListRoot.DepartmentListFragment;
import com.example.azolotarev.test.UI.Main.DepartmentsListRoot.DepartmentListPresenter;


public class AuthorizationFragment extends Fragment implements AuthorizationContract.View {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PRESENTER = "presenter";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters


    private AuthorizationContract.Presenter mPresenter;

    private Button mLogInButton;
    private EditText mLoginField, mPasswordField;
    private FrameLayout mFrameLayout;
    private CardView mProgress;

    public AuthorizationFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AuthorizationFragment newInstance() {
        return new AuthorizationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter((AuthorizationContract.Presenter) PresenterManager.getPresenter(this.getClass().getName()));
        Log.d("TAG","onCreate auth ragment");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(false);
        Log.d("TAG","onCreateView auth ragment");
        View v=inflater.inflate(R.layout.fragment_authorization, container, false);
        mLoginField=v.findViewById(R.id.login_field);
        mPasswordField=v.findViewById(R.id.password_field);
        mFrameLayout=v.findViewById(R.id.frameLayout);
        mProgress=v.findViewById(R.id.progress_card);
        initLogInButton(v);
        mPresenter.bindView(this);
        return v;
    }

    private void initLogInButton( View v) {
        mLogInButton=v.findViewById(R.id.button_log_in);
        mLogInButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
               if(!mLoginField.getText().toString().trim().isEmpty() && !mPasswordField.getText().toString().trim().isEmpty()){
                   mPresenter.logIn(mLoginField.getText().toString(), mPasswordField.getText().toString());
                   mLoginField.clearFocus();
                   mPasswordField.clearFocus();
                   InputMethodManager inputMethodManager=(InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                   if(getActivity().getCurrentFocus()!=null)
                   inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
               }
            }
        });
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
        PresenterManager.addPresenter(new DepartmentListPresenter(new DepartmentInteractor(
                                new Repository(PersistentStorage.get(),new Net(new Connect())))),
                fragment.getClass().getName());
        FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer,fragment).commit();
        mPresenter.destroy();
    }

    @Override
    public void setPresenter(@NonNull AuthorizationContract.Presenter presenter) {
        mPresenter=presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("TAG","onResume auth");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("TAG","onPause auth");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("TAG","onStop auth");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("TAG","onDestroy auth");
        mPresenter.unbindView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("TAG","onDetach auth");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("TAG","onAttach auth");
    }
}
