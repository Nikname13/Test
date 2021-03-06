package com.example.azolotarev.test.UI.Main.EmployeePage.LargeImage;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import com.example.azolotarev.test.R;
import com.example.azolotarev.test.Service.PresenterManager;

public class LargeImageFragment extends Fragment implements LargeImageContract.View {

    public static final String ARG_EMPLOYEE ="employee_id";
    private LargeImageContract.Presenter mPresenter;
    private ImageView mImageView;

    public static LargeImageFragment newInstance(@NonNull String id){
        Bundle arg=new Bundle();
        arg.putString(ARG_EMPLOYEE,id);
        LargeImageFragment fragment=new LargeImageFragment();
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter((LargeImageContract.Presenter) PresenterManager.getPresenter(this.getClass().getName()));
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.setPhotoId(getArguments().getString(ARG_EMPLOYEE));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_large_image,container,false);
        mImageView=v.findViewById(R.id.employee_avatar);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        mImageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.i("TAG","!!onGlobalLayout  "+mImageView.getWidth()+" "+mImageView.getHeight());
                mPresenter.loadPhoto(mImageView.getWidth(), mImageView.getHeight());
                mImageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        mPresenter.bindView(this);
        return v;
    }

    @Override
    public void setAvatar(@NonNull Bitmap avatar) {
        mImageView.setImageBitmap(avatar);
    }

    @Override
    public void setPresenter(@NonNull LargeImageContract.Presenter presenter) {
        mPresenter=presenter;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
    @Override
    public void onStop() {
        super.onStop();
        Log.e("TAG","largeImage onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("TAG","largeImage onDestroy");
        if(!isRemoving()) mPresenter.unbindView();
        else mPresenter.destroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("TAG","largeImage onDetach "+isRemoving());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("TAG","largeImage onDestroyView");
    }

}
