package com.example.azolotarev.test.Domain.Authorization;


import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import com.example.azolotarev.test.Repository.RepositoryContract;
import com.example.azolotarev.test.UI.ProgressContract;

public class AuthorizationInteractor implements AuthorizationInteractorContract{

    private boolean mSuccess;
    private String mConnectionError,mSuccessError;
    private RepositoryContract mRepository;
    private ProgressContract mProgressListener;

    public AuthorizationInteractor(RepositoryContract repository) {
        mRepository=repository;
        mSuccess=false;
    }
    @Override
    public void logIn(@NonNull final getSuccessCallback callback, @NonNull  String login,@NonNull String password, @NonNull boolean firstLoad) {
      //  Log.e("TAG", "auth interactor login");
        new AsyncAuth(callback,firstLoad).execute(login,password);
    }

    @Override
    public void setProgressListener(ProgressContract listener) {
        mProgressListener=listener;
    }

    private class AsyncAuth extends AsyncTask<String, Void, Void> {
        private getSuccessCallback mCallback;
        private boolean mFirstLoad;

        public AsyncAuth(@NonNull getSuccessCallback callback, @NonNull boolean firstLoad) {
            mCallback=callback;
            mFirstLoad=firstLoad;
        }

        @Override
        protected void onPreExecute() {
            mProgressListener.showProgress();
        }

        @Override
        protected Void doInBackground(String... strings) {
             mRepository.isAuth(
                     strings[0],
                     strings[1],
                     mFirstLoad,
            new RepositoryContract.LoadSuccessCallback() {
                 @Override
                 public void onSuccess(boolean success) {
                 //    Log.e("TAG", "auth interactor onSuccess");
                     mSuccess=success;
                 }

                 @Override
                 public void logOut(String errorMessage) {
                 //    Log.e("TAG", "auth interactor logout");
                     mSuccessError=errorMessage;
                 }

                 @Override
                 public void connectionError(String errorMessage) {
                   //  Log.e("TAG", "auth interactor connectionError");
                     mConnectionError=errorMessage;
                 }
             });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
          //  Log.e("TAG", "auth interactor onPostExecute");
            mProgressListener.hideProgress();
            mCallback.onSuccess(mSuccess);
            if(mSuccessError!=null && !mSuccessError.isEmpty()){
             //   Log.e("TAG", "auth interactor mSuccessError");
                mCallback.logOut(mSuccessError);
            }
            if(mConnectionError!=null && !mConnectionError.isEmpty()) {
             //   Log.e("TAG", "auth interactor mConnetctionError");
                mCallback.connectionError(mConnectionError);
            }
            }
        }
    }
