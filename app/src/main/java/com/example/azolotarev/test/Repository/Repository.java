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
    private boolean mStorageFull;
    private NetContract mNet;
    Map<String,DepartmentModel> mCachedDepartment;


    public Repository(@NonNull Context context, @NonNull NetContract net) {
        mNet=net;
        mJParser=new JParser();
        mContext = context;
        PersistentStorage.init(mContext);
    }

    @Override
    public void isAuth(@NonNull final LoadSuccessCallback callback ,@NonNull String login,@NonNull String password){
        Log.e("TAG", "repository isAuth");
        if(login.isEmpty() || password.isEmpty()){
            Log.e("TAG", "repository empty");
          isAuth(callback);
        }else {
            Log.e("TAG", "repository not Empty");
            getSuccessFromNet(callback,login,password);
       checkStorage(mSuccess,login,password);
        }
    }

    private void isAuth(@NonNull final LoadSuccessCallback callback ){
        if(PersistentStorage.getLOGIN().isEmpty() || PersistentStorage.getPASSWORD().isEmpty()) {
            callback.onSuccess(false);
        }else{
            mStorageFull=true;
             isAuth(callback,PersistentStorage.getLOGIN(), PersistentStorage.getPASSWORD());
        }
    }

    private void checkStorage(boolean success, String login, String password){
        if(success){
            if(!mStorageFull) PersistentStorage.addCredentials(login, password);
        }else{
            if(mStorageFull) PersistentStorage.clearCredentials();
        }
    }


    private void getSuccessFromNet(@NonNull final LoadSuccessCallback callback, String login, String password) {
        Log.e("TAG", "repository getSuccessFromNet");
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
                callback.connectionError(errorMessage);
            }
        },
                login,
                password
        );
    }

    @Override
    public void getDepartments(@NonNull LoadDepartmentsCallback callback, boolean refreshCache) {
        if(mCachedDepartment!=null && !refreshCache){
            //из кэша
            return;
        }
        if(refreshCache){
            getDepartmentsFromNet(callback);
        }
        //добавить not available
    }

    private void getDepartmentsFromNet(@NonNull final LoadDepartmentsCallback callback){
        if(mSuccess){
        mJParser.getDepartments(new JParserContract.ParsDepartmentsCallback() {
            @Override
            public void onDepartmentsLoaded(List<DepartmentModel> departments) {
                refreshCache(departments);
            }
        },
                "");
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
