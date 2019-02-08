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
    interface FilteredCallback{
        void onFilteredList(List<MapModel> list);
    }
    void filteredList(@NonNull String filterString, @NonNull final FilteredCallback callback);
    void loadList(@NonNull final GetListCallback callback);
    void setProgressListener(@NonNull ProgressContract listener);
    void clearCredentials();
    void refreshList();
}
