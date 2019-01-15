package com.example.azolotarev.test.UI.Main.DepartmentsListRoot;

import android.support.annotation.NonNull;
import com.example.azolotarev.test.Model.BaseModel;
import com.example.azolotarev.test.Model.DepartmentModel;
import com.example.azolotarev.test.Model.EmployeeModel;
import com.example.azolotarev.test.Model.RecyclerModel;
import com.example.azolotarev.test.UI.Main.RecyclerItemContract;
import com.example.azolotarev.test.UI.ProgressContract;
import com.example.azolotarev.test.UI.BasePresenter;
import com.example.azolotarev.test.UI.BaseView;

import java.util.List;

public interface DepartmentListContract {

    interface View extends BaseView<Presenter>,ProgressContract,RecyclerItemContract,RecyclerItemContract.scroll {
        void showConnectionError(String errorMessage);
        void showSuccessError(String errorMessage);
        void showAuthorization();
        void showList(@NonNull List<Integer> recyclerModelList);
        void updateList(@NonNull List<Integer> recyclerModelList);
        void showEmployeeDetail(@NonNull EmployeeModel model);
    }

    interface Presenter extends BasePresenter,ProgressContract{
        interface itemInPositionCallback{
            void onItem(@NonNull RecyclerModel model);
        }
        void itemInPosition(@NonNull final RecyclerItemContract.itemInPositionCallback callback, @NonNull int position);
        void loadList(@NonNull boolean freshUpdate, @NonNull boolean firstLoad);
        void openElementDetail(@NonNull RecyclerModel model);
    }
}
