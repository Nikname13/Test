package com.example.azolotarev.test.UI.Main.EmployeePage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.azolotarev.test.Data.Local.PersistentStorage;
import com.example.azolotarev.test.Data.Net.Connect;
import com.example.azolotarev.test.Data.Net.Net;
import com.example.azolotarev.test.Domain.EmployeePage.EmployeeInteractor;
import com.example.azolotarev.test.R;
import com.example.azolotarev.test.Repository.Repository;
import com.example.azolotarev.test.Service.PresenterManager;
import com.example.azolotarev.test.UI.Main.EmployeePage.LargeImage.LargeImageFragment;
import com.example.azolotarev.test.UI.Main.EmployeePage.LargeImage.LargeImagePresenter;

public class EmployeeFragment extends Fragment implements EmployeeContract.View {

    private EmployeeContract.Presenter mPresenter;
    public static final String ARG_EMPLOYEE ="employee_id";
    private ImageView mAvatar;
    private TextView mTitle, mName, mPhone, mEmail;
    private LinearLayout mTitleContainer, mNameContainer, mPhoneContainer, mEmailContainer, mEmployeeContainer;
    private boolean mIsImageFitToScreen;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TAG","Employee fragment create"+savedInstanceState);

        setPresenter((EmployeeContract.Presenter) PresenterManager.getPresenter(this.getClass().getName()+getArguments().getString(ARG_EMPLOYEE)));
    }

    public static EmployeeFragment newInstance(@NonNull String id){
        Bundle arg=new Bundle();
        arg.putString(ARG_EMPLOYEE,id);
        EmployeeFragment fragment=new EmployeeFragment();
        fragment.setArguments(arg);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.employee_detail,container,false);
        mEmployeeContainer =v.findViewById(R.id.employee_detail_container);
        mTitle=v.findViewById(R.id.employee_title_textView);
        mTitleContainer=v.findViewById(R.id.employee_title);
        mName=v.findViewById(R.id.employee_name_textView);
        mNameContainer=v.findViewById(R.id.employee_name);
        mPhone=v.findViewById(R.id.employee_phone_textView);
        mPhoneContainer=v.findViewById(R.id.employee_phone);
        mPhoneContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.callNumber(mPhone.getText().toString());
            }
        });
        mEmail=v.findViewById(R.id.employee_email_textView);
        mEmailContainer=v.findViewById(R.id.employee_email);
        mEmailContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.sendEmail(mEmail.getText().toString());
            }
        });
        mAvatar =v.findViewById(R.id.employee_avatar);
        mAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.showLargeImage();
            }
        });
        mPresenter.bindView(this);
        Log.d("TAG","onCreateView onResume "+getArguments().getString(ARG_EMPLOYEE));
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("TAG","employee onResume");
        mPresenter.start(getArguments().getString(ARG_EMPLOYEE));
    }

    @Override
    public void setPresenter(@NonNull EmployeeContract.Presenter presenter) {
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
    public void setAvatar(Bitmap avatar) {
        mAvatar.setImageBitmap(avatar);
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
    public void callNumber(String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+number));
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }else{
            Snackbar snackbar=Snackbar.make(mEmployeeContainer,"Нет необходимого приложения",Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
    }

    @Override
    public void sendEmail(String email) {
        Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
        sendIntent.setData(Uri.parse("mailto:"));
        sendIntent.putExtra(Intent.EXTRA_EMAIL  , new String[] {email});
        if (sendIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(sendIntent);
        }else{
            Snackbar snackbar=Snackbar.make(mEmployeeContainer,"Нет необходимого приложения",Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
    }

    @Override
    public void showLargeImage(@NonNull String id) {
        LargeImageFragment fragment=LargeImageFragment.newInstance(id);
        if(PresenterManager.getPresenter(fragment.getClass().getName())==null) {
            PresenterManager.addPresenter(new LargeImagePresenter(new EmployeeInteractor(
                            new Repository(PersistentStorage.get(),
                                    new Net(new Connect())))),
                    fragment.getClass().getName());
        }
        FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.add(R.id.fragmentContainer, fragment).commit();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("TAG","employee onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("TAG","employee onStop");
        getActivity().getSupportFragmentManager().beginTransaction().remove(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("TAG","employee onDestroy");
        mPresenter.unbindView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("TAG","employee onDetach");
    }
}
