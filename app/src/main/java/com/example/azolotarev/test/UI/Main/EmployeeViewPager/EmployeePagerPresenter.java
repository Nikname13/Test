package com.example.azolotarev.test.UI.Main.EmployeeViewPager;

import android.support.annotation.NonNull;
import android.util.Log;
import com.example.azolotarev.test.Data.Local.ListCache;
import com.example.azolotarev.test.Domain.DepartmentsList.DepartmentInteractorContract;
import com.example.azolotarev.test.Domain.EmployeePage.EmployeeInteractorContract;
import com.example.azolotarev.test.Model.MapModel;
import com.example.azolotarev.test.Service.PresenterManager;
import com.example.azolotarev.test.UI.BaseView;

import java.util.List;

public class EmployeePagerPresenter implements EmployeePagerContract.Presenter {

    private EmployeeInteractorContract mInteractor;
    private EmployeePagerContract.View mView;
    private List<MapModel> mMapModelList;

    public EmployeePagerPresenter(@NonNull EmployeeInteractorContract interactor) {
        mInteractor = interactor;
        mInteractor.setProgressListener(this);
    }

    @Override
    public void start(String positionInTree, final String id, String filterString) {
        if(filterString==null || filterString.isEmpty()) {
            mInteractor.loadList(Integer.valueOf(positionInTree), new DepartmentInteractorContract.GetListCallback() {
                @Override
                public void onMapListLoaded(List<MapModel> list) {
                  //  Log.e("TAG", "onMapListLoaded");
                    mMapModelList=list;
                   initViewPager(list,id);
                }

                @Override
                public void notAvailable(String errorMessage) {

                }

                @Override
                public void logOut(String errorMessage) {

                }

                @Override
                public void connectionError(String errorMessage) {

                }
            });
        }else{
            mInteractor.filteredList(filterString, new DepartmentInteractorContract.FilteredCallback() {
                @Override
                public void onFilteredList(List<MapModel> list) {
                    initViewPager(list,id);
                }
            });
        }

    }

    @Override
    public void setTitlePage(@NonNull String name) {
        mView.setTitlePage(name);
    }

    private void initViewPager(@NonNull List<MapModel> list,@NonNull String id){
        for (MapModel mapModel : list) {
            if (mapModel.getId().equals(id)) {
                mView.initViewPager(list, list.indexOf(mapModel));
                break;
            }
        }
    }

    @Override
    public void logOut() {
        mInteractor.clearCredentials();
        mView.logOut();
    }

    @Override
    public void showLogOutMessage(){
        mView.showLogOutMessage();
    }

    @Override
    public void start() {

    }

    @Override
    public void bindView(@NonNull BaseView view) {
        mView= (EmployeePagerContract.View) view;
    }

    @Override
    public void unbindView() {
        mView=null;
    }

    @Override
    public void destroy() {
        PresenterManager.removePresenter(mView.getClass().getName());
        unbindView();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
