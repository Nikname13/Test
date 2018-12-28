package com.example.azolotarev.test.UI.Main.DepartmentsList.ChildrenDepartments;

import android.support.annotation.NonNull;
import com.example.azolotarev.test.Model.BaseModel;
import com.example.azolotarev.test.Model.DepartmentModel;
import com.example.azolotarev.test.UI.BasePresenter;
import com.example.azolotarev.test.UI.BaseView;
import com.example.azolotarev.test.UI.Main.DepartmentsList.ItemClickListener;
import com.example.azolotarev.test.UI.ProgressContract;

import java.util.List;

public interface ChildrenDepartmentContract {

    interface View extends BaseView<Presenter>,ItemClickListener,ProgressContract {
         List<Object> getListModel();
         int getViewType();
        void showListModel(@NonNull List<Object> departmentList, @NonNull int viewType);
        void showChildrenList(@NonNull List<Object> departmentList, @NonNull int containerId, @NonNull int viewType);
    }
    interface Presenter extends BasePresenter,ProgressContract {
        void openDetail(@NonNull Object selectedDepartment, @NonNull int containerId);
    }
}
