package com.example.azolotarev.test.UI.Authorization;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import com.example.azolotarev.test.R;
import com.example.azolotarev.test.UI.DepartmentsList.DepartmentListActivity;


public class AuthorizationFragment extends Fragment implements AuthorizationContract.View {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters


    private AuthorizationContract.Presenter mPresenter;

    private Button mLogInButton;
    private EditText mLoginField, mPasswordField;
    private FrameLayout mFrameLayout;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_authorization, container, false);

        mLoginField=(EditText)v.findViewById(R.id.login_field);
        mPasswordField=(EditText)v.findViewById(R.id.password_field);
        mFrameLayout=(FrameLayout)v.findViewById(R.id.frameLayout);
        initLogInButton(v);
        return v;
    }

    private void initLogInButton( View v) {
        mLogInButton=(Button)v.findViewById(R.id.button_log_in);
        mLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(!mLoginField.getText().toString().trim().isEmpty() && !mPasswordField.getText().toString().trim().isEmpty()){
                   mPresenter.logIn(mLoginField.getText().toString(), mPasswordField.getText().toString());
               }
            }
        });
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showConnectionError() {

    }

    @Override
    public void showSuccessError(String errorMessage) {
        Snackbar snackbar=Snackbar.make(mFrameLayout,errorMessage,Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void showDepartmentsList() {
        Intent intent = new Intent(getContext(), DepartmentListActivity.class);
        intent.putExtra(DepartmentListActivity.EXTRA_SUCCESS,true);
        startActivity(intent);
    }

    @Override
    public void setPresenter(@NonNull AuthorizationContract.Presenter presenter) {
        mPresenter=presenter;
    }
}
