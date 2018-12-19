package com.example.azolotarev.test.Repository;

import android.content.Context;
import android.support.annotation.NonNull;
import com.example.azolotarev.test.Service.Connect;
import com.example.azolotarev.test.Service.PersistentStorage;
import com.example.azolotarev.test.Service.URLBuilder;

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


    public Repository(Context context) {
        mJParser=new JParser();
        mContext = context;
    }

    @Override
    public boolean isAuth(@NonNull String login,@NonNull String password){
        PersistentStorage.init(mContext);
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
}
