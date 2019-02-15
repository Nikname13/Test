package com.example.azolotarev.test.UI.Main.EmployeePage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.*;
import com.example.azolotarev.test.R;
import com.example.azolotarev.test.Service.PresenterManager;
import com.example.azolotarev.test.Service.Router;

public class EmployeeFragment extends Fragment implements EmployeeContract.View {

    private EmployeeContract.Presenter mPresenter;
    public static final String ARG_EMPLOYEE ="employee_id";
    public static final String ARG_PHOTO ="photo_id";
    private ImageView mAvatarView;
    private TextView mTitle, mName, mPhone, mEmail;
    private RelativeLayout mDetailContainer;
    private LinearLayout mTitleContainer, mNameContainer, mPhoneContainer, mEmailContainer, mEmployeeContainer;
    private ProgressBar mProgressDetail, mProgressImage;
    private boolean mLoadImage=false;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setPresenter((EmployeeContract.Presenter) PresenterManager.getPresenter(this.getClass().getName()+getArguments().getString(ARG_EMPLOYEE)));
    }

    public static EmployeeFragment newInstance(@NonNull String id, @NonNull String photoId){
        Bundle arg=new Bundle();
        arg.putString(ARG_EMPLOYEE, id);
        arg.putString(ARG_PHOTO, photoId);
        EmployeeFragment fragment=new EmployeeFragment();
        fragment.setArguments(arg);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.employee_detail,container,false);
        mPresenter.bindView(this);
        mDetailContainer=v.findViewById(R.id.employee_detail_container);
        mProgressDetail=v.findViewById(R.id.progress_detail);
        mProgressImage=v.findViewById(R.id.progress_photo);
        mEmployeeContainer =v.findViewById(R.id.employee_detail_layout);
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
        mAvatarView =v.findViewById(R.id.employee_avatar);
        mAvatarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mLoadImage)mPresenter.showLargeImage();
            }
        });
        mAvatarView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(!mLoadImage) {
                    mPresenter.loadPhoto(mAvatarView.getWidth(), mAvatarView.getHeight());
                    mLoadImage=true;
                }

            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
       // Log.e("TAG","!!employee onResume");
        mPresenter.setPhotoId(getArguments().getString(ARG_PHOTO));
        mPresenter.setItemId(getArguments().getString(ARG_EMPLOYEE));
        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull EmployeeContract.Presenter presenter) {
        mPresenter=presenter;
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
    public void setAvatarView(Bitmap avatarView) {
        mAvatarView.setImageBitmap(avatarView);
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
            mPresenter.errorIntent();
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
            mPresenter.errorIntent();
        }
    }

    @Override
    public void showLargeImage(@NonNull String id) {
        Router.showLargeImage(getActivity(), id);
    }

    @Override
    public void showError(@NonNull String errorMessage) {
        Snackbar snackbar=Snackbar.make(mDetailContainer,errorMessage,Snackbar.LENGTH_SHORT);
        snackbar.show();
    }


    @Override
    public void showProgress() {
        mProgressDetail.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgress() {
        mProgressDetail.setVisibility(View.GONE);

    }

    @Override
    public void showProgressImage() {
        mProgressImage.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressImage() {
        mProgressImage.setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("TAG","employee onPause "+getArguments().getString(ARG_EMPLOYEE));
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("TAG","employee onStop "+getArguments().getString(ARG_EMPLOYEE));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("TAG","employee onDestroy "+getArguments().getString(ARG_EMPLOYEE));
        if(!isRemoving()) mPresenter.unbindView();
        else mPresenter.destroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("TAG","employee onDetach "+getArguments().getString(ARG_EMPLOYEE) + isRemoving());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("TAG","employee onDestroyView "+getArguments().getString(ARG_EMPLOYEE));
    }

}
