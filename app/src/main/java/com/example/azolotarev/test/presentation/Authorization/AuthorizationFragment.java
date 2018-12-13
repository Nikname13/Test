package com.example.azolotarev.test.presentation.Authorization;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.example.azolotarev.test.R;
import com.example.azolotarev.test.Service.PersistentStorage;
import com.example.azolotarev.test.presentation.DepartmentsList.DepartmentListActivity;


public class AuthorizationFragment extends Fragment implements AuthorizationContract.View {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters


    private Callback mListener;
    private AuthorizationContract.Presenter mPresenter;

    private Button mLogInButton;
    private EditText mLoginField, mPasswordField;

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
        initLogInButton(v);
        return v;
    }

    private void initLogInButton( View v) {
        mLogInButton=(Button)v.findViewById(R.id.button_log_in);
        mLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(!mLoginField.getText().toString().trim().isEmpty() && !mPasswordField.getText().toString().trim().isEmpty()){
                   mListener.onAuthorization(mLoginField.getText().toString(), mPasswordField.getText().toString());
               }
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Callback) {
            mListener = (Callback) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showSuccess() {
        //Snackbar snackbar=Snackbar.make()
    }

    @Override
    public void showDepartmentsList() {
        Intent intent = new Intent(getContext(), DepartmentListActivity.class);
        startActivity(intent);
    }

    @Override
    public void setPresenter(@NonNull AuthorizationContract.Presenter presenter) {
        mPresenter=presenter;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface Callback {
        void onAuthorization(String login, String password);
    }
}
