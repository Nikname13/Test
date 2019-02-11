package com.example.azolotarev.test.UI.Main.DepartmentsListRoot;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
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
    private ScrollChildSwipeRefreshLayout mSwipeRefreshLayout;
    private Toolbar mToolbar;
    private SearchView mSearchView;
    private MenuItem mSearchItem;
    private String mFilteredString;
    private static final String SEARCH_KEY="search_key";

    public static DepartmentListFragment newInstance() {
        return new DepartmentListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setHasOptionsMenu(true);
        setPresenter((DepartmentListContract.Presenter) PresenterManager.getPresenter(this.getClass().getName()));
        Log.d("TAG","onCreate rootdepartment");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("TAG","onCreate rootdepartment");
        View v=inflater.inflate(R.layout.fragment_list,container,false);
        mToolbar=v.findViewById(R.id.main_toolbar);
        mToolbar.setTitle("");
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        mRecyclerViewRoot =v.findViewById(R.id.departments_recycler_view);
        mRecyclerViewRoot.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCoordinatorLayout =v.findViewById(R.id.department_layout);
        mSwipeRefreshLayout=v.findViewById(R.id.refresh_layout);
        mSwipeRefreshLayout.setScrollUpChild(mRecyclerViewRoot);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("TAG","!!!ScrollChildSwipeRefreshLayout!!!");
                mPresenter.loadList(true);
            }
        });
        mPresenter.bindView(this);
        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.e("TAG","onResume rootdepartment "+getActivity().toString());
        mPresenter.start();
    }

    @Override
    public void showConnectionError(String errorMessage) {

    }

    @Override
    public void showSuccessError(String errorMessage) {

    }

    @Override
    public void logOut() {
        AuthorizationFragment fragment=AuthorizationFragment.newInstance();
        PresenterManager.addPresenter(new AuthorizationPresenter(new AuthorizationInteractor(new Repository(PersistentStorage.get(),new Net(new Connect())))),
        fragment.getClass().getName());
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
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
    public void showMessage(String message) {
        Snackbar snackbar=Snackbar.make(mCoordinatorLayout,message,Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @Override
    public void showList(@NonNull List<Integer> list) {
        Log.d("TAG","showList ----- "+mDepartmentsAdapter);
        if(mDepartmentsAdapter==null) {
            mDepartmentsAdapter = new RecyclerListAdapter(list, getActivity(), this);
            mRecyclerViewRoot.setAdapter(mDepartmentsAdapter);
        }else{
            mRecyclerViewRoot.setAdapter(mDepartmentsAdapter);
            updateList(list);
        }
       if(mSearchItem!=null)expandFilter();
    }

    @Override
    public void updateList(@NonNull List<Integer> recyclerModelList) {
        mDepartmentsAdapter.setList(recyclerModelList);
        mDepartmentsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmployeeDetail(@NonNull String positionInTree, @NonNull String id, String filter) {
        EmployeePagerFragment fragment=EmployeePagerFragment.newInstance(positionInTree,id,filter);
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
        Log.d("TAG","applyFilter ----- "+mDepartmentsAdapter);
        mDepartmentsAdapter.getFilter().filter(query);
    }

    @Override
    public void setFilterString(@NonNull String filterString) {
        mFilteredString=filterString;
    }

    @Override
    public void showProgress() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setPresenter(@NonNull DepartmentListContract.Presenter presenter) {
        mPresenter=presenter;
    }

    @Override
    public void onClickItem(@NonNull MapModel model) {
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
       // mFilteredString=null;
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
    public void onCreateOptionsMenu(Menu menu, final MenuInflater inflater) {
        Log.d("TAG","onCreateOptionsMenu rootdepartment "+ getActivity().getMenuInflater());
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
        mSearchItem=menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(ContextManager.getContext().SEARCH_SERVICE);
        mSearchView = (SearchView) mSearchItem.getActionView();
        mSearchView.setSearchableInfo(searchManager
                .getSearchableInfo(getActivity().getComponentName()));
        mSearchView.setMaxWidth(Integer.MAX_VALUE);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        if(mDepartmentsAdapter!=null) expandFilter();
    }

    private void expandFilter(){
        if(mFilteredString!=null && !mFilteredString.isEmpty()){
            Log.d("TAG","if(mFilteredString!=null && !mFilteredString.isEmpty() rootdepartment "+mSearchItem);
            mSearchItem.expandActionView();
            mSearchView.setQuery(mFilteredString,true);
            mSearchView.requestFocus();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_log_out:
                mPresenter.showLogOutMessage();
                return true;
            case R.id.action_search:
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

}
