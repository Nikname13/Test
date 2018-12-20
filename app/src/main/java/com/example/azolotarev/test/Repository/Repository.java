package com.example.azolotarev.test.Repository;

import android.content.Context;
import android.support.annotation.NonNull;
import com.example.azolotarev.test.Model.DepartmentModel;
import com.example.azolotarev.test.Service.Connect;
import com.example.azolotarev.test.Service.PersistentStorage;
import com.example.azolotarev.test.Service.URLBuilder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public  class Repository implements RepositoryContract {

    private static final String URI_HELLO="Hello";
    private static final String URI_GETALL="GetAll";
    private static final String URI_PHOTO="GetWPhoto";
    private static final String PARAM_LOGIN="login";
    private static final String PARAM_PASSWORD="password";
    private static final String PARAM_ID ="id";
    private final Context mContext;
    private boolean mSuccess;
    private JParserContract mJParser;
    private boolean mStorageFull;
    Map<String,DepartmentModel> mCachedDepartment;


    public Repository(Context context) {
        mJParser=new JParser();
        mContext = context;
        PersistentStorage.init(mContext);
    }

    @Override
    public boolean isAuth(@NonNull String login,@NonNull String password){
        if(login.isEmpty() || password.isEmpty()){
          mSuccess = isAuth();
        }else {
       mSuccess = mJParser.getSuccess(Connect.get(new URLBuilder(URI_HELLO).withParam(PARAM_LOGIN, login).withParam(PARAM_PASSWORD, password).build()));
       checkStorage(mSuccess,login,password);
        }
     return mSuccess;
    }

    private boolean isAuth(){
        if(PersistentStorage.getLOGIN().isEmpty() || PersistentStorage.getPASSWORD().isEmpty()) {
            return false;
        }else{
            mStorageFull=true;
            return isAuth(PersistentStorage.getLOGIN(), PersistentStorage.getPASSWORD());
        }
    }

    private void checkStorage(boolean success, String login, String password){
        if(success){
            if(!mStorageFull) PersistentStorage.addCredentials(login, password);
        }else{
            if(mStorageFull) PersistentStorage.clearCredentials();
        }
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
        if(isAuth()){
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
