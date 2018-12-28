package com.example.azolotarev.test.UI.Main.DepartmentsList.RootDepartments;

import android.support.annotation.NonNull;
import com.example.azolotarev.test.Model.DepartmentModel;
import com.example.azolotarev.test.UI.Main.DepartmentsList.ItemClickListener;
import com.example.azolotarev.test.UI.ProgressContract;
import com.example.azolotarev.test.UI.BasePresenter;
import com.example.azolotarev.test.UI.BaseView;

import java.util.List;

public interface DepartmentListContract {

    interface View extends BaseView<Presenter>,ProgressContract,ItemClickListener {
        void showConnectionError(String errorMessage);
        void showSuccessError(String errorMessage);
        void showAuthorization();
        void showDepartmentsList(@NonNull List<DepartmentModel> departmentList);
        void showDepartmentChildren(@NonNull List<DepartmentModel> departmentList, @NonNull int containerId);
    }

    interface Presenter extends BasePresenter,ProgressContract{
        void loadDepartments(@NonNull boolean freshUpdate, @NonNull boolean firstLoad);
        void openDepartmentDetail(@NonNull DepartmentModel selectedDepartment, @NonNull int containerId);
    }
}
