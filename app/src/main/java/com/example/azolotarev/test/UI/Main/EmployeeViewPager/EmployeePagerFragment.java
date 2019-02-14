package com.example.azolotarev.test.UI.Main.EmployeeViewPager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
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
import com.example.azolotarev.test.Service.Router;
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
    private static final String ARG_FILTER ="employee_filter";
    private Toolbar mToolbar;

    public static EmployeePagerFragment newInstance(@NonNull String position, @NonNull String id, @NonNull String filterString){
        Bundle arg=new Bundle();
        arg.putString(ARG_EMPLOYEE_POSITION,position);
        arg.putString(ARG_EMPLOYEE_ID,id);
        arg.putString(ARG_FILTER,filterString);
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
       // Log.d("TAG","onResume pager position "+getArguments().getString(ARG_EMPLOYEE_POSITION)+" id "+getArguments().getString(ARG_EMPLOYEE_ID)+" filter "+getArguments().getString(ARG_FILTER));
        mPresenter.start(getArguments().getString(ARG_EMPLOYEE_POSITION),getArguments().getString(ARG_EMPLOYEE_ID),getArguments().getString(ARG_FILTER));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Log.d("TAG","onCreateView pager");
        View v=inflater.inflate(R.layout.fragment_employee_pager,container,false);
        mToolbar=v.findViewById(R.id.employee_toolbar);
        mToolbar.setTitle("");
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        mViewPager=v.findViewById(R.id.employee_pager);
        mPresenter.bindView(this);
        return v;

    }

    @Override
    public void initViewPager(@NonNull final List<MapModel> mapModelList, int startPosition) {
        //Log.d("TAG"," fm ");
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                Log.d("TAG","initViewPager loadItem "+i);
                EmployeeFragment fragment=EmployeeFragment.newInstance(mapModelList.get(i).getId(), mapModelList.get(i).getModel().getId());
                //Log.d("TAG","presenterManager get presenter "+fragment.getClass().getName()+mapModelList.get(i).getId());
                if(PresenterManager.getPresenter(fragment.getClass().getName()+mapModelList.get(i).getId())==null) {
                   // Log.d("TAG","!-! initViewPager fragment.getClass().getName() "+fragment.getClass().getName());
                    PresenterManager.addPresenter(new EmployeePresenter(new EmployeeInteractor(
                                    new Repository(PersistentStorage.get(),
                                            new Net(new Connect())))),
                            fragment.getClass().getName()+mapModelList.get(i).getId());
/*                    PresenterManager.print();
                    for(Fragment fragment1:getActivity().getSupportFragmentManager().getFragments()){
                        Log.d("TAG",fragment1.toString());
                    }*/
                }
                return fragment;
            }

            @Override
            public int getCount() {
                return mapModelList.size();
            }
        });
        mViewPager.setCurrentItem(startPosition);
        mPresenter.setTitlePage(mapModelList.get(startPosition).getModel().getParent().getName());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                mPresenter.setTitlePage(mapModelList.get(i).getModel().getParent().getName());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public void setTitlePage(@NonNull String name) {
        mToolbar.setTitle(name);
    }


    @Override
    public void logOut() {
        Router.showLogOut(getActivity());
    }

    @Override
    public void showLogOutMessage() {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.log_out_title)
                .setMessage(R.string.log_out_message)
                .setPositiveButton(R.string.positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.logOut();
                    }
                })
                .setNegativeButton(R.string.negative_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
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
                mPresenter.showLogOutMessage();
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
        Log.e("TAG","pager onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("TAG","pager onDestroy");
        if(!isRemoving()) mPresenter.unbindView();
        else{
            mPresenter.destroy();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("TAG","pager onDetach ");

    }


}
