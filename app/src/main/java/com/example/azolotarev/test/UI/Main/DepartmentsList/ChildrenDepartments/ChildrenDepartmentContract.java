package com.example.azolotarev.test.UI.Main.DepartmentsList.ChildrenDepartments;

import com.example.azolotarev.test.Model.DepartmentModel;
import com.example.azolotarev.test.UI.BasePresenter;
import com.example.azolotarev.test.UI.BaseView;
import com.example.azolotarev.test.UI.Main.DepartmentsList.ItemClickListener;
import com.example.azolotarev.test.UI.ProgressContract;

import java.util.List;

public interface ChildrenDepartmentContract {

    interface View extends BaseView<Presenter>,ItemClickListener,ProgressContract {
         List<DepartmentModel> getDepartments();
        void showDepartmentsList(List<DepartmentModel> departmentList);
    }
    interface Presenter extends BasePresenter,ProgressContract {}
}
