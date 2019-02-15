package com.example.azolotarev.test.UI.Authorization;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.button.MaterialButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.example.azolotarev.test.R;
import com.example.azolotarev.test.Service.PresenterManager;
import com.example.azolotarev.test.Service.Router;


public class AuthorizationFragment extends Fragment implements AuthorizationContract.View {

    private AuthorizationContract.Presenter mPresenter;

    private MaterialButton mLogInButton;
    private EditText mLoginField, mPasswordField;
    private TextInputLayout mLoginInput, mPasswordInput;
    private FrameLayout mFrameLayout;
    private ProgressBar mProgress;
    private LinearLayout mContainerAuth;

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
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(false);
        Log.d("TAG","onCreateView auth ragment");
        View v=inflater.inflate(R.layout.fragment_authorization, container, false);
        mLoginInput=v.findViewById(R.id.login_input_layout);
        //mLoginInput.setError(getText(R.string.auth_error_message_empty));
        mLoginField=v.findViewById(R.id.login_field);
        mLoginField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count>0) mLoginInput.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mPasswordInput=v.findViewById(R.id.password_input_layout);
        //mPasswordInput.setError(getText(R.string.auth_error_message_empty));
        mPasswordField=v.findViewById(R.id.password_field);
        mPasswordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count>0) mPasswordInput.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mFrameLayout=v.findViewById(R.id.frameLayout);
        mProgress=v.findViewById(R.id.progress_bar);
        mContainerAuth =v.findViewById(R.id.container_log_in);
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
                if(mLoginField.getText().toString().trim().isEmpty()){
                    mLoginInput.setErrorEnabled(true);
                    mLoginInput.setError(getText(R.string.auth_error_message_empty));
                }
                if(mPasswordField.getText().toString().trim().isEmpty()){
                    mPasswordInput.setErrorEnabled(true);
                    mPasswordInput.setError(getText(R.string.auth_error_message_empty));
                }
               if(!mLoginField.getText().toString().trim().isEmpty() && !mPasswordField.getText().toString().trim().isEmpty()){
                   mPresenter.logIn(mLoginField.getText().toString(), mPasswordField.getText().toString());
                       Log.d("TAG","closeKeyboard");
                       InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                           inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),0);
                   }
               }
        });
    }

    @Override
    public void showProgress() {
        mProgress.setVisibility(View.VISIBLE);
        mContainerAuth.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        mProgress.setVisibility(View.GONE);
    }

    @Override
    public void showError(String errorMessage) {
        Snackbar snackbar=Snackbar.make(mFrameLayout,errorMessage,Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void showDepartmentsList() {
        Router.showDepartmentsList(getActivity());
    }

    @Override
    public void showAuthField() {
        mContainerAuth.setVisibility(View.VISIBLE);
    }

    @Override
    public void setPresenter(@NonNull AuthorizationContract.Presenter presenter) {
        mPresenter=presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
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
