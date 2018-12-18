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


    public Repository(Context context) {
        mJParser=new JParser();
        mContext = context;
    }

    @Override
    public boolean isAuth(@NonNull String login,@NonNull String password){
        if(login.isEmpty() || password.isEmpty()){
          mSuccess = isAuth();
        }else {
        mJParser.getSuccess(Connect.get(new URLBuilder(URI_HELLO).withParam(PARAM_LOGIN, login).withParam(PARAM_PASSWORD, password).build()));
        }
     return mSuccess;
    }

    private boolean isAuth(){
        PersistentStorage.init(mContext);
        if(PersistentStorage.getLOGIN().isEmpty() || PersistentStorage.getPASSWORD().isEmpty()) {
            return false;
        }else{
            return isAuth(PersistentStorage.getLOGIN(), PersistentStorage.getPASSWORD());
        }
    }
}
