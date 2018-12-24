package com.example.azolotarev.test.Repository;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import com.example.azolotarev.test.Data.Net.NetContract;
import com.example.azolotarev.test.Model.DepartmentModel;
import com.example.azolotarev.test.Data.Net.Connect;
import com.example.azolotarev.test.Data.Local.PersistentStorage;
import com.example.azolotarev.test.Data.Net.URLBuilder;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public  class Repository implements RepositoryContract {


    private final Context mContext;
    private boolean mSuccess =false;
    private JParserContract mJParser;
    private boolean mStorageFull=false;
    private NetContract mNet;
    Map<String,DepartmentModel> mCachedDepartment;


    public Repository(@NonNull Context context, @NonNull NetContract net) {
        mNet=net;
        mJParser=new JParser();
        mContext = context;
        PersistentStorage.init(mContext);
    }

    @Override
    public void isAuth(@NonNull final LoadSuccessCallback callback ,@NonNull String login,@NonNull String password, @NonNull boolean firstLoad){
        Log.e("TAG", "repository isAuth");
        if(login.isEmpty() || password.isEmpty()){
            Log.e("TAG", "repository empty");
          isAuth(callback,firstLoad);
        }else {
            Log.e("TAG", "repository not Empty");
            getSuccessFromNet(callback,login,password, firstLoad);
       checkStorage(mSuccess,login,password);
        }
    }

    private void isAuth(@NonNull final LoadSuccessCallback callback,@NonNull boolean firstLoad ){
        if(PersistentStorage.getLOGIN().isEmpty() || PersistentStorage.getPASSWORD().isEmpty()) {
            callback.onSuccess(false);
        }else{
            mStorageFull=true;
             isAuth(callback,PersistentStorage.getLOGIN(), PersistentStorage.getPASSWORD(),firstLoad);
        }
    }

    private void checkStorage(boolean success, String login, String password){
        Log.e("TAG", "repository checkStorage "+success);
        if(success){
            if(!mStorageFull) PersistentStorage.addCredentials(login, password);
        }else{
            //удаляется из сторэджа т.к. локальная проверка авторизации
            if(mStorageFull) PersistentStorage.clearCredentials();
        }
    }


    private void getSuccessFromNet(@NonNull final LoadSuccessCallback callback, @NonNull String login,@NonNull String password, @NonNull boolean firstLoad) {
        Log.e("TAG", "repository getSuccessFromNet");
        mNet.setCredentials(login,password);
        if(firstLoad) {
            mNet.isAuth(new NetContract.LoadSuccessCallback() {
                            @Override
                            public void onResponse(String response) {
                                Log.e("TAG", "repository onResponse");
                                mJParser.getSuccess(new JParserContract.ParsSuccessCallback() {
                                                        @Override
                                                        public void onSuccess(boolean success) {
                                                            Log.e("TAG", "repository mJParser onSuccess");
                                                            mSuccess=success;
                                                            callback.onSuccess(success);
                                                        }

                                                        @Override
                                                        public void errorSuccess(String errorMessage) {
                                                            Log.e("TAG", "repository mJParser errorSuccess");
                                                            callback.logOut(errorMessage);
                                                        }
                                                    },
                                        response);
                            }

                            @Override
                            public void connectionError(String errorMessage) {
                                Log.e("TAG", "repository connectionError");
                                if (mStorageFull) {
                                    callback.onSuccess(true);
                                    callback.connectionError(errorMessage + ". Данные загружены из кэша");
                                } else {
                                    callback.connectionError(errorMessage);
                                }

                            }
                        }
            );
        }
    }

    @Override
    public void getDepartments(@NonNull LoadDepartmentsCallback callback,@NonNull boolean refreshCache,  @NonNull boolean firstLoad) {
        Log.e("TAG", "repository getDepartments "+refreshCache);
        if(mCachedDepartment!=null && !refreshCache){
            Log.e("TAG", "repository getDepartments cachedDepartment");
            //из кэша
            return;
        }
        if(refreshCache){
            Log.e("TAG", "repository getDepartments refreshCache");
            getDepartmentsFromNet(callback,firstLoad);
        }
        //добавить not available
    }

    private void getDepartmentsFromNet(@NonNull final LoadDepartmentsCallback callback,@NonNull final boolean firstLoad){
        isAuth(new LoadSuccessCallback() {
            @Override
            public void onSuccess(boolean success) {

            }

            @Override
            public void logOut(String errorMessage) {

            }

            @Override
            public void connectionError(String errorMessage) {

            }
        },
        firstLoad);
        if(mStorageFull){
            mNet.getDepartments(new NetContract.LoadDepartmentsCallback() {
                @Override
                public void onResponse(String response) {
                    mJParser.getDepartments(new JParserContract.ParsDepartmentsCallback(){
                                                @Override
                                                public void onDepartmentsLoaded(List<DepartmentModel> departments) {
                                                    Log.e("TAG", "repository mJParser pnDepartmentsLoaded");
                                                   callback.onDepartmentsLoaded(departments);
                                                }

                                                @Override
                                                public void errorSuccess(String errorMessage) {

                                                }
                                            },
                    response);
                }

                @Override
                public void connectionError(String errorMessage) {
                    if(mCachedDepartment!=null){
                        getDepartments(callback,true, firstLoad);
                        callback.connectionError(errorMessage);
                    }else {
                        callback.notAvailable(errorMessage);
                    }
                }
            },
            true
                );
        }else{
            callback.logOut("Ошибка авторизации");
        }
    }

    private void refreshCache(List<DepartmentModel> departments) {
        if(mCachedDepartment ==null){
            mCachedDepartment=new LinkedHashMap<>();
        }
        mCachedDepartment.clear();
        for(DepartmentModel department:departments){
            mCachedDepartment.put(String.valueOf(department.getId()),department);
        }
    }
}
