package com.example.azolotarev.test.Domain.EmployeePage;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import com.example.azolotarev.test.Domain.DepartmentsList.DepartmentInteractor;
import com.example.azolotarev.test.Model.MapModel;
import com.example.azolotarev.test.Repository.RepositoryContract;

import java.util.ArrayList;
import java.util.List;

public class EmployeeInteractor extends DepartmentInteractor implements EmployeeInteractorContract {

    private Bitmap mPhoto;
    private MapModel mItem;

    public EmployeeInteractor(RepositoryContract repository) {
        super(repository);
    }

    @Override
    public void loadList(final int position, @NonNull final GetListCallback callback) {
        loadList(new GetListCallback() {
            @Override
            public void onMapListLoaded(List<MapModel> list) {
                onEmployeeList(callback,list, position);
            }

            @Override
            public void notAvailable(String errorMessage) {
                callback.notAvailable(errorMessage);
            }

            @Override
            public void logOut(String errorMessage) {

            }

            @Override
            public void connectionError(String errorMessage) {

            }
        });
    }

    @Override
    public void getItem(@NonNull String id, @NonNull GetItemCallback callback) {
        new AsyncItem(callback).execute(id);
    }

    @Override
    public void loadPhoto(@NonNull String id, int imageWidth, int imageHeight, boolean large, @NonNull PhotoCallback callback) {
        new AsyncEmployeePhoto(callback, large).execute(id, String.valueOf(imageWidth), String.valueOf(imageHeight));
    }

    private void onEmployeeList(@NonNull final GetListCallback callback, @NonNull List<MapModel> mapModelList, int position){
        List<MapModel> employeeList=new ArrayList<>();
        MapModel mapModel=mapModelList.get(position);
        int group=mapModel.getGroupPosition();
        for(int i=group;i<mapModelList.size();i++){
            MapModel model=mapModelList.get(i);
            if(model.getGroupPosition()==group) employeeList.add(model);
        }
        callback.onMapListLoaded(employeeList);
    }

    private class AsyncEmployeePhoto extends AsyncTask<String,Void, Void> {
        private PhotoCallback callback;
        private boolean large;

        public AsyncEmployeePhoto(PhotoCallback callback, boolean large) {
            this.callback = callback;
            this.large=large;
        }


        @Override
        protected Void doInBackground(String... id) {
            getRepository().loadPhoto(
                    id[0],
                    Integer.valueOf(id[1]),
                    Integer.valueOf(id[2]),
                    large,
                    new RepositoryContract.LoadPhotoCallback() {

                                     @Override
                                     public void onResponse(Bitmap photo) {
                                         mPhoto=photo;
                                     }

                                     @Override
                                     public void logOut(String errorMessage) {
                                         setSuccessError(errorMessage);
                                     }

                                     @Override
                                     public void connectionError(String errorMessage) {
                                         setConnectionError(errorMessage);
                                     }
                                 });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(mPhoto!=null) callback.onPhoto(mPhoto);
            if(getConnectionError()!=null) callback.connectionError(getConnectionError());
            if(getSuccessError()!=null) callback.logOut(getSuccessError());

        }
    }

    private class AsyncItem extends AsyncTask<String,Void,Void>{
        private GetItemCallback callback;

        public AsyncItem(GetItemCallback callback) {
            this.callback = callback;
        }

        @Override
        protected void onPreExecute() {
            getProgress().showProgress();
        }

        @Override
        protected Void doInBackground(String... id) {
            getRepository().loadItem(
                    id[0],
                    new RepositoryContract.LoadItemCallback() {
                                    @Override
                                    public void onItemLoaded(@NonNull MapModel item) {
                                        mItem=item;
                                    }

                                    @Override
                                    public void notAvailable(String errorMessage) {
                                        setNotAvailable(errorMessage);
                                    }
                                }
            );
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            getProgress().hideProgress();
            if(mItem!=null) callback.onItemLoaded(mItem);
            else if(getNotAvailable()!=null) callback.notAvailable(getNotAvailable());//допилить вызов метода для загрузки списка
        }
    }
}
