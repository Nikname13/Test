package com.example.azolotarev.test.UI.Main.DepartmentsListRoot;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.example.azolotarev.test.Model.EmployeeModel;
import com.example.azolotarev.test.Model.RecyclerModel;
import com.example.azolotarev.test.R;
import com.example.azolotarev.test.UI.Main.RecyclerListAdapter;

import java.util.List;

public class DepartmentListFragment extends Fragment implements DepartmentListContract.View {

    private DepartmentListContract.Presenter mPresenter;
    private RecyclerView mRecyclerViewRoot;
    private RecyclerListAdapter mDepartmentsAdapter;
    private RelativeLayout mRelativeLayout;
    private int mRecyclerPosition;

    public static DepartmentListFragment newInstance() {
        return new DepartmentListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_list,container,false);
        mRecyclerViewRoot =v.findViewById(R.id.departments_recycler_view);
        mRecyclerViewRoot.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRelativeLayout=v.findViewById(R.id.departments_layout);
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

    }

    @Override
    public void showList(@NonNull List<Integer> list) {
        mDepartmentsAdapter=new RecyclerListAdapter(list, getActivity(),this);
        mRecyclerViewRoot.setAdapter(mDepartmentsAdapter);
        Log.d("TAG","position "+mRecyclerPosition);
        mRecyclerViewRoot.scrollToPosition(mRecyclerPosition);
    }

    @Override
    public void updateList(@NonNull List<Integer> recyclerModelList) {
        mDepartmentsAdapter.setList(recyclerModelList);
        mDepartmentsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmployeeDetail(@NonNull EmployeeModel model) {
        //запилить создание фрагмента EmployeePage
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
    public void onClickItem(@NonNull RecyclerModel model) {
        Log.d("TAG","departments list fragment onClickItem scroll(Y)= ");
        mPresenter.openElementDetail(model);
    }

    @Override
    public void itemInPosition(@NonNull final itemInPositionCallback callback, @NonNull int position) {
        mPresenter.itemInPosition(new itemInPositionCallback() {
            @Override
            public void onItem(@NonNull RecyclerModel model) {
                callback.onItem(model);
            }
        },
        position);
    }

    @Override
    public void scrollToPosition(@NonNull int position) {
       // mRecyclerViewRoot.scrollToPosition(position);
        Log.d("TAG","scrollToPosition "+position);
        mRecyclerPosition=position;
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
    }

    @Override
    public void scrollTo(@NonNull int position) {
        Log.e("TAG","scrollTo position= "+position);
        mRecyclerViewRoot.scrollBy(0,position*15);

    }
}
