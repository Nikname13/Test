package com.example.azolotarev.test.Domain.DepartmentsList;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import com.example.azolotarev.test.Model.DepartmentModel;
import com.example.azolotarev.test.Model.EmployeeModel;
import com.example.azolotarev.test.Model.MapModel;
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
    private List<MapModel> mMapList;

    public DepartmentInteractor(RepositoryContract repository) {
        mRepository = repository;
    }

    @Override
    public void loadList(@NonNull final GetListCallback callback) {
        Log.e("TAG","department interacrot loadList refreshCache= "+mRefreshCache);
        new AsyncLoadList(callback).execute();
    }

    @Override
    public void filteredList(@NonNull final String filterString, @NonNull final FilteredCallback callback) {
        if(mMapList!=null)
        applyFilter(filterString, callback, mMapList);
        else {
            loadList(new GetListCallback() {
                @Override
                public void onMapListLoaded(List<MapModel> list) {
                    applyFilter(filterString, callback, list);
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
        }
    }

    private void applyFilter(String filterString, FilteredCallback callback, List<MapModel> mapList) {
        List<MapModel> filteredList=new ArrayList<>();
        for (MapModel model : mapList) {
            if(model.getModel() instanceof EmployeeModel){
                EmployeeModel employee= (EmployeeModel) model.getModel();
                if((employee.getName()!=null && employee.getName().toLowerCase().contains(filterString.toLowerCase())) ||
                        (employee.getTitle()!=null && employee.getTitle().toLowerCase().contains(filterString.toLowerCase())) ||
                        (employee.getPhone()!=null && employee.getPhone().toLowerCase().contains(filterString.toLowerCase())) ||
                        (employee.getEmail()!=null && employee.getEmail().toLowerCase().contains(filterString.toLowerCase()))){
                    filteredList.add(model);
                }
            }
        }
        callback.onFilteredList(filteredList);
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
    public void refreshList() {
        Log.e("TAG","department interacrot refreshList");
        mRefreshCache=true;
    }

    protected RepositoryContract getRepository() {
        return mRepository;
    }

    protected ProgressContract getProgress() {
        return mProgress;
    }

    protected String getConnectionError() {
        return mConnectionError;
    }

    protected String getSuccessError() {
        return mSuccessError;
    }

    protected String getNotAvailable() {
        return mNotAvailable;
    }

    protected List<MapModel> getMapList() {
        return mMapList;
    }

    protected void setConnectionError(String connectionError) {
        mConnectionError = connectionError;
    }

    protected void setSuccessError(String successError) {
        mSuccessError = successError;
    }

    protected void setNotAvailable(String notAvailable) {
        mNotAvailable = notAvailable;
    }

    private void setMapList(){
        mMapList=new ArrayList<>();
        for(DepartmentModel departmentModel:mDepartmentModels){
            mMapList.add(new MapModel(departmentModel,0, true, 0));
            mapToRecyclerModel(departmentModel,0);
        }
        mRepository.refreshCache(mMapList);
        mRefreshCache=false;
        Log.d("TAG","set Recycler list done!");
    }

    private void mapToRecyclerModel(DepartmentModel department, int lvl){
        int groupPosition=mMapList.size();
        if(department.getDepartmentsList()!=null){
            for(DepartmentModel departmentModel:department.getDepartmentsList()){
                mMapList.add(new MapModel(departmentModel,lvl+1, false, groupPosition));
                mapToRecyclerModel(departmentModel,lvl+1);
            }
        }
        if(department.getEmployeeList()!=null){
            for(EmployeeModel employeeModel:department.getEmployeeList()){
                mMapList.add(new MapModel(employeeModel,lvl+1, false, groupPosition));
            }
        }
    }

    private class AsyncLoadList extends AsyncTask<Void, Void, Void>{
        private GetListCallback callback;

        public AsyncLoadList(GetListCallback callback) {
            this.callback = callback;
        }

        @Override
        protected void onPreExecute() {
            mProgress.showProgress();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mRepository.getDepartments(new RepositoryContract.LoadDepartmentsCallback() {
                                           @Override
                                           public void onMapListLoaded(List<MapModel> list) {
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
                    ,mRefreshCache,false);
            if(mMapList==null || mRefreshCache && mDepartmentModels!=null) setMapList();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            onPostLoadList(callback);
        }
    }

    protected void onPostLoadList(@NonNull final GetListCallback callback){
        mProgress.hideProgress();
        if(mSuccessError!=null && !mSuccessError.isEmpty()) callback.logOut(mSuccessError);
        if(mConnectionError!=null && !mConnectionError.isEmpty()) callback.connectionError(mConnectionError);
        if(mNotAvailable!=null && !mNotAvailable.isEmpty()) callback.notAvailable(mNotAvailable);
        if(mMapList!=null) callback.onMapListLoaded(mMapList);
        Log.d("TAG","onPostExecute");

    }

}
