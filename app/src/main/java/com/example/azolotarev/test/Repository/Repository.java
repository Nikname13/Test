package com.example.azolotarev.test.Repository;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;
import com.example.azolotarev.test.Data.Local.AvatarCache;
import com.example.azolotarev.test.Data.Local.ListCache;
import com.example.azolotarev.test.Data.Local.PersistentStorage;
import com.example.azolotarev.test.Data.Net.NetContract;
import com.example.azolotarev.test.Model.DepartmentModel;
import com.example.azolotarev.test.Model.MapModel;

import java.util.ArrayList;
import java.util.List;

public  class Repository implements RepositoryContract {


    private PersistentStorage mStorage;
    private boolean mSuccess =false;
    private JParserContract mJParser;
    private boolean mStorageFull=false;
    private NetContract mNet;



    public Repository(@NonNull PersistentStorage storage, @NonNull NetContract net) {
        mNet=net;
        mJParser=new JParser();
        mStorage=storage;
    }

    @Override
    public void isAuth(@NonNull String login,@NonNull String password, @NonNull boolean firstLoad,@NonNull final LoadSuccessCallback callback){
        Log.e("TAG", "repository isAuth");
        if(login.isEmpty() || password.isEmpty()){
            Log.e("TAG", "repository empty");
          isAuth(callback,firstLoad);
        }else {
            Log.e("TAG", "repository not Empty");
            getSuccessFromNet(callback,login,password,firstLoad);
       checkStorage(mSuccess,login,password);
        }
    }

    @Override
    public void loadDepartments(boolean refreshCache, @NonNull boolean firstLoad, @NonNull final LoadDepartmentsCallback callback) {
        Log.d("TAG", "repository getListModel "+refreshCache);
        if(ListCache.getCachedList() !=null && !refreshCache){
            Log.d("TAG", "repository getListModel cachedDepartment");
            callback.onMapListLoaded(new ArrayList<>(ListCache.getCachedList().values()));
            return;
        }
        if(refreshCache){
            Log.d("TAG", "repository getListModel refreshCache");
            getDepartmentsFromNet(callback,firstLoad);
        }
        //добавить not available
    }

    @Override
    public void loadItem(@NonNull String id, @NonNull final LoadItemCallback callback) {
        if(ListCache.getCachedList() !=null && !ListCache.getCachedList().isEmpty()){
            MapModel recyclerModel=ListCache.getCachedList().get(id);
            callback.onItemLoaded(recyclerModel);
        }else callback.notAvailable("No item");
    }

    @Override
    public void loadPhoto(@NonNull String id, int imageWidth, int imageHeight, @NonNull final LoadPhotoCallback callback) {
        if(AvatarCache.get().getBitmapFromMemory(String.valueOf(id))!=null){
            Log.i("TAG", "repository loadPhoto cache");
            callback.onResponse(AvatarCache.get().getBitmapFromMemory(String.valueOf(id)));
            return;
        }
       getPhotoFromNet(id, imageWidth, imageHeight, callback);
    }

    @Override
    public void refreshCache(List<MapModel> mapList) {
        ListCache.setCachedList(mapList);
    }

    @Override
    public void clearCredentials() {
        mStorage.clearCredentials();
    }

    private void isAuth(@NonNull final LoadSuccessCallback callback,@NonNull boolean firstLoad ){
        if(mStorage.getLOGIN().isEmpty() || mStorage.getPASSWORD().isEmpty()) {
            callback.onSuccess(false);
        }else{
            mStorageFull=true;
            isAuth(mStorage.getLOGIN(), mStorage.getPASSWORD(), firstLoad, callback);
        }
    }

    private void checkStorage(boolean success, String login, String password){
        Log.e("TAG", "repository checkStorage "+success);
        if(success){
            clearCredentials();
            mStorage.addCredentials(login, password);
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
                                mJParser.parsSuccess(
                                        response,
                                        new JParserContract.ParsSuccessCallback() {
                                                        @Override
                                                        public void onSuccess(boolean success) {
                                                            Log.e("TAG", "repository mJParser onSuccess");
                                                            mSuccess=success;
                                                            callback.onSuccess(success);
                                                        }

                                                        @Override
                                                        public void errorSuccess(String errorMessage) {
                                                            Log.e("TAG", "repository mJParser notAvailable");
                                                            callback.logOut(errorMessage);
                                                        }
                                                    }
                                        );
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
                    mJParser.parsDepartments(
                            response,
                            new JParserContract.ParsDepartmentsCallback(){
                                                @Override
                                                public void onDepartmentsLoaded(List<DepartmentModel> departments) {
                                                    Log.e("TAG", "repository mJParser pnDepartmentsLoaded");
                                                   callback.onDepartmentsLoaded(departments);
                                                }

                                                @Override
                                                public void notAvailable(String errorMessage) {

                                                }
                                            });
                }

                @Override
                public void connectionError(String errorMessage) {
                    if(ListCache.getCachedList() !=null){
                        loadDepartments(true, firstLoad, callback);
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

    private void getPhotoFromNet( @NonNull final String id, int imageWeight, int imageHeight, @NonNull final LoadPhotoCallback callback){
        Log.i("TAG", "repository getPhotoFromNet ");
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
                false);
        mNet.getPhoto(
                id,
                imageWeight,
                imageHeight,
                new NetContract.LoadPhotoCallback() {
                          @Override
                          public void onResponse(Bitmap response) {
                             Log.i("TAG", "repository getPhotoFromNet "+response.getByteCount());
                             if(response!=null) {
                                 callback.onResponse(response);
                                 AvatarCache.get().setBitmapToMemory(id, response);
                             }
                          }

                          @Override
                          public void connectionError(String errorMessage) {

                          }
                      });
    }

}
