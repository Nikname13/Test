package com.example.azolotarev.test.UI.Main.EmployeePage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.example.azolotarev.test.Model.EmployeeModel;
import com.example.azolotarev.test.R;

public class EmployeeFragment extends Fragment implements EmployeePageContract.View {

    private EmployeePageContract.Presenter mPresenter;
    private static final String ARG_EMPLOYEE_OBJECT="employee_object";
    private TextView mTitle, mName, mPhone, mEmail;
    private CardView mTitleContainer, mNameContainer, mPhoneContainer, mEmailContainer;

    public static EmployeeFragment newInstance(EmployeeModel employeeModel){
        Bundle arg=new Bundle();
        arg.putSerializable(ARG_EMPLOYEE_OBJECT,employeeModel);
        EmployeeFragment fragment=new EmployeeFragment();
        fragment.setArguments(arg);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.employee_detail,container,false);
        mTitle=(TextView)v.findViewById(R.id.employee_title_textView);
        mTitleContainer=(CardView)v.findViewById(R.id.employee_title);
        mName=(TextView)v.findViewById(R.id.employee_name_textView);
        mNameContainer=(CardView)v.findViewById(R.id.employee_name);
        mPhone=(TextView)v.findViewById(R.id.employee_phone_textView);
        mPhoneContainer=(CardView)v.findViewById(R.id.employee_phone);
        mEmail=(TextView)v.findViewById(R.id.employee_email_textView);
        mEmailContainer=(CardView)v.findViewById(R.id.employee_email);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull EmployeePageContract.Presenter presenter) {
        mPresenter=presenter;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void setTitle(String title) {
        mTitle.setText(title);
        mTitleContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void setName(String name) {
        mName.setText(name);
        mNameContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void setPhone(String phone) {
        mPhone.setText(phone);
        mPhoneContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void setEmail(String email) {
        mEmail.setText(email);
        mEmailContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void setPhoto() {

    }

    @Override
    public void hideTitle() {
        mTitleContainer.setVisibility(View.GONE);
    }

    @Override
    public void hideName() {
        mNameContainer.setVisibility(View.GONE);
    }

    @Override
    public void hidePhone() {
        mPhoneContainer.setVisibility(View.GONE);
    }

    @Override
    public void hideEmail() {
        mEmailContainer.setVisibility(View.GONE);
    }

    @Override
    public EmployeeModel getEmployee() {
        return (EmployeeModel) getArguments().getSerializable(ARG_EMPLOYEE_OBJECT);
    }
}
