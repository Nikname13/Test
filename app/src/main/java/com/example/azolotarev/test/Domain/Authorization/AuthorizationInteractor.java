package com.example.azolotarev.test.Domain.Authorization;


import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import com.example.azolotarev.test.Repository.RepositoryContract;
import com.example.azolotarev.test.UI.ProgressContract;

public class AuthorizationInteractor implements AuthorizationInteractorContract{

    private boolean mSuccess;
    private String mConnectionError;
    private String mSuccessError;
    private RepositoryContract mRepository;
    private ProgressContract mProgressListener;

    public AuthorizationInteractor(RepositoryContract repository) {
        mRepository=repository;
        mSuccess=false;
    }
    @Override
    public void logIn(@NonNull final getSuccessCallback callback, @NonNull  String login,@NonNull String password) {
        Log.e("TAG", "auth interactor login");
        new AsyncAuth(callback).execute(login,password);
    }

    @Override
    public void setProgressListener(ProgressContract listener) {
        mProgressListener=listener;
    }

    private class AsyncAuth extends AsyncTask<String, Void, Void> {
        private getSuccessCallback mCallback;
        public AsyncAuth(getSuccessCallback callback) {
            mCallback=callback;
        }

        @Override
        protected void onPreExecute() {
            mProgressListener.showProgress();
        }

        @Override
        protected Void doInBackground(String... strings) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
             mRepository.isAuth(new RepositoryContract.LoadSuccessCallback() {
                 @Override
                 public void onSuccess(boolean success) {
                     Log.e("TAG", "auth interactor onSuccess");
                     mSuccess=success;
                 }

                 @Override
                 public void logOut(String errorMessage) {
                     Log.e("TAG", "auth interactor logout");
                     mSuccessError=errorMessage;
                 }

                 @Override
                 public void connectionError(String errorMessage) {
                     Log.e("TAG", "auth interactor connectionError");
                     mConnectionError=errorMessage;
                 }
             }, strings[0], strings[1]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.e("TAG", "auth interactor onPostExecute");
            mProgressListener.hideProgress();
            mCallback.onSuccess(mSuccess);
            if(mSuccessError!=null && !mSuccessError.isEmpty()){
                Log.e("TAG", "auth interactor mSuccessError");
                mCallback.logOut(mSuccessError);
            }
            if(mConnectionError!=null && !mConnectionError.isEmpty()) {
                Log.e("TAG", "auth interactor mConnetctionError");
                mCallback.connectionError(mConnectionError);
            }
            }
        }
    }
