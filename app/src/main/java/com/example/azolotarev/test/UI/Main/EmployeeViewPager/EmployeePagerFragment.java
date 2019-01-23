package com.example.azolotarev.test.UI.Main.EmployeeViewPager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.azolotarev.test.Data.Local.PersistentStorage;
import com.example.azolotarev.test.Data.Net.Connect;
import com.example.azolotarev.test.Data.Net.Net;
import com.example.azolotarev.test.Domain.EmployeePage.EmployeeInteractor;
import com.example.azolotarev.test.Model.MapModel;
import com.example.azolotarev.test.R;
import com.example.azolotarev.test.Repository.Repository;
import com.example.azolotarev.test.Service.PresenterManager;
import com.example.azolotarev.test.UI.Main.EmployeePage.EmployeeFragment;
import com.example.azolotarev.test.UI.Main.EmployeePage.EmployeePresenter;

import java.util.List;

public class EmployeePagerFragment extends Fragment implements EmployeePagerContract.View {

    private EmployeePagerContract.Presenter mPresenter;
    private ViewPager mViewPager;
    private static final String ARG_EMPLOYEE ="employee_id";

    public static EmployeePagerFragment newInstance(@NonNull String position){
        Bundle arg=new Bundle();
        arg.putString(ARG_EMPLOYEE,position);
        EmployeePagerFragment fragment=new EmployeePagerFragment();
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter((EmployeePagerContract.Presenter) PresenterManager.getPresenter(this.getClass().getName()));
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start(getArguments().getString(ARG_EMPLOYEE));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_employee_pager,container,false);
        mViewPager=v.findViewById(R.id.employee_pager);
        mPresenter.bindView(this);
        return v;

    }

    @Override
    public void initViewPager(@NonNull final List<MapModel> mapModelList) {
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                EmployeeFragment fragment=EmployeeFragment.newInstance(mapModelList.get(i).getId());
                if(PresenterManager.getPresenter(fragment.getClass().getName())==null) {
                    PresenterManager.addPresenter(new EmployeePresenter(new EmployeeInteractor(
                                    new Repository(PersistentStorage.get(),
                                            new Net(new Connect())))),
                            fragment.getClass().getName());
                }
                return fragment;
            }

            @Override
            public int getCount() {
                return mapModelList.size();
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
    public void setPresenter(@NonNull EmployeePagerContract.Presenter presenter) {
        mPresenter=presenter;
    }

}
