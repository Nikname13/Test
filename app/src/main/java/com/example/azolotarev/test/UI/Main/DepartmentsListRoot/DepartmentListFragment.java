package com.example.azolotarev.test.UI.Main.DepartmentsListRoot;

import android.app.SearchManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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
import com.example.azolotarev.test.Service.ContextManager;
import com.example.azolotarev.test.Service.PresenterManager;
import com.example.azolotarev.test.UI.Authorization.AuthorizationFragment;
import com.example.azolotarev.test.UI.Authorization.AuthorizationPresenter;
import com.example.azolotarev.test.UI.Main.EmployeeViewPager.EmployeePagerFragment;
import com.example.azolotarev.test.UI.Main.EmployeeViewPager.EmployeePagerPresenter;
import com.example.azolotarev.test.UI.Main.RecyclerListAdapter;

import java.util.List;

public class DepartmentListFragment extends Fragment implements DepartmentListContract.View {

    private DepartmentListContract.Presenter mPresenter;
    private RecyclerView mRecyclerViewRoot;
    private RecyclerListAdapter mDepartmentsAdapter;
    private CoordinatorLayout mCoordinatorLayout;
    private Toolbar mToolbar;

    public static DepartmentListFragment newInstance() {
        return new DepartmentListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setHasOptionsMenu(true);
        setPresenter((DepartmentListContract.Presenter) PresenterManager.getPresenter(this.getClass().getName()));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_list,container,false);
        mToolbar=v.findViewById(R.id.main_toolbar);
        mToolbar.setTitle("");
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        mRecyclerViewRoot =v.findViewById(R.id.departments_recycler_view);
        mRecyclerViewRoot.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCoordinatorLayout =v.findViewById(R.id.departments_layout);
        mPresenter.bindView(this);
        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.e("TAG","onResume rootdepartment");
        mPresenter.start();
    }

    @Override
    public void showConnectionError(String errorMessage) {

    }

    @Override
    public void showSuccessError(String errorMessage) {

    }

    @Override
    public void showAuthorization() {
        AuthorizationFragment fragment=AuthorizationFragment.newInstance();
        PresenterManager.addPresenter(new AuthorizationPresenter(new AuthorizationInteractor(new Repository(PersistentStorage.get(),new Net(new Connect())))),
        fragment.getClass().getName());
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
    }

    @Override
    public void showList(@NonNull List<Integer> list) {
            mDepartmentsAdapter = new RecyclerListAdapter(list, getActivity(), this);
            mRecyclerViewRoot.setAdapter(mDepartmentsAdapter);
    }

    @Override
    public void updateList(@NonNull List<Integer> recyclerModelList) {
        mDepartmentsAdapter.setList(recyclerModelList);
        mDepartmentsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmployeeDetail(@NonNull String positionInTree, @NonNull String id) {
        EmployeePagerFragment fragment=EmployeePagerFragment.newInstance(positionInTree,id);
        if(PresenterManager.getPresenter(fragment.getClass().getName())==null) {
            PresenterManager.addPresenter(new EmployeePagerPresenter(new EmployeeInteractor(
                            new Repository(PersistentStorage.get(),
                                    new Net(new Connect())))),
                    fragment.getClass().getName());
        }
        FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.fragmentContainer, fragment).commit();
    }

    @Override
    public void applyFilter(@NonNull String query) {
        mDepartmentsAdapter.getFilter().filter(query);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void setPresenter(@NonNull DepartmentListContract.Presenter presenter) {
        mPresenter=presenter;
    }

    @Override
    public void onClickItem(@NonNull MapModel model) {
        Log.d("TAG","departments list fragment onClickItem scroll(Y)= ");
        mPresenter.openElementDetail(model);
    }

    @Override
    public void onFilter(@NonNull String filterString, @NonNull final RecyclerFilterCallback callback) {
        mPresenter.onFilter(filterString, new RecyclerFilterCallback() {
            @Override
            public void onResult(List<Integer> filteredList) {
                callback.onResult(filteredList);
            }
        });
    }

    @Override
    public void itemInPosition(@NonNull final ItemInPositionCallback callback, @NonNull int position) {
        mPresenter.itemInPosition(new ItemInPositionCallback() {
            @Override
            public void onItem(@NonNull MapModel model) {
                callback.onItem(model);
            }
        },
        position);
    }

    @Override
    public void scrollToPosition(@NonNull int position) {
       // mRecyclerViewRoot.scrollToPosition(position);
        Log.d("TAG","scrollToPosition "+position);
        //mRecyclerPosition=position;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("TAG","onPause rootdepartment");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("TAG","onStop rootdepartment");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("TAG","onDestroy rootdepartment");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("TAG","onDetach rootdepartment");
        mPresenter.unbindView();
    }

    @Override
    public void scrollTo(@NonNull int position) {
        Log.e("TAG","scrollTo position= "+position);
        mRecyclerViewRoot.scrollBy(0,position*15);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       // super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
        //inflater.inflate(R.menu.menu_main,menu);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(ContextManager.getContext().SEARCH_SERVICE);
       SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mPresenter.applyFilter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mPresenter.applyFilter(s);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_log_out:
                mPresenter.logOut();
                return true;
            case R.id.action_search:
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

}
