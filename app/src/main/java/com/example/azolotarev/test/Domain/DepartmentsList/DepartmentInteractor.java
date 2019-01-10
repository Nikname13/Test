package com.example.azolotarev.test.Domain.DepartmentsList;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import com.example.azolotarev.test.Model.DepartmentModel;
import com.example.azolotarev.test.Repository.RepositoryContract;
import com.example.azolotarev.test.UI.ProgressContract;

import java.util.List;

public class DepartmentInteractor implements DepartmentInteractorContract {

    private RepositoryContract mRepository;
    private ProgressContract mProgress;
    private boolean mRefreshCache=false;
    private List<DepartmentModel> mDepartmentModels;
    private String mConnectionError,mSuccessError,mNotAvailable;

    public DepartmentInteractor(RepositoryContract repository) {
        mRepository = repository;
    }

    @Override
    public void getDepartments(@NonNull final getDepartmentsCallback callback, @NonNull boolean firstLoad) {
        Log.e("TAG","department interacrot getDepartments refreshCache= "+mRefreshCache);
        new AsyncDepartments(callback).execute(mRefreshCache,firstLoad);
        mRefreshCache=false;
    }

    @Override
    public void setProgressListener(@NonNull ProgressContract listener) {
        mProgress=listener;
    }

    @Override
    public void refreshDepartments() {
        Log.e("TAG","department interacrot refreshDepartments");
        mRefreshCache=true;
    }

    private class AsyncDepartments extends AsyncTask<Boolean,Void, Void>{
        private getDepartmentsCallback mCallback;

        public AsyncDepartments(getDepartmentsCallback callback) {
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
                public void logOut(String errorMessage) {
                    mSuccessError=errorMessage;
                }

                @Override
                public void connectionError(String errorMessage) {
                    mConnectionError=errorMessage;
                }

                @Override
                public void onDepartmentsLoaded(List<DepartmentModel> departments) {
                    mDepartmentModels=departments;
                }

                @Override
                public void notAvailable(String errorMessage) {
                    mNotAvailable=errorMessage;
                }
            }
            ,booleans[0],booleans[1]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mProgress.hideProgress();
            if(mSuccessError!=null && !mSuccessError.isEmpty()) mCallback.logOut(mSuccessError);
            if(mConnectionError!=null && !mConnectionError.isEmpty()) mCallback.connectionError(mConnectionError);
            if(mNotAvailable!=null && !mNotAvailable.isEmpty()) mCallback.notAvailable(mNotAvailable);
            if(mDepartmentModels!=null) mCallback.onDepartmentsLoaded(mDepartmentModels);
        }
    }
}
