package com.example.azolotarev.test.Domain.EmployeePage;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import com.example.azolotarev.test.Model.RecyclerModel;
import com.example.azolotarev.test.Repository.RepositoryContract;
import com.example.azolotarev.test.UI.ProgressContract;

public class EmployeeInteractor implements EmployeeInteractorContract {

    private final RepositoryContract mRepository;
    private ProgressContract mProgress;
    private String mConnectionError,mSuccessError,mNotAvailable;
    private Bitmap mPhoto;
    private RecyclerModel mItem;

    public EmployeeInteractor(RepositoryContract repository) {
        mRepository = repository;
    }

    @Override
    public void getItem(String id, @NonNull GetItemCallback callback) {
        new AsyncItem(callback).execute(id);
    }

    @Override
    public void loadPhoto(@NonNull getPhotoCallback callback, @NonNull String id) {
        new AsyncEmployeePhoto(callback).execute(id);
    }

    @Override
    public void setProgressListener(@NonNull ProgressContract listener) {
        mProgress=listener;
    }

    @Override
    public void clearCredentials() {
        mRepository.clearCredentials();
    }

    private class AsyncEmployeePhoto extends AsyncTask<String,Void, Void> {
        private getPhotoCallback mCallback;

        public AsyncEmployeePhoto(getPhotoCallback callback) {
            mCallback = callback;
        }

        @Override
        protected void onPreExecute() {
            mProgress.showProgress();
        }

        @Override
        protected Void doInBackground(String... id) {
            mRepository.getPhoto(new RepositoryContract.LoadPhotoCallback() {

                                     @Override
                                     public void onResponse(Bitmap photo) {
                                         mPhoto=photo;
                                     }

                                     @Override
                                     public void logOut(String errorMessage) {
                                         mSuccessError=errorMessage;
                                     }

                                     @Override
                                     public void connectionError(String errorMessage) {
                                         mConnectionError=errorMessage;
                                     }
                                 },
                    id[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mProgress.hideProgress();
            if(mPhoto!=null) mCallback.onPhoto(mPhoto);
            if(mConnectionError!=null)mCallback.connectionError(mConnectionError);
            if(mSuccessError!=null)mCallback.logOut(mSuccessError);

        }
    }

    private class AsyncItem extends AsyncTask<String,Void,Void>{
        private GetItemCallback mCallback;

        public AsyncItem(GetItemCallback callback) {
            mCallback = callback;
        }

        @Override
        protected void onPreExecute() {
            mProgress.showProgress();
        }

        @Override
        protected Void doInBackground(String... id) {
            mRepository.getItem(new RepositoryContract.LoadItemCallback() {
                                    @Override
                                    public void onItemLoaded(@NonNull RecyclerModel item) {
                                        mItem=item;
                                    }

                                    @Override
                                    public void notAvailable(String errorMessage) {
                                        mNotAvailable=errorMessage;
                                    }
                                },
                    id[0]
            );
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            mProgress.hideProgress();
            if(mItem!=null) mCallback.onItemLoaded(mItem);
            else if(mNotAvailable!=null) mCallback.notAvailable(mNotAvailable);
        }

    }

}
