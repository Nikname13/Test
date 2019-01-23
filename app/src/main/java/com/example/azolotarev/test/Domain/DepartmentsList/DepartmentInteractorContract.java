package com.example.azolotarev.test.Domain.DepartmentsList;

import android.support.annotation.NonNull;
import com.example.azolotarev.test.BaseCallback;
import com.example.azolotarev.test.Model.MapModel;
import com.example.azolotarev.test.UI.ProgressContract;

import java.util.List;

public interface DepartmentInteractorContract {

    interface GetListCallback extends  BaseCallback.BaseErrorCallback {
        void onMapListLoaded(List<MapModel> list);
        void notAvailable(String errorMessage);
    }

    void loadList(@NonNull final GetListCallback callback, @NonNull boolean firstLoad);
    void setProgressListener(@NonNull ProgressContract listener);
    void clearCredentials();
    void refreshList();
}
