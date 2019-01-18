package com.example.azolotarev.test.Domain.DepartmentsList;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import com.example.azolotarev.test.Model.DepartmentModel;
import com.example.azolotarev.test.Model.EmployeeModel;
import com.example.azolotarev.test.Model.RecyclerModel;
import com.example.azolotarev.test.Repository.RepositoryContract;
import com.example.azolotarev.test.UI.ProgressContract;

import java.util.ArrayList;
import java.util.List;

public class DepartmentInteractor implements DepartmentInteractorContract {

    private RepositoryContract mRepository;
    private ProgressContract mProgress;
    private boolean mRefreshCache=false;
    private List<DepartmentModel> mDepartmentModels;
    private String mConnectionError,mSuccessError,mNotAvailable;
    private List<RecyclerModel> mMapList;

    public DepartmentInteractor(RepositoryContract repository) {
        mRepository = repository;
    }

    @Override
    public void getDepartments(@NonNull final GetListCallback callback, @NonNull boolean firstLoad) {
        Log.e("TAG","department interacrot getDepartments refreshCache= "+mRefreshCache);
        new AsyncDepartments(callback).execute(mRefreshCache,firstLoad);
        mRefreshCache=false;
    }

    @Override
    public void setProgressListener(@NonNull ProgressContract listener) {
        mProgress=listener;
    }

    @Override
    public void clearCredentials() {
        mRepository.clearCredentials();
    }

    @Override
    public void refreshDepartments() {
        Log.e("TAG","department interacrot refreshDepartments");
        mRefreshCache=true;
    }

    private void setMapList(){
        mMapList=new ArrayList<>();
        for(DepartmentModel departmentModel:mDepartmentModels){
            mMapList.add(new RecyclerModel(departmentModel,0, true));
            mapToRecyclerModel(departmentModel,0);
        }
        mRepository.refreshCache(mMapList);
        Log.d("TAG","set Recycler list done!");
    }

    private void mapToRecyclerModel(DepartmentModel department, int lvl){
        if(department.getDepartmentsList()!=null){
            for(DepartmentModel departmentModel:department.getDepartmentsList()){
                mMapList.add(new RecyclerModel(departmentModel,lvl+1, false));
                mapToRecyclerModel(departmentModel,lvl+1);
            }
        }
        if(department.getEmployeeList()!=null){
            for(EmployeeModel employeeModel:department.getEmployeeList()){
                mMapList.add(new RecyclerModel(employeeModel,lvl+1, false));
            }
        }
    }

    private class AsyncDepartments extends AsyncTask<Boolean,Void, Void>{
        private GetListCallback mCallback;

        public AsyncDepartments(GetListCallback callback) {
            mCallback = callback;
        }

        @Override
        protected void onPreExecute() {
            mProgress.showProgress();
        }

        @Override
        protected Void doInBackground(Boolean... booleans) {
            mRepository.getDepartments(new RepositoryContract.LoadDepartmentsCallback() {
                                           @Override
                                           public void onMapListLoaded(List<RecyclerModel> list) {
                                               mMapList =list;
                                           }

                                           @Override
                                           public void logOut(String errorMessage) {
                                               mSuccessError=errorMessage;
                                           }

                                           @Override
                                           public void connectionError(String errorMessage) {
                                               mConnectionError=errorMessage;
                                           }

                                           @Override
                                           public void onDepartmentsLoaded(List<DepartmentModel> departments) {
                                               Log.d("TAG","doInBackground");
                                               mDepartmentModels=departments;
                                           }

                                           @Override
                                           public void notAvailable(String errorMessage) {
                                               mNotAvailable=errorMessage;
                                           }
                                       }
                    ,booleans[0],booleans[1]);
            if(mMapList==null && mDepartmentModels!=null) setMapList();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mProgress.hideProgress();
            if(mSuccessError!=null && !mSuccessError.isEmpty()) mCallback.logOut(mSuccessError);
            if(mConnectionError!=null && !mConnectionError.isEmpty()) mCallback.connectionError(mConnectionError);
            if(mNotAvailable!=null && !mNotAvailable.isEmpty()) mCallback.notAvailable(mNotAvailable);
            if(mMapList!=null) mCallback.onMapListLoaded(mMapList);
            Log.d("TAG","onPostExecute");

        }
    }

}
