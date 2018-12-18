package com.example.azolotarev.test.Domain.Authorization;


import android.os.AsyncTask;
import android.support.annotation.NonNull;
import com.example.azolotarev.test.Repository.RepositoryContract;
import com.example.azolotarev.test.Service.ProgressContract;

public class AuthorizationInteractor implements AuthorizationInteractorContract{

    private boolean mSuccess;
    private RepositoryContract mRepository;
    private ProgressContract mProgressListener;

    public AuthorizationInteractor(RepositoryContract repository) {
        mRepository=repository;
        mSuccess=false;
    }
    @Override
    public void logIn(@NonNull  String login,@NonNull String password) {
        new AsyncAuth().execute(login,password);
    }

    @Override
    public void setProgressListener(ProgressContract listener) {
        mProgressListener=listener;
    }

    @Override
    public boolean getSuccess() {
        return mSuccess;
    }

    private class AsyncAuth extends AsyncTask<String,Void,Boolean>{
        @Override
        protected void onPreExecute() {
            mProgressListener.showProgress();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            return mRepository.isAuth(strings[0],strings[1]);
        }

        @Override
        protected void onPostExecute(Boolean success) {
            mSuccess = success;
            mProgressListener.hideProgress();
        }
    }

}
