package com.example.azolotarev.test.UI.Main.EmployeePage;

import com.example.azolotarev.test.UI.BasePresenter;
import com.example.azolotarev.test.UI.BaseView;
import com.example.azolotarev.test.UI.ProgressContract;

public interface EmployeePageContract {
    interface View extends BaseView<Presenter>, ProgressContract {
    }

    interface Presenter extends BasePresenter,ProgressContract{}
}
