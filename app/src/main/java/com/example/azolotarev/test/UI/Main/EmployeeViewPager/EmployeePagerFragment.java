package com.example.azolotarev.test.UI.Main.EmployeeViewPager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.*;
import com.example.azolotarev.test.Data.Local.PersistentStorage;
import com.example.azolotarev.test.Data.Net.Connect;
import com.example.azolotarev.test.Data.Net.Net;
import com.example.azolotarev.test.Domain.Authorization.AuthorizationInteractor;
import com.example.azolotarev.test.Domain.EmployeePage.EmployeeInteractor;
import com.example.azolotarev.test.Model.MapModel;
import com.example.azolotarev.test.R;
import com.example.azolotarev.test.Repository.Repository;
import com.example.azolotarev.test.Service.PresenterManager;
import com.example.azolotarev.test.UI.Authorization.AuthorizationFragment;
import com.example.azolotarev.test.UI.Authorization.AuthorizationPresenter;
import com.example.azolotarev.test.UI.Main.EmployeePage.EmployeeFragment;
import com.example.azolotarev.test.UI.Main.EmployeePage.EmployeePresenter;

import java.util.List;

public class EmployeePagerFragment extends Fragment implements EmployeePagerContract.View {

    private EmployeePagerContract.Presenter mPresenter;
    private ViewPager mViewPager;
    private static final String ARG_EMPLOYEE_POSITION ="employee_position";
    private static final String ARG_EMPLOYEE_ID ="employee_id";
    private Toolbar mToolbar;


    public static EmployeePagerFragment newInstance(@NonNull String position, @NonNull String id){
        Bundle arg=new Bundle();
        arg.putString(ARG_EMPLOYEE_POSITION,position);
        arg.putString(ARG_EMPLOYEE_ID,id);
        EmployeePagerFragment fragment=new EmployeePagerFragment();
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setPresenter((EmployeePagerContract.Presenter) PresenterManager.getPresenter(this.getClass().getName()));
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("TAG","onResume pager");
        mPresenter.start(getArguments().getString(ARG_EMPLOYEE_POSITION),getArguments().getString(ARG_EMPLOYEE_ID));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("TAG","onCreateView pager");
        View v=inflater.inflate(R.layout.fragment_employee_pager,container,false);
        mToolbar=v.findViewById(R.id.employee_toolbar);
        mToolbar.setTitle("");
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        mViewPager=v.findViewById(R.id.employee_pager);
        mPresenter.bindView(this);
        return v;

    }

    @Override
    public void initViewPager(@NonNull final List<MapModel> mapModelList, int startPosition) {
        mPresenter.hashCode();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                Log.d("TAG","initViewPager getItem "+i);
                EmployeeFragment fragment=EmployeeFragment.newInstance(mapModelList.get(i).getId());
                Log.d("TAG","presenterManager get presenter "+fragment.getClass().getName()+mapModelList.get(i).getId());
                if(PresenterManager.getPresenter(fragment.getClass().getName()+mapModelList.get(i).getId())==null) {
                    Log.d("TAG","initViewPager fragment.getClass().getName() "+fragment.getClass().getName());
                    PresenterManager.addPresenter(new EmployeePresenter(new EmployeeInteractor(
                                    new Repository(PersistentStorage.get(),
                                            new Net(new Connect())))),
                            fragment.getClass().getName()+mapModelList.get(i).getId());
                }
                return fragment;
            }

            @Override
            public int getCount() {
                return mapModelList.size();
            }
        });
        mViewPager.setCurrentItem(startPosition);
    }


    @Override
    public void showAuthorization() {
        AuthorizationFragment fragment=AuthorizationFragment.newInstance();
        PresenterManager.addPresenter(new AuthorizationPresenter(new AuthorizationInteractor(new Repository(PersistentStorage.get(),new Net(new Connect())))),
                fragment.getClass().getName());
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_employee,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_log_out:
                mPresenter.logOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void setPresenter(@NonNull EmployeePagerContract.Presenter presenter) {
        mPresenter=presenter;
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("TAG","onStop pager");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("TAG","onDestroy pager");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("TAG","onDetach pager");
        mPresenter.unbindView();
    }

}
