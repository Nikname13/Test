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
    public void getItem(@NonNull int id, @NonNull GetItemCallback callback) {
        new AsyncItem(callback).execute(id);
    }

    @Override
    public void loadPhoto(@NonNull getPhotoCallback callback, @NonNull int id) {
        new AsyncEmployeePhoto(callback,4647).execute();
    }

    @Override
    public void setProgressListener(@NonNull ProgressContract listener) {
        mProgress=listener;
    }

    private class AsyncEmployeePhoto extends AsyncTask<Boolean,Void, Void> {
        private getPhotoCallback mCallback;
        private int mId;

        public AsyncEmployeePhoto(getPhotoCallback callback, int id) {
            mCallback = callback;
            mId=id;
        }

        @Override
        protected void onPreExecute() {
            mProgress.showProgress();
        }

        @Override
        protected Void doInBackground(Boolean... booleans) {
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
                    mId);
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

    private class AsyncItem extends AsyncTask<Integer,Void,Void>{
        private GetItemCallback mCallback;

        public AsyncItem(GetItemCallback callback) {
            mCallback = callback;
        }

        @Override
        protected void onPreExecute() {
            mProgress.showProgress();
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            mRepository.getItem(new RepositoryContract.LoadItemCallback() {
                                    @Override
                                    public void onItemLoaded(@NonNull RecyclerModel item) {
                                        mItem=item;
                                    }

                                    @Override
                                    public void notAvailable(String errorMessage) {

                                    }
                                },
                    integers[0]
            );
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            mProgress.hideProgress();
            if(mItem!=null) mCallback.onItemLoaded(mItem);
        }

    }

}
