package com.example.azolotarev.test.UI.Main.DepartmentsListRoot;

import android.support.annotation.NonNull;
import com.example.azolotarev.test.Model.MapModel;
import com.example.azolotarev.test.UI.Main.RecyclerItemContract;
import com.example.azolotarev.test.UI.ProgressContract;
import com.example.azolotarev.test.UI.BasePresenter;
import com.example.azolotarev.test.UI.BaseView;

import java.util.List;

public interface DepartmentListContract {

    interface View extends BaseView<Presenter>,ProgressContract,RecyclerItemContract,RecyclerItemContract.scroll {
        void showConnectionError(String errorMessage);
        void showSuccessError(String errorMessage);
        void logOut();
        void showLogOutMessage();
        void showMessage(String message);
        void showList(@NonNull List<Integer> recyclerModelList);
        void updateList(@NonNull List<Integer> recyclerModelList);
        void showEmployeeDetail(@NonNull String positionInTree, @NonNull String id, String filterString);
        void applyFilter(@NonNull String query);
        void setFilterString(String filterString);
    }

    interface Presenter extends BasePresenter,ProgressContract{
        interface ItemInPositionCallback {
            void onItem(@NonNull MapModel model);
        }
        interface RecyclerFilterCallback{
            void onResult(List<Integer> filteredList);
        }
        void onFilter(@NonNull String filterString, @NonNull final RecyclerItemContract.RecyclerFilterCallback callback);
        void toClearFilter();
        void itemInPosition(@NonNull final RecyclerItemContract.ItemInPositionCallback callback, int position);
        void loadList(@NonNull boolean freshUpdate);
        void openElementDetail(@NonNull MapModel model);
        void applyFilter(@NonNull String query);
        void showLogOutMessage();
        void logOut();
    }
}
