package com.example.azolotarev.test.UI.DepartmentsList;

import android.support.annotation.NonNull;
import com.example.azolotarev.test.Model.DepartmentModel;
import com.example.azolotarev.test.UI.ProgressContract;
import com.example.azolotarev.test.UI.BasePresenter;
import com.example.azolotarev.test.UI.BaseView;

import java.util.List;

public interface DepartmentListContract {

    interface View extends BaseView<Presenter>,ProgressContract {
        void showConnectionError(String errorMessage);
        void showSuccessError(String errorMessage);
        void showAuthorization();
        void showDepartmentsList(List<DepartmentModel> departmentList);
    }

    interface Presenter extends BasePresenter,ProgressContract{
        void loadDepartments(@NonNull boolean freshUpdate, @NonNull boolean firstLoad);
    }
}
